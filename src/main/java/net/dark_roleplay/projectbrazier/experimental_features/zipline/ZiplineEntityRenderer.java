package net.dark_roleplay.projectbrazier.experimental_features.zipline;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class ZiplineEntityRenderer extends EntityRenderer<ZiplineEntity> {
	public ZiplineEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	private TempModel model = new TempModel();

	@Override
	public ResourceLocation getEntityTexture(ZiplineEntity entity) {
		return null;
	}

	@Override
	public boolean shouldRender(ZiplineEntity livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ) {
		return true;
	}

	@Override
	public void render(ZiplineEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

		model.render(matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

	static class TempModel extends EntityModel<ZiplineEntity> {
		public ModelRenderer line;

		public TempModel() {
			this.line = new ModelRenderer(this, 0 , 0);
			line.addBox(-1, 0, -5, 2, 1, 10);
		}

		@Override
		public void setRotationAngles(ZiplineEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			this.line.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}

}