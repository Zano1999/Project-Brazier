package net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class PaneCornerModel implements IModelGeometry<PaneCornerModel> {
	private IUnbakedModel unconditional;
	private IUnbakedModel[] inner_corner, outer_corner, horizontal, vertical, none;

	public PaneCornerModel(IUnbakedModel unconditional, IUnbakedModel[] inner_corner, IUnbakedModel[] outer_corner, IUnbakedModel[] horizontal, IUnbakedModel[] vertical, IUnbakedModel[] none) {
		this.unconditional = unconditional;
		this.inner_corner = inner_corner;
		this.outer_corner = outer_corner;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.none = none;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		return new PaneCornerBakedModel(
				this.unconditional.bakeModel(bakery, spriteGetter, modelTransform, modelLocation),
				bake(inner_corner, bakery, spriteGetter, modelTransform, modelLocation),
				bake(outer_corner, bakery, spriteGetter, modelTransform, modelLocation),
				bake(horizontal, bakery, spriteGetter, modelTransform, modelLocation),
				bake(vertical, bakery, spriteGetter, modelTransform, modelLocation),
				bake(none, bakery, spriteGetter, modelTransform, modelLocation)
		);
	}

	private IBakedModel[] bake(IUnbakedModel[] input, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation){
		if(input == null) return null;
		IBakedModel[] output = new IBakedModel[input.length];
		for(int i = 0; i < input.length; i++)
			output[i] = input[i].bakeModel(bakery, spriteGetter, modelTransform, modelLocation);

		return output;
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
		Set<RenderMaterial> textures = new HashSet<>();

		textures.addAll(unconditional.getTextures(modelGetter, missingTextureErrors));
		for(int i = 0; i < 4; i++){
			textures.addAll(inner_corner[i].getTextures(modelGetter, missingTextureErrors));
			textures.addAll(outer_corner[i].getTextures(modelGetter, missingTextureErrors));
			textures.addAll(horizontal[i].getTextures(modelGetter, missingTextureErrors));
			textures.addAll(vertical[i].getTextures(modelGetter, missingTextureErrors));
			if(none != null)
				textures.addAll(none[i].getTextures(modelGetter, missingTextureErrors));
		}

		return textures;
	}

	public static class Loader implements IModelLoader {
		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserCtx, JsonObject modelContents) {
			IUnbakedModel unconditional;
			IUnbakedModel[] inner_corner, outer_corner, horizontal, vertical, none = null;

			JsonObject textures = JSONUtils.getJsonObject(modelContents, "textures", null);

			unconditional = loadSubModel(deserCtx, modelContents, "unconditional", textures);
			inner_corner = loadSubModel(deserCtx, JSONUtils.getJsonArray(modelContents, "inner_corner"), textures);
			outer_corner = loadSubModel(deserCtx, JSONUtils.getJsonArray(modelContents, "outer_corner"), textures);
			horizontal = loadSubModel(deserCtx, JSONUtils.getJsonArray(modelContents, "horizontal"), textures);
			vertical = loadSubModel(deserCtx, JSONUtils.getJsonArray(modelContents, "vertical"), textures);
			if(modelContents.has("none"))
				none = loadSubModel(deserCtx, JSONUtils.getJsonArray(modelContents, "none"), textures);

			return new PaneCornerModel(unconditional, inner_corner, outer_corner, horizontal, vertical, none);
		}

		private IUnbakedModel[] loadSubModel(JsonDeserializationContext deserCtx, JsonArray base, JsonObject textures){
			IUnbakedModel[] result = new IUnbakedModel[base.size()];
			for(int i = 0; i < result.length; i++){
				JsonObject subModelJson = base.get(i).getAsJsonObject();
				if(textures != null){
					JsonObject subModelTextures = JSONUtils.getJsonObject(subModelJson, "textures", new JsonObject());
					for(Map.Entry<String, JsonElement> entry : textures.entrySet())
						subModelTextures.add(entry.getKey(), entry.getValue());

					subModelJson.add("textures", subModelTextures);
				}
				result[i] = deserCtx.deserialize(subModelJson, BlockModel.class);
			}
			return result;
		}

		private IUnbakedModel loadSubModel(JsonDeserializationContext deserCtx, JsonObject base, String subModelName, JsonObject textures){
			JsonObject subModelJson = JSONUtils.getJsonObject(base, subModelName);
			if(textures != null){
				JsonObject subModelTextures = JSONUtils.getJsonObject(subModelJson, "textures", new JsonObject());
				for(Map.Entry<String, JsonElement> entry : textures.entrySet())
					subModelTextures.add(entry.getKey(), entry.getValue());

				subModelJson.add("textures", subModelTextures);
			}

			return deserCtx.deserialize(subModelJson, BlockModel.class);
		}
	}
}
