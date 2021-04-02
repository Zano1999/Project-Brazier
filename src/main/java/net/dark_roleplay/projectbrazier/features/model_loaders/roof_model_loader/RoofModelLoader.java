package net.dark_roleplay.projectbrazier.features.model_loaders.roof_model_loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

import java.util.Map;

public class RoofModelLoader implements IModelLoader<RoofModel> {

	private static final Codec<RoofModel> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.DOUBLE.optionalFieldOf("height", 16D).forGetter(RoofModel::getHeight),
			Codec.DOUBLE.optionalFieldOf("width", 16D).forGetter(RoofModel::getWidth),
			Codec.DOUBLE.optionalFieldOf("length", 16D).forGetter(RoofModel::getLength),
			Codec.INT.optionalFieldOf("steps", 6).forGetter(RoofModel::getSteps),
			Codec.DOUBLE.optionalFieldOf("verticalOffset", 0D).forGetter(RoofModel::getVerticalOffset)
	).apply(i, RoofModel::new));

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}

	@Override
	public RoofModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
		RoofModel model = CODEC.decode(JsonOps.INSTANCE, modelContents).getOrThrow(false, System.out::println).getFirst();

		JsonObject textures = modelContents.get("textures").getAsJsonObject();
		for(Map.Entry<String, JsonElement> entry : textures.entrySet()){
			model.addTexture(entry.getKey(), entry.getValue().getAsString());
		}
		return model;
	}
}