package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.projectbrazier.feature.registrars.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ProjectBrazier.MODID)
public class ProjectBrazier {

	public static final String MODID = "projectbrazier";

	public ProjectBrazier() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		BrazierPackets.registerPackets();

		MinecraftForge.EVENT_BUS.addListener(BrazierCommands::registerCommands);

		BrazierRegistries.BLOCKS.register(modEventBus);
		BrazierRegistries.BLOCKS_NO_ITEMS.register(modEventBus);
		BrazierRegistries.BLOCK_ENTITIES.register(modEventBus);
		BrazierRegistries.ITEMS.register(modEventBus);
		BrazierRegistries.CONTAINERS.register(modEventBus);
		BrazierRegistries.ENTITIES.register(modEventBus);
		BrazierRegistries.SOUNDS.register(modEventBus);

		modEventBus.addListener(this::hackyHackToByPassLoadingOrder);
		modEventBus.addListener(this::setupCommonStuff);
		modEventBus.addListener(this::setupServerStuff);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ProjectBrazierClient::modConstructor);


//		DecorRegistrar.register();
		//Decals
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(DecalRegistry::registerRegistry);
//        MinecraftForge.EVENT_BUS.addGenericListener(Chunk.class, CapabilityAttachListener::attachChunkCapability);
//        CapabilityManager.INSTANCE.register(DecalChunk.class, new DecalChunkStorage(), () -> new DecalChunk(0, 256)); //TODO 1.17 update the default to new default heights.
	}

	public void hackyHackToByPassLoadingOrder(RegistryEvent.NewRegistry event) {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		BrazierBlocks.preRegistry();
		BrazierBlockEntities.preRegistry();
		BrazierItems.preRegistry();
		BrazierContainers.preRegistry();
		BrazierEntities.preRegistry();
		BrazierSounds.preRegistry();

		modEventBus.addListener(BrazierBlocks::postRegistry);
		modEventBus.addListener(BrazierBlockEntities::postRegistry);
		modEventBus.addGenericListener(Item.class, BrazierItems::registerItemBlocks);
		modEventBus.addListener(BrazierItems::postRegistry);
		modEventBus.addListener(BrazierContainers::postRegistry);
		modEventBus.addListener(BrazierEntities::postRegistry);
		modEventBus.addListener(BrazierSounds::postRegistry);
	}

	public void setupCommonStuff(FMLCommonSetupEvent event) {
	}

	public void setupServerStuff(FMLDedicatedServerSetupEvent event) {
	}
}
