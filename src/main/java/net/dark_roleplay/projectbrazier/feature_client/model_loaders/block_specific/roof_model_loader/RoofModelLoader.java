package net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;

import java.util.Map;

public class RoofModelLoader implements IModelLoader<RoofModel> {

	private static final Codec<RoofModel> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.FLOAT.optionalFieldOf("height", 16F).forGetter(RoofModel::getHeight),
			Codec.FLOAT.optionalFieldOf("width", 16F).forGetter(RoofModel::getWidth),
			Codec.FLOAT.optionalFieldOf("length", 16F).forGetter(RoofModel::getLength),
			Codec.INT.optionalFieldOf("steps", 6).forGetter(RoofModel::getSteps),
			Codec.FLOAT.optionalFieldOf("verticalOffset", 0F).forGetter(RoofModel::getVerticalOffset),
			Codec.FLOAT.optionalFieldOf("horizontalOffset", 0F).forGetter(RoofModel::getHorizontalOffset),
			Codec.INT.optionalFieldOf("tileOffset", 0).forGetter(RoofModel::getTileOffset)
	).apply(i, RoofModel::new));

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {}

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