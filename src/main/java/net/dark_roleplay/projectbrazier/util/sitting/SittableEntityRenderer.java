package net.dark_roleplay.projectbrazier.util.sitting;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SittableEntityRenderer extends EntityRenderer<SittableEntity> {
	public SittableEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getEntityTexture(SittableEntity entity) {
		return null;
	}

	@Override
	public boolean shouldRender(SittableEntity livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ) {
		return false;
	}
}