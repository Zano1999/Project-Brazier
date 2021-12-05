package net.dark_roleplay.projectbrazier.feature_client.model_loaders;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class ModelWrapper implements IModelGeometry {
	private final UnbakedModel model;

	public ModelWrapper(UnbakedModel model) {
		this.model = model;
	}

	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
		return this.model.bake(bakery, spriteGetter, modelTransform, modelLocation);
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		return model.getMaterials(modelGetter, missingTextureErrors);
	}
}
