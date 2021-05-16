package net.dark_roleplay.projectbrazier.experimental_features.raytrace;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT)
public class RayTraceWorldRender {

	public static final RenderTypeBuffers renderBuffers = new RenderTypeBuffers();
	private static final IRenderTypeBuffer.Impl renderBuffer = renderBuffers.getBufferSource();
	private static final Supplier<IVertexBuilder> linesWithCullAndDepth = () -> renderBuffer.getBuffer(RenderType.getLines());

	@SubscribeEvent
	public static void debugRenderCollisions(RenderWorldLastEvent event){
		if(RayTraceTestScreen.hitPoint == null) return;

		Vector3d vec = Minecraft.getInstance().getRenderManager().info.getProjectedView();
		MatrixStack matrix = event.getMatrixStack();
		matrix.push();
		matrix.translate(-vec.x, -vec.y, -vec.z);

		for(float f = 0.005F; f < 0.025F; f+=0.005F)
		WorldRenderer.drawBoundingBox(
				matrix, linesWithCullAndDepth.get(),
				new AxisAlignedBB(
						RayTraceTestScreen.hitPoint.add(-f, -f, -f),
						RayTraceTestScreen.hitPoint.add(f, f, f)
				), 1F, 0F, 0F, 1F);

		matrix.pop();
		renderBuffer.finish();
	}
}
