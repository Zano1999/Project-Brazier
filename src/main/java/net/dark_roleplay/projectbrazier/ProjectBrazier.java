package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.projectbrazier.features.model_loaders.axis_connected_models.AxisConnectedModel;
import net.dark_roleplay.projectbrazier.features.model_loaders.emissive.EmissiveModel;
import net.dark_roleplay.projectbrazier.features.model_loaders.simple_pane_conneted_model.SimplePaneConnectedModel;
import net.dark_roleplay.projectbrazier.handler.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ProjectBrazier.MODID)
public class ProjectBrazier {

    public static final String MODID = "projectbrazier";


    public ProjectBrazier() {
        //Biomes o' Plenty Support
        //JsonReader reader = new JsonReader(new InputStreamReader(Marg.class.getClassLoader().getResourceAsStream("data/bop_woods.json")));
        //Marg.MARG_GSON.fromJson(reader, Material.class);

        MedievalNetworking.registerPackets();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::hackyHackToByPassLoadingOrder);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommonStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupServerStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClientStuff);
    }

    public void hackyHackToByPassLoadingOrder(RegistryEvent.NewRegistry event){

        MedievalBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalBlocks.BLOCKS_NO_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MedievalItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, MedievalItems::registerItemBlocks);
        MedievalTileEntities.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
//        MedievalContainers.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalSounds.SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public void setupCommonStuff(FMLCommonSetupEvent event) {
    }

    public void setupServerStuff(FMLDedicatedServerSetupEvent event) {
    }

    public void setupClientStuff(FMLClientSetupEvent event) {

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {

            MedievalKeybinds.registerKeybinds(event);
            ModelLoaderRegistry.registerLoader(new ResourceLocation(MODID, "simple_pane_connected_model"), new SimplePaneConnectedModel.Loader());
            ModelLoaderRegistry.registerLoader(new ResourceLocation(MODID, "axis_connected_model"), new AxisConnectedModel.Loader());
            ModelLoaderRegistry.registerLoader(new ResourceLocation(MODID, "emissive"), new EmissiveModel.Loader());
            ProjectBrazierClient.registerRenderLayers();
        });
    }
}
