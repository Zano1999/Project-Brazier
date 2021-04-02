package net.dark_roleplay.projectbrazier.experiments.immersive_screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class DebugImmersiveScreen extends ImmersiveScreen {

	private final IRenderTypeBuffer.Impl bufferSource;

	public DebugImmersiveScreen() {
		super(new StringTextComponent("Debuuug"), new Vector3d(23.5, 57, 29.5), new Vector3f(0, 0, 0));

		bufferSource = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
	}

	@Override
	public void renderInWorld(WorldRenderer context, MatrixStack matrixStack, float partialTicks) {
		if (this.raytrace == null) return;

		Vector3d cameraPos = RenderUtils.getCameraPos();

		RayTraceContext rtc = new RayTraceContext(
				cameraPos.add(this.raytrace.getFirst()),
				cameraPos.add(this.raytrace.getFirst().add(this.raytrace.getSecond().mul(30, 30, 30))),
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, null);

		BlockRayTraceResult rtcResult = Minecraft.getInstance().world.rayTraceBlocks(rtc);
		Vector3d hitPoint = rtcResult.getHitVec();

		matrixStack.push();
		matrixStack.translate(hitPoint.getX() - cameraPos.x - 0.5, hitPoint.getY() - cameraPos.y - 0.5, hitPoint.getZ() - cameraPos.z);
		matrixStack.rotate(Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getRotation());

//		ItemStack itemStackIn, ItemCameraTransforms.TransformType transformTypeIn, boolean leftHand, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IBakedModel modelIn
		ItemStack test = Items.DIAMOND_SWORD.getDefaultInstance();

		World world = Minecraft.getInstance().world;
		int combinedLight = WorldRenderer.getCombinedLight(world, new BlockPos(hitPoint).offset(rtcResult.getFace()));

		IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(test, null, null);
		Minecraft.getInstance().getItemRenderer().renderItem(
				test, ItemCameraTransforms.TransformType.NONE, false,
				matrixStack, bufferSource, combinedLight, combinedLight, ibakedmodel
		);

		matrixStack.pop();
		bufferSource.finish();
	}
}
