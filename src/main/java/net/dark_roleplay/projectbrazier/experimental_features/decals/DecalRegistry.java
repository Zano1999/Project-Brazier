package net.dark_roleplay.projectbrazier.experimental_features.decals;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.decals.capability.DecalContainer;
import net.dark_roleplay.projectbrazier.experimental_features.decals.capability.DecalContainerProvider;
import net.dark_roleplay.projectbrazier.experimental_features.decals.capability.DecalContainerStorage;
import net.dark_roleplay.projectbrazier.experimental_features.decals.decal.Decal;
import net.dark_roleplay.projectbrazier.experimental_features.decals.decals.WhitewashDecal;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.Decor;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class DecalRegistry {

	public static Decal WHITEWASH;
	public static IForgeRegistry<Decal> REGISTRY;


	@CapabilityInject(DecalContainer.class)
	public static Capability<DecalContainer> DECAL;

	public static void register() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(DecorRegistrar::registerRegistry);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Decor.class, DecorRegistrar::registerDecor);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(DecorRegistrar::commonSetup);
		MinecraftForge.EVENT_BUS.addGenericListener(Chunk.class, DecorRegistrar::attachCapability);
	}

	public static void registerRegistry(RegistryEvent.NewRegistry event){
		RegistryBuilder<Decal> decalRegistry = new RegistryBuilder();
		decalRegistry.setType(Decal.class).setName(new ResourceLocation(ProjectBrazier.MODID,"decals"));
		REGISTRY = decalRegistry.create();
	}

	public static void registerDecor(RegistryEvent.Register<Decal> event){
		event.getRegistry().register(WHITEWASH = new WhitewashDecal().setRegistryName("book_1"));
	}

	public static void attachCapability(AttachCapabilitiesEvent<Chunk> event){
		event.addCapability(DecalContainerProvider.NAME, new DecalContainerProvider());
	}

	public static void commonSetup(FMLCommonSetupEvent event){
		CapabilityManager.INSTANCE.register(DecalContainer.class, new DecalContainerStorage(), () -> new DecalContainer());
	}
}
