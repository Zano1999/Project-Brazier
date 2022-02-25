package net.dark_roleplay.projectbrazier.experimental_features.chopping_block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class ChoppingBlockBlockEntityRenderer implements BlockEntityRenderer<ChoppingBlockBlockEntity> {

	public ChoppingBlockBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

	@Override
	public void render(ChoppingBlockBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
		if (blockEntity.isEmpty()) return;

		poseStack.pushPose();
		poseStack.translate(0.5, 1.25, 0.5);
		poseStack.scale(0.5F, 0.5F, 0.5F);

		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		itemRenderer.render(
				blockEntity.getHeldItem(),
				ItemTransforms.TransformType.NONE,
				true,
				poseStack,
				multiBufferSource,
				combinedLight,
				combinedOverlay,
				itemRenderer.getModel(
						blockEntity.getHeldItem(),
						blockEntity.getLevel(),
						null, 0
				)
		);

		poseStack.popPose();
	}
}
