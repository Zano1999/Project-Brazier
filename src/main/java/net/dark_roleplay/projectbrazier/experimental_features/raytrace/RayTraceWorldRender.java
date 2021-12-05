package net.dark_roleplay.projectbrazier.experimental_features.raytrace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT)
public class RayTraceWorldRender {

	public static final RenderTypeBuffers renderBuffers = new RenderTypeBuffers();
	private static final MultiBufferSource.Impl renderBuffer = renderBuffers.bufferSource();
	private static final Supplier<VertexConsumer> linesWithCullAndDepth = () -> renderBuffer.getBuffer(RenderType.lines());

	@SubscribeEvent
	public static void debugRenderCollisions(RenderLevelLastEvent event){
		if(RayTraceTestScreen.hitPoint == null) return;

		Vec3 vec = Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition();
		PoseStack matrix = event.getMatrixStack();
		matrix.pushPose();
		matrix.translate(-vec.x, -vec.y, -vec.z);

		for(float f = 0.005F; f < 0.025F; f+=0.005F)
		WorldRenderer.renderLineBox(
				matrix, linesWithCullAndDepth.get(),
				new AABB(
						RayTraceTestScreen.hitPoint.add(-f, -f, -f),
						RayTraceTestScreen.hitPoint.add(f, f, f)
				), 1F, 0F, 0F, 1F);

		matrix.popPose();
		renderBuffer.endBatch();
	}
}
