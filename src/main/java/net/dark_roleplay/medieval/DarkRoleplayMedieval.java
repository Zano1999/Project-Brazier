package net.dark_roleplay.medieval;

import net.dark_roleplay.IModule;
import net.dark_roleplay.medieval.handler.*;
import net.dark_roleplay.medieval.holders.MedievalConfigs;
import net.dark_roleplay.medieval.objects.guis.generic_container.GenericContainerGui;
import net.dark_roleplay.tertiary_interactor.TertiaryInteractionModule;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DarkRoleplayMedieval.MODID)
public class DarkRoleplayMedieval {

    public static final String MODID = "drpmedieval";

    private IModule[] modules = {new TertiaryInteractionModule()};

    public DarkRoleplayMedieval() {
        //Biomes o' Plenty Support
        //JsonReader reader = new JsonReader(new InputStreamReader(Marg.class.getClassLoader().getResourceAsStream("data/bop_woods.json")));
        //Marg.MARG_GSON.fromJson(reader, Material.class);

        MedievalNetworking.initNetworking();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MedievalConfigs.SKILL_CONFIG_SPEC, "Abilities.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::hackyHackToByPassLoadingOrder);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommonStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupServerStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClientStuff);

    }

    public void hackyHackToByPassLoadingOrder(RegistryEvent.NewRegistry event){
        MedievalBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalTileEntities.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MedievalContainers.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public void setupCommonStuff(FMLCommonSetupEvent event) {
    }

    public void setupServerStuff(FMLDedicatedServerSetupEvent event) {
    }

    public void setupClientStuff(FMLClientSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> DarkRoleplayMedievalClient.registerRenderLayers());
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {

            ScreenManager.registerFactory(MedievalContainers.GENERIC_CONTAINER.get(), GenericContainerGui::new);

            //ModelLoaderRegistry.registerLoader(ModelQualityModelLoader.INSTANCE);
//			ClientRegistry.bindTileEntitySpecialRenderer(RoadSignTileEntity.class, new RoadSignTileEntityRenderer());

            //TODO 1.15 update
//            ClientRegistry.bindTileEntityRenderer(ChoppingTileEntity.class, new ChoppingTileEntityRenderer());
//            ClientRegistry.bindTileEntityRenderer(RoofTileEntity.class, new RoofTileEntityRenderer());
//
//
//			ClientRegistry.bindTileEntitySpecialRenderer(ModelTesterTileEntity.class, new ModelTesterTileEntityRenderer());
//			ClientRegistry.registerKeyBinding(CrafterKeybinds.BLOCK_INTERACTOR);
//			ClientRegistry.registerKeyBinding(CrafterKeybinds.DODGE);
        });
    }
}
