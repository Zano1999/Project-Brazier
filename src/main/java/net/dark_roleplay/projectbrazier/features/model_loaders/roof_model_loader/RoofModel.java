package net.dark_roleplay.projectbrazier.features.model_loaders.roof_model_loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.model.*;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class RoofModel implements IModelGeometry<RoofModel> {

	private static final Codec<RoofModel> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.DOUBLE.optionalFieldOf("height", 16D).forGetter(RoofModel::getHeight),
			Codec.DOUBLE.optionalFieldOf("width", 16D).forGetter(RoofModel::getWidth),
			Codec.DOUBLE.optionalFieldOf("length", 16D).forGetter(RoofModel::getLength),
			Codec.INT.optionalFieldOf("steps", 6).forGetter(RoofModel::getSteps),
			Codec.DOUBLE.optionalFieldOf("verticalOffset", 16D).forGetter(RoofModel::getVerticalOffset)
	).apply(i, RoofModel::new));

	private final double height, width, length, verticalOffset;
	private final int steps;
	private Map<String, RenderMaterial> textures = new HashMap<>();

	public RoofModel(double height, double width, double length, int steps, double verticalOffset) {
		this.height = height;
		this.width = width;
		this.length = length;
		this.steps = steps;
		this.verticalOffset = verticalOffset;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getLength() {
		return length;
	}

	public double getVerticalOffset() {
		return verticalOffset;
	}

	public int getSteps() {
		return steps;
	}

	public void addTexture(String key, String value){
		this.textures.put(key, new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(value)));
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		return null;
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		return null;
	}

	public static class Loader implements IModelLoader<RoofModel>{

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {

		}

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
}
