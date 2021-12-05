package net.dark_roleplay.projectbrazier.experimental_features.collisions;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.ReuseableStream;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CollisionListener {

	private static Map<BlockEntity, VoxelShape> COLLISIONS = new ConcurrentHashMap<>();

	public static void addCollision(BlockEntity te, VoxelShape shape){
		VoxelShape oldShape = COLLISIONS.replace(te, shape);
		if(oldShape == null)
			COLLISIONS.put(te, shape);
	}

	public static void removeCollision(BlockEntity te){
		COLLISIONS.remove(te);
	}

	@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID)
	public static class ServerCollisions{
		@SubscribeEvent
		public static void onServerTick(TickEvent.ServerTickEvent event){
			for(Map.Entry<BlockEntity, VoxelShape> entry : COLLISIONS.entrySet())
				handleCollision(entry.getKey().getLevel(), entry.getValue());
		}
	}

	@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT)
	public static class ClientCollisions{
		public static final RenderTypeBuffers renderBuffers = new RenderTypeBuffers();
		private static final IRenderTypeBuffer.Impl renderBuffer = renderBuffers.bufferSource();
		private static final Supplier<IVertexBuilder> linesWithCullAndDepth = () -> renderBuffer.getBuffer(RenderType.lines());

		@SubscribeEvent
		public static void onWorldTick(TickEvent.WorldTickEvent event){
			for(Map.Entry<BlockEntity, VoxelShape> entry : COLLISIONS.entrySet())
				handleCollision(entry.getKey().getLevel(), entry.getValue());
		}

		@SubscribeEvent
		public static void debugRenderCollisions(RenderWorldLastEvent event){
			Vec3 vec = Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition();
			MatrixStack matrix = event.getMatrixStack();
			matrix.pushPose();
			matrix.translate(-vec.x, -vec.y, -vec.z);
			for(VoxelShape entry : COLLISIONS.values()){
				WorldRenderer.renderLineBox(matrix, linesWithCullAndDepth.get(), entry.bounds(), 1F, 1F, 0F, 1F);
			}
			matrix.popPose();
			renderBuffer.endBatch();
		}
	}

	public static void handleCollision(Level world, VoxelShape shape){
		AABB scanArea = shape.bounds().inflate(1);

		List<Entity> entitiesWithinAABB = world.getEntitiesOfClass(Entity.class, scanArea, target -> !target.noPhysics);
		//TODO Skip remote client players
		ReuseableStream<VoxelShape> collisionShapes = new ReuseableStream<>(Stream.of(shape));

		for (Entity entity : entitiesWithinAABB) {
			Vec3 prevPosition = new Vec3(entity.xo, entity.yo, entity.zo);
			Vec3 entityPosition = entity.position();

			Vec3 prevMotion = entityPosition.subtract(prevPosition);
			Vec3 entityMotion = entity.getDeltaMovement();

			AABB entityBounds = entity.getBoundingBox();
			AABB prevEntityBounds = entity.getBoundingBox().move(prevMotion.reverse());

			Vec3 allowedMotion = Vec3.ZERO;

			allowedMotion = Entity.collideBoundingBoxLegacy(prevMotion, prevEntityBounds, collisionShapes);

			//Handle previousMotion
			if (allowedMotion.lengthSqr() > 1.0E-7D) {
				entity.setBoundingBox(entityBounds = prevEntityBounds.move(allowedMotion));
				entity.setLocationFromBoundingbox();
			}

			//handleNextMotion

			allowedMotion = Entity.collideBoundingBoxLegacy(entityMotion, entityBounds, collisionShapes);
			entity.setDeltaMovement(allowedMotion);
			if(allowedMotion.y == 0 ){
				entity.setOnGround(true);
				//				if(entity instanceof ServerPlayerEntity)
//				((ServerPlayerEntity)entity).connection.floatingTickCount = 0;
			}


		}
	}
}
