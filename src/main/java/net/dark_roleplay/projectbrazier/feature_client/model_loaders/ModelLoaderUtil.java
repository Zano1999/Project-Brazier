package net.dark_roleplay.projectbrazier.feature_client.model_loaders;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.util.GsonHelper;

import java.util.Map;

public class ModelLoaderUtil {

	public static UnbakedModel loadModelWithTextures(JsonDeserializationContext deserCtx, JsonObject base, String subModelName, JsonObject textures) {
		JsonObject subModelJson = GsonHelper.getAsJsonObject(base, subModelName);
		if (textures != null) {
			JsonObject subModelTextures = GsonHelper.getAsJsonObject(base, "textures", new JsonObject());
			for (Map.Entry<String, JsonElement> entry : textures.entrySet())
				subModelTextures.add(entry.getKey(), entry.getValue());

			subModelJson.add("textures", subModelTextures);
		}

		return deserCtx.deserialize(subModelJson, BlockModel.class);
	}
}
