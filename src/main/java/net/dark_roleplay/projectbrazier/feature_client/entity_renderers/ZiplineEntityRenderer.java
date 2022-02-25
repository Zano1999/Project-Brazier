package net.dark_roleplay.projectbrazier.feature_client.entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.entities.ZiplineEntity;
import net.dark_roleplay.projectbrazier.util.rendering.BakedModelRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public class ZiplineEntityRenderer extends EntityRenderer<ZiplineEntity> {
	public ZiplineEntityRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getTextureLocation(ZiplineEntity entity) {
		return null;
	}

	@Override
	public void render(ZiplineEntity entity, float p_114486_, float p_114487_, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		BakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(ProjectBrazier.MODID, "entity/zipline_handle"));

		VertexConsumer consumer = bufferSource.getBuffer(RenderType.solid());
		//consumer.putBulkData(poseStack,
		poseStack.pushPose();

		poseStack.translate(0, 2F, 0);
		poseStack.mulPose(Quaternion.fromXYZ(0, (float) Math.toRadians(-entity.getYRot()), 0));
		BakedModelRenderHelper.renderModel(poseStack, consumer, model, 1F, 1F, 1F, light, null);
		poseStack.popPose();
	}
}