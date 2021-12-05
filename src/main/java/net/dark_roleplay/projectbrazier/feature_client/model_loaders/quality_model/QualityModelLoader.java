package net.dark_roleplay.projectbrazier.feature_client.model_loaders.quality_model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.ModelLoaderUtil;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.ModelWrapper;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

public class QualityModelLoader implements IModelLoader {

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
	}

	@Override
	public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
		JsonObject textures = GsonHelper.getAsJsonObject(modelContents, "textures", null);

		for (int i = QualityModelSettings.MODEL_QUALITY; i >= 0 && i <= QualityModelSettings.MAX_MODEL_QUALITY; i--)
			if (modelContents.has("" + i))
				return new ModelWrapper(ModelLoaderUtil.loadModelWithTextures(deserializationContext, modelContents, "" + i, textures));

		for (int i = QualityModelSettings.MODEL_QUALITY; i <= QualityModelSettings.MAX_MODEL_QUALITY; i++)
			if (modelContents.has("" + i))
				return new ModelWrapper(ModelLoaderUtil.loadModelWithTextures(deserializationContext, modelContents, "" + i, textures));
		return null;
	}
}
