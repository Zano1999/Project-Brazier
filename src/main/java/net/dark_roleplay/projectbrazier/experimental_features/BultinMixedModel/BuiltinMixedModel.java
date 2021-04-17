package net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
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

	private final IUnbakedModel originalModel;

	private BuiltinMixedModel(IUnbakedModel originalModel){
		this.originalModel = originalModel;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite>  spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		return new BuiltinMixedBakedModel(originalModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation));
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
		return originalModel.getTextures(modelGetter, missingTextureErrors);
	}

	public static class BuiltinMixedBakedModel extends BakedModelWrapper {
		public BuiltinMixedBakedModel(IBakedModel originalModel) {
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
		public void onResourceManagerReload(IResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			modelContents.remove("loader");
			IUnbakedModel model = deserializationContext.deserialize(modelContents, BlockModel.class);

			return new BuiltinMixedModel(model);
		}
	}
}