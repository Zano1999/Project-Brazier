package net.dark_roleplay.projectbrazier.experimental_features.immersive_screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.level.Level;

public class DebugImmersiveScreen extends ImmersiveScreen {

	private final IRenderTypeBuffer.Impl bufferSource;

	public DebugImmersiveScreen() {
		super(new StringTextComponent("Debuuug"), new Vec3(23.5, 57, 29.5), new Vector3f(0, 0, 0));

		bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
	}

	@Override
	public void renderInWorld(WorldRenderer context, MatrixStack matrixStack, float partialTicks) {
		if (this.raytrace == null) return;

		Vec3 cameraPos = RenderUtils.getCameraPos();

		RayTraceContext rtc = new RayTraceContext(
				cameraPos.add(this.raytrace.getFirst()),
				cameraPos.add(this.raytrace.getFirst().add(this.raytrace.getSecond().multiply(30, 30, 30))),
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, null);

		BlockHitResult rtcResult = Minecraft.getInstance().level.clip(rtc);
		Vec3 hitPoint = rtcResult.getLocation();

		matrixStack.pushPose();
		matrixStack.translate(hitPoint.x() - cameraPos.x - 0.5, hitPoint.y() - cameraPos.y - 0.5, hitPoint.z() - cameraPos.z);
		matrixStack.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());

//		ItemStack itemStackIn, ItemCameraTransforms.TransformType transformTypeIn, boolean leftHand, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IBakedModel modelIn
		ItemStack test = Items.DIAMOND_SWORD.getDefaultInstance();

		Level world = Minecraft.getInstance().level;
		int combinedLight = WorldRenderer.getLightColor(world, new BlockPos(hitPoint).relative(rtcResult.getDirection()));

		IBakedModel ibakedmodel = itemRenderer.getModel(test, null, null);
		Minecraft.getInstance().getItemRenderer().render(
				test, ItemCameraTransforms.TransformType.NONE, false,
				matrixStack, bufferSource, combinedLight, combinedLight, ibakedmodel
		);

		matrixStack.popPose();
		bufferSource.endBatch();
	}
}
