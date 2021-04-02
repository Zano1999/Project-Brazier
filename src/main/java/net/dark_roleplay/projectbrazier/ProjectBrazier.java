package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.projectbrazier.features.blocks.barrel.BarrelBlock;
import net.dark_roleplay.projectbrazier.handler.*;
import net.minecraft.item.Item;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

@Mod(ProjectBrazier.MODID)
public class ProjectBrazier {

    public static final String MODID = "projectbrazier";


    public ProjectBrazier() {
        MedievalNetworking.registerPackets();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::hackyHackToByPassLoadingOrder);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommonStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupServerStuff);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ProjectBrazierClient::modConstructor);

        //Decals
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(DecalRegistry::registerRegistry);
//        MinecraftForge.EVENT_BUS.addGenericListener(Chunk.class, CapabilityAttachListener::attachChunkCapability);
//        CapabilityManager.INSTANCE.register(DecalChunk.class, new DecalChunkStorage(), () -> new DecalChunk(0, 256)); //TODO 1.17 update the default to new default heights.
    }

    public void hackyHackToByPassLoadingOrder(RegistryEvent.NewRegistry event){

        MedievalBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalBlocks.BLOCKS_NO_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MedievalItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, MedievalItems::registerItemBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, MedievalBlocks::postBlockRegistryCallback);

        MedievalTileEntities.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalContainers.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalSounds.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public void setupCommonStuff(FMLCommonSetupEvent event) {
        MedievalBlocks.planksOnly.forEach(material -> {
            BarrelBlock openBarrel = (BarrelBlock) MedievalBlocks.OPEN_BARREL.get(material).get();
            BarrelBlock closedBarrel = (BarrelBlock) MedievalBlocks.CLOSED_BARREL.get(material).get();

            openBarrel.setOtherBlock(closedBarrel);
            closedBarrel.setOtherBlock(openBarrel);
        });

        DebugChunkGenerator.ALL_VALID_STATES.clear();
        DebugChunkGenerator.ALL_VALID_STATES.addAll(ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals(MODID)).flatMap((p_236067_0_) -> {
            return p_236067_0_.getStateContainer().getValidStates().stream();
        }).collect(Collectors.toList()));
    }

    public void setupServerStuff(FMLDedicatedServerSetupEvent event) {
    }
}
