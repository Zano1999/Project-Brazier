package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel.BuiltinMixedModel;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.screens.CraftingScreen;
import net.dark_roleplay.projectbrazier.experimental_features.walking_gui.PassiveScreenHelper;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierContainers;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierEntities;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorClientListener;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorListener;
import net.dark_roleplay.projectbrazier.feature_client.blockentityrenderers.BarrelBlockEntityRenderer;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.axis_connected_models.AxisConnectedModel;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.emissive.EmissiveModel;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.quality_model.QualityModelLoader;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.roof_model_loader.RoofModelLoader;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.simple_pane_conneted_model.SimplePaneConnectedModel;
import net.dark_roleplay.projectbrazier.feature_client.screens.GeneralContainerScreen;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntity;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

public class ProjectBrazierClient {

	public static void modConstructor(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::setupClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::registerModelLoaders);
		MinecraftForge.EVENT_BUS.addListener(DecorListener::bakeChunk);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(DecorClientListener::registerModels);

	}

	public static void setupClientStuff(FMLClientSetupEvent event) {
		BrazierKeybinds.registerKeybinds(event);
		ProjectBrazierClient.registerRenderLayers();
		//TODO Experimental
		PassiveScreenHelper.editKeybinds();

		ScreenManager.registerFactory(BrazierContainers.GENERAL_CONTAINER.get(), GeneralContainerScreen::new);
		ScreenManager.registerFactory(BrazierContainers.CRAFTING_PLAYER_CONTAINER.get(), CraftingScreen::new);
	}

	public static void registerModelLoaders(ModelRegistryEvent event){
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "roof"), new RoofModelLoader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "quality_model"), new QualityModelLoader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "simple_pane_connected_model"), new SimplePaneConnectedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "axis_connected_model"), new AxisConnectedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "emissive"), new EmissiveModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "builtin_mixed"), new BuiltinMixedModel.Loader());
	}

	public static void registerRenderLayers(){
		RenderTypeLookup.setRenderLayer(BrazierBlocks.HANGING_HORN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.IRON_BRAZIER_COAL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.IRON_FIRE_BOWL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.SOUL_IRON_BRAZIER_COAL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.SOUL_IRON_FIRE_BOWL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());

		//TODO Move to TER registration event?
		for(RegistryObject<Block> b : BrazierBlocks.FLOWER_BUCKET.values())
			RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());

//		ClientRegistry.bindTileEntityRenderer(BrazierBlockEntities.DRAWBRODGE_ANCHOR.get(), DrawbridgeAnchorTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(BrazierBlockEntities.BARREL_BLOCK_ENTITY.get(), BarrelBlockEntityRenderer::new);

		RenderingRegistry.<SittableEntity>registerEntityRenderingHandler(BrazierEntities.SITTABLE.get(), SittableEntityRenderer::new);
	}

	private static void setRenderLayer(RenderType type, Map<MargMaterial, RegistryObject<Block>>... materialBlockObjects){
		for(Map<MargMaterial, RegistryObject<Block>> materialBlocks : materialBlockObjects){
			for(RegistryObject<Block> blockObj : materialBlocks.values()){
				RenderTypeLookup.setRenderLayer(blockObj.get(), type);
			}
		}
	}

	private static void setRenderLayer(RenderType type, RegistryObject<Block>... blockObjects){
		for(RegistryObject<Block> blockObj : blockObjects){
			RenderTypeLookup.setRenderLayer(blockObj.get(), type);
		}
	}

	public static void registerItemOverrides(){
//		MedievalItems.TIMBERING_NOTES.get().addPropertyOverride(new ResourceLocation(ProjectBrazier.MODID, "positions"), (stack, world, entity) -> {
//			CompoundNBT nbt = stack.getOrCreateTag();
//			float res = 0;
//			if (!nbt.contains("TimberingData")) return 0;
//			res += nbt.getCompound("TimberingData").contains("Start") ? 0.3 : 0;
//			res += nbt.getCompound("TimberingData").contains("Target") ? 0.6 : 0;
//			return res;
//		});
	}

}
