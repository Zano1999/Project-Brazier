package net.dark_roleplay.projectbrazier.features.model_loaders.quality_model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.dark_roleplay.projectbrazier.features.model_loaders.ModelWrapper;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

public class QualityModelLoader implements IModelLoader {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}

	@Override
	public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
		for (int i = QualityModelSettings.MODEL_QUALITY; i >= 0 && i <= QualityModelSettings.MAX_MODEL_QUALITY; i--)
			if (modelContents.has("" + i))
				return new ModelWrapper(deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents, "" + i), BlockModel.class));

		for (int i = QualityModelSettings.MODEL_QUALITY; i <= QualityModelSettings.MAX_MODEL_QUALITY; i++)
			if (modelContents.has("" + i))
				return new ModelWrapper(deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents, "" + i), BlockModel.class));
		return null;
	}
}
