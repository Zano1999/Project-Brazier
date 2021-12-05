package net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class PaneCornerModel implements IModelGeometry<PaneCornerModel> {
	private UnbakedModel unconditional;
	private UnbakedModel[] inner_corner, outer_corner, horizontal, vertical, none;

	public PaneCornerModel(UnbakedModel unconditional, UnbakedModel[] inner_corner, UnbakedModel[] outer_corner, UnbakedModel[] horizontal, UnbakedModel[] vertical, UnbakedModel[] none) {
		this.unconditional = unconditional;
		this.inner_corner = inner_corner;
		this.outer_corner = outer_corner;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.none = none;
	}

	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
		return new PaneCornerBakedModel(
				this.unconditional.bake(bakery, spriteGetter, modelTransform, modelLocation),
				bake(inner_corner, bakery, spriteGetter, modelTransform, modelLocation),
				bake(outer_corner, bakery, spriteGetter, modelTransform, modelLocation),
				bake(horizontal, bakery, spriteGetter, modelTransform, modelLocation),
				bake(vertical, bakery, spriteGetter, modelTransform, modelLocation),
				bake(none, bakery, spriteGetter, modelTransform, modelLocation)
		);
	}

	private BakedModel[] bake(UnbakedModel[] input, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ResourceLocation modelLocation){
		if(input == null) return null;
		BakedModel[] output = new BakedModel[input.length];
		for(int i = 0; i < input.length; i++)
			output[i] = input[i].bake(bakery, spriteGetter, modelTransform, modelLocation);

		return output;
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
		Set<Material> textures = new HashSet<>();

		textures.addAll(unconditional.getMaterials(modelGetter, missingTextureErrors));
		for(int i = 0; i < 4; i++){
			textures.addAll(inner_corner[i].getMaterials(modelGetter, missingTextureErrors));
			textures.addAll(outer_corner[i].getMaterials(modelGetter, missingTextureErrors));
			textures.addAll(horizontal[i].getMaterials(modelGetter, missingTextureErrors));
			textures.addAll(vertical[i].getMaterials(modelGetter, missingTextureErrors));
			if(none != null)
				textures.addAll(none[i].getMaterials(modelGetter, missingTextureErrors));
		}

		return textures;
	}

	public static class Loader implements IModelLoader {
		@Override
		public void onResourceManagerReload(ResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserCtx, JsonObject modelContents) {
			UnbakedModel unconditional;
			UnbakedModel[] inner_corner, outer_corner, horizontal, vertical, none = null;

			JsonObject textures = GsonHelper.getAsJsonObject(modelContents, "textures", null);

			unconditional = loadSubModel(deserCtx, modelContents, "unconditional", textures);
			inner_corner = loadSubModel(deserCtx, GsonHelper.getAsJsonArray(modelContents, "inner_corner"), textures);
			outer_corner = loadSubModel(deserCtx, GsonHelper.getAsJsonArray(modelContents, "outer_corner"), textures);
			horizontal = loadSubModel(deserCtx, GsonHelper.getAsJsonArray(modelContents, "horizontal"), textures);
			vertical = loadSubModel(deserCtx, GsonHelper.getAsJsonArray(modelContents, "vertical"), textures);
			if(modelContents.has("none"))
				none = loadSubModel(deserCtx, GsonHelper.getAsJsonArray(modelContents, "none"), textures);

			return new PaneCornerModel(unconditional, inner_corner, outer_corner, horizontal, vertical, none);
		}

		private UnbakedModel[] loadSubModel(JsonDeserializationContext deserCtx, JsonArray base, JsonObject textures){
			UnbakedModel[] result = new UnbakedModel[base.size()];
			for(int i = 0; i < result.length; i++){
				JsonObject subModelJson = base.get(i).getAsJsonObject();
				if(textures != null){
					JsonObject subModelTextures = GsonHelper.getAsJsonObject(subModelJson, "textures", new JsonObject());
					for(Map.Entry<String, JsonElement> entry : textures.entrySet())
						subModelTextures.add(entry.getKey(), entry.getValue());

					subModelJson.add("textures", subModelTextures);
				}
				result[i] = deserCtx.deserialize(subModelJson, BlockModel.class);
			}
			return result;
		}

		private UnbakedModel loadSubModel(JsonDeserializationContext deserCtx, JsonObject base, String subModelName, JsonObject textures){
			JsonObject subModelJson = GsonHelper.getAsJsonObject(base, subModelName);
			if(textures != null){
				JsonObject subModelTextures = GsonHelper.getAsJsonObject(subModelJson, "textures", new JsonObject());
				for(Map.Entry<String, JsonElement> entry : textures.entrySet())
					subModelTextures.add(entry.getKey(), entry.getValue());

				subModelJson.add("textures", subModelTextures);
			}

			return deserCtx.deserialize(subModelJson, BlockModel.class);
		}
	}
}
