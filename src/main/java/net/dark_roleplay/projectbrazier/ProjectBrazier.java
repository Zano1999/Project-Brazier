package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.projectbrazier.feature.registrars.*;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
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
        BrazierPackets.registerPackets();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::hackyHackToByPassLoadingOrder);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommonStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupServerStuff);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ProjectBrazierClient::modConstructor);


        DecorRegistrar.register();
        //Decals
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(DecalRegistry::registerRegistry);
//        MinecraftForge.EVENT_BUS.addGenericListener(Chunk.class, CapabilityAttachListener::attachChunkCapability);
//        CapabilityManager.INSTANCE.register(DecalChunk.class, new DecalChunkStorage(), () -> new DecalChunk(0, 256)); //TODO 1.17 update the default to new default heights.
    }

    public void hackyHackToByPassLoadingOrder(RegistryEvent.NewRegistry event){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BrazierBlocks.preRegistry();
        BrazierBlockEntities.preRegistry();
        BrazierItems.preRegistry();
        BrazierContainers.preRegistry();
        BrazierEntities.preRegistry();
        BrazierSounds.preRegistry();

        BrazierRegistries.BLOCKS.register(modEventBus);
        modEventBus.addListener(BrazierBlocks::postRegistry);

        BrazierRegistries.BLOCKS_NO_ITEMS.register(modEventBus);
        modEventBus.addListener(BrazierBlockEntities::postRegistry);

        BrazierRegistries.ITEMS.register(modEventBus);
        modEventBus.addGenericListener(Item.class, BrazierItems::registerItemBlocks);
        modEventBus.addListener(BrazierItems::postRegistry);

        BrazierRegistries.CONTAINERS.register(modEventBus);
        modEventBus.addListener(BrazierContainers::postRegistry);

        BrazierRegistries.ENTITIES.register(modEventBus);
        modEventBus.addListener(BrazierEntities::postRegistry);

        BrazierRegistries.SOUNDS.register(modEventBus);
        modEventBus.addListener(BrazierSounds::postRegistry);
    }

    public void setupCommonStuff(FMLCommonSetupEvent event) {}

    public void setupServerStuff(FMLDedicatedServerSetupEvent event) {}
}
