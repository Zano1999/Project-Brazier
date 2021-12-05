package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Decor extends ForgeRegistryEntry<Decor>{

	private ResourceLocation modelLocation;

	public ResourceLocation getModelLocation(){
		return modelLocation != null ? modelLocation : (modelLocation = new ResourceLocation(this.getRegistryName().getNamespace(), "decor/" + this.getRegistryName().getPath()));
	}
}
