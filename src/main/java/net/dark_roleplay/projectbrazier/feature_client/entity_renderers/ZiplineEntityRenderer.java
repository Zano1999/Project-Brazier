package net.dark_roleplay.projectbrazier.feature_client.entity_renderers;

import net.dark_roleplay.projectbrazier.feature.entities.ZiplineEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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
	public boolean shouldRender(ZiplineEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
		return false;
	}
}