package net.dark_roleplay.projectbrazier.features.model_loaders.roof_model_loader;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BakedRoofModel implements IBakedModel {

	private final Map<Direction, List<BakedQuad>> models = new EnumMap<>(Direction.class);

	private final TextureAtlasSprite particleTexture;

	public BakedRoofModel(TextureAtlasSprite particleTexture) {
		this.particleTexture = particleTexture;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		return models.get(side);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isSideLit() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return particleTexture;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}
}
