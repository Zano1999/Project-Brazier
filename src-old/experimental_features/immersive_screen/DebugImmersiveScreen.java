package net.dark_roleplay.projectbrazier.experimental_features.immersive_screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;;
import net.minecraft.world.level.Level;

public class DebugImmersiveScreen extends ImmersiveScreen {

	private final MultiBufferSource.BufferSource bufferSource;

	public DebugImmersiveScreen() {
		super(new TextComponent("Debuuug"), new Vec3(23.5, 57, 29.5), new Vector3f(0, 0, 0));

		bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
	}

	@Override
	public void renderInWorld(LevelRenderer context, PoseStack matrixStack, float partialTicks) {
		if (this.raytrace == null) return;

		Vec3 cameraPos = RenderUtils.getCameraPos();

		ClipContext rtc = new ClipContext(
				cameraPos.add(this.raytrace.getFirst()),
				cameraPos.add(this.raytrace.getFirst().add(this.raytrace.getSecond().multiply(30, 30, 30))),
				ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null);

		BlockHitResult rtcResult = Minecraft.getInstance().level.clip(rtc);
		Vec3 hitPoint = rtcResult.getLocation();

		matrixStack.pushPose();
		matrixStack.translate(hitPoint.x() - cameraPos.x - 0.5, hitPoint.y() - cameraPos.y - 0.5, hitPoint.z() - cameraPos.z);
		matrixStack.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());

//		ItemStack itemStackIn, ItemCameraTransforms.TransformType transformTypeIn, boolean leftHand, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IBakedModel modelIn
		ItemStack test = Items.DIAMOND_SWORD.getDefaultInstance();

		Level world = Minecraft.getInstance().level;
		int combinedLight = LevelRenderer.getLightColor(world, new BlockPos(hitPoint).relative(rtcResult.getDirection()));

		BakedModel ibakedmodel = itemRenderer.getModel(test, null, null, 0);
		Minecraft.getInstance().getItemRenderer().render(
				test, ItemTransforms.TransformType.NONE, false,
				matrixStack, bufferSource, combinedLight, combinedLight, ibakedmodel
		);

		matrixStack.popPose();
		bufferSource.endBatch();
	}
}
