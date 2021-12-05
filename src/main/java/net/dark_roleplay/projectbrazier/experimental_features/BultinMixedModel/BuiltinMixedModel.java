package net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class BuiltinMixedModel implements IModelGeometry<BuiltinMixedModel> {

	private final UnbakedModel originalModel;

	private BuiltinMixedModel(UnbakedModel originalModel){
		this.originalModel = originalModel;
	}

	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite>  spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
		return new BuiltinMixedBakedModel(originalModel.bake(bakery, spriteGetter, modelTransform, modelLocation));
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
		return originalModel.getMaterials(modelGetter, missingTextureErrors);
	}

	public static class BuiltinMixedBakedModel extends BakedModelWrapper {
		public BuiltinMixedBakedModel(BakedModel originalModel) {
			super(originalModel);
		}

		@Override
		public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData){
			List<BakedQuad> quads = new ArrayList<>();
			quads.addAll(super.getQuads(state, side, rand, extraData));

			if(extraData == null || extraData == EmptyModelData.INSTANCE) return quads;
			List<IQuadProvider> quadProviders = extraData.getData(BuiltinMixedModelData.PROP);
			if(quadProviders != null)
				for(IQuadProvider provider : quadProviders){
					List<BakedQuad> additionalQuads = provider.getQuads(state, side, rand);
					if(additionalQuads != null && !additionalQuads.isEmpty())
						quads.addAll(additionalQuads);
				}

			return quads;
		}
	}

	public static class Loader implements IModelLoader {
		@Override
		public void onResourceManagerReload(ResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			modelContents.remove("loader");
			UnbakedModel model = deserializationContext.deserialize(modelContents, BlockModel.class);

			return new BuiltinMixedModel(model);
		}
	}
}