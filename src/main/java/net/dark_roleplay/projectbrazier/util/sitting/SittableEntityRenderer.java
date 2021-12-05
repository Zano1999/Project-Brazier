package net.dark_roleplay.projectbrazier.util.sitting;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SittableEntityRenderer extends EntityRenderer<SittableEntity> {
	public SittableEntityRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getTextureLocation(SittableEntity entity) {
		return null;
	}

	@Override
	public boolean shouldRender(SittableEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
		return false;
	}
}