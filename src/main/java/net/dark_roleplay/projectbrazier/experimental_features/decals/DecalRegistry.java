package net.dark_roleplay.projectbrazier.experimental_features.decals;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class DecalRegistry {

	public static IForgeRegistry<Decal> REGISTRY;

	public static void registerRegistry(RegistryEvent.NewRegistry event){
		RegistryBuilder<Decal> decalRegistry = new RegistryBuilder();
		decalRegistry.setType(Decal.class).setName(new ResourceLocation(ProjectBrazier.MODID,"decals"));
		REGISTRY = decalRegistry.create();
	}
}
