package net.dark_roleplay.projectbrazier.experiments.collisions;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CollisionListener {

	private static Map<TileEntity, VoxelShape> COLLISIONS = new ConcurrentHashMap<>();

	public static void addCollision(TileEntity te, VoxelShape shape){
		VoxelShape oldShape = COLLISIONS.replace(te, shape);
		if(oldShape == null)
			COLLISIONS.put(te, shape);
	}

	public static void removeCollision(TileEntity te){
		COLLISIONS.remove(te);
	}

	@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID)
	public static class ServerCollisions{
		@SubscribeEvent
		public static void onServerTick(TickEvent.ServerTickEvent event){
			if(Minecraft.getInstance().world == null) return;
			for(Map.Entry<TileEntity, VoxelShape> entry : COLLISIONS.entrySet())
				handleCollision(entry.getKey().getWorld(), entry.getValue());
		}
	}

	@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT)
	public static class ClientCollisions{
		public static final RenderTypeBuffers renderBuffers = new RenderTypeBuffers();
		private static final IRenderTypeBuffer.Impl renderBuffer = renderBuffers.getBufferSource();
		private static final Supplier<IVertexBuilder> linesWithCullAndDepth = () -> renderBuffer.getBuffer(RenderType.getLines());

		@SubscribeEvent
		public static void onWorldTick(TickEvent.WorldTickEvent event){
			for(Map.Entry<TileEntity, VoxelShape> entry : COLLISIONS.entrySet())
				handleCollision(entry.getKey().getWorld(), entry.getValue());
		}

		@SubscribeEvent
		public static void debugRenderCollisions(RenderWorldLastEvent event){
			Vector3d vec = Minecraft.getInstance().getRenderManager().info.getProjectedView();
			MatrixStack matrix = event.getMatrixStack();
			matrix.push();
			matrix.translate(-vec.x, -vec.y, -vec.z);
			for(VoxelShape entry : COLLISIONS.values()){
				WorldRenderer.drawBoundingBox(matrix, linesWithCullAndDepth.get(), entry.getBoundingBox(), 1F, 1F, 0F, 1F);
			}
			matrix.pop();
			renderBuffer.finish();
		}
	}

	public static void handleCollision(World world, VoxelShape shape){
		AxisAlignedBB scanArea = shape.getBoundingBox().grow(1);

		List<Entity> entitiesWithinAABB = world.getEntitiesWithinAABB(Entity.class, scanArea, target -> !target.noClip);
		//TODO Skip remote client players
		ReuseableStream<VoxelShape> collisionShapes = new ReuseableStream<>(Stream.of(shape));

		for (Entity entity : entitiesWithinAABB) {
			Vector3d prevPosition = new Vector3d(entity.prevPosX, entity.prevPosY, entity.prevPosZ);
			Vector3d entityPosition = entity.getPositionVec();

			Vector3d prevMotion = entityPosition.subtract(prevPosition);
			Vector3d entityMotion = entity.getMotion();

			AxisAlignedBB entityBounds = entity.getBoundingBox();
			AxisAlignedBB prevEntityBounds = entity.getBoundingBox().offset(prevMotion.inverse());

			Vector3d allowedMotion = Vector3d.ZERO;

			allowedMotion = Entity.collideBoundingBox(prevMotion, prevEntityBounds, collisionShapes);

			//Handle previousMotion
			if (allowedMotion.lengthSquared() > 1.0E-7D) {
				entity.setBoundingBox(entityBounds = prevEntityBounds.offset(allowedMotion));
				entity.resetPositionToBB();
			}

			//handleNextMotion

			allowedMotion = Entity.collideBoundingBox(entityMotion, entityBounds, collisionShapes);
			entity.setMotion(allowedMotion);
			if(allowedMotion.y == 0 ){
				entity.setOnGround(true);
				//				if(entity instanceof ServerPlayerEntity)
//				((ServerPlayerEntity)entity).connection.floatingTickCount = 0;
			}


		}
	}
}
