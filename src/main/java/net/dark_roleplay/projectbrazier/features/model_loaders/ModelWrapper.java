package net.dark_roleplay.projectbrazier.features.model_loaders;

import net.minecraft.client.renderer.model.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class ModelWrapper implements IModelGeometry {
	private final IUnbakedModel model;

	public ModelWrapper(IUnbakedModel model) {
		this.model = model;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		return this.model.bakeModel(bakery, spriteGetter, modelTransform, modelLocation);
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		return model.getTextures(modelGetter, missingTextureErrors);
	}
}
