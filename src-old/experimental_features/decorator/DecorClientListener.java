package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;

public class DecorClientListener {

	public static void registerModels(ModelRegistryEvent event){
		for(Decor decor : DecorRegistrar.REGISTRY.getValues()){
			ModelLoader.addSpecialModel(decor.getModelLocation());
		}
	}
}
