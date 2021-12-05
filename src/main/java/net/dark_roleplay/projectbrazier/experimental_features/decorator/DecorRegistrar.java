package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainerProvider;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainerStorage;
import net.minecraft.resources.ResourceLocation;
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

public class DecorRegistrar {

	public static Decor TEST;
	public static IForgeRegistry<Decor> REGISTRY;

	@CapabilityInject(DecorContainer.class)
	public static Capability<DecorContainer> DECOR;

	public static void register() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(DecorRegistrar::registerRegistry);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Decor.class, DecorRegistrar::registerDecor);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(DecorRegistrar::commonSetup);
		MinecraftForge.EVENT_BUS.addGenericListener(Chunk.class, DecorRegistrar::attachCapability);
	}

	public static void registerRegistry(RegistryEvent.NewRegistry event){
		RegistryBuilder<Decor> decalRegistry = new RegistryBuilder();
		decalRegistry.setType(Decor.class).setName(new ResourceLocation(ProjectBrazier.MODID, "decor"));
		REGISTRY = decalRegistry.create();
	}

	public static void registerDecor(RegistryEvent.Register<Decor> event){
		event.getRegistry().register(TEST = new Decor().setRegistryName("book_1"));
		event.getRegistry().register(new Decor().setRegistryName("paper_pile_1"));
	}

	public static void attachCapability(AttachCapabilitiesEvent<Chunk> event){
		event.addCapability(DecorContainerProvider.NAME, new DecorContainerProvider());
	}

	public static void commonSetup(FMLCommonSetupEvent event){
		CapabilityManager.INSTANCE.register(DecorContainer.class, new DecorContainerStorage(), () -> new DecorContainer());

	}
}
