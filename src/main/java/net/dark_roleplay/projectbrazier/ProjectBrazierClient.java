package net.dark_roleplay.projectbrazier;

import com.google.common.eventbus.EventBus;
import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.experimental_features.BultinMixedModel.BuiltinMixedModel;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.screens.CraftingScreen;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItemListeners;
import net.dark_roleplay.projectbrazier.feature.registrars.*;
import net.dark_roleplay.projectbrazier.feature_client.listeners.TertiaryInteractionListener;
import net.dark_roleplay.projectbrazier.experimental_features.walking_gui.PassiveScreenHelper;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorClientListener;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorListener;
import net.dark_roleplay.projectbrazier.feature_client.blockentityrenderers.BarrelBlockEntityRenderer;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.axis_connected_models.AxisConnectedModel;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.emissive.EmissiveModel;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model.PaneCornerModel;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.quality_model.QualityModelLoader;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader.RoofModelLoader;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.simple_pane_conneted_model.SimplePaneConnectedModel;
import net.dark_roleplay.projectbrazier.feature_client.screens.GeneralContainerScreen;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.dark_roleplay.projectbrazier.util.MaterialRegistryObject;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntity;
import net.dark_roleplay.projectbrazier.util.sitting.SittableEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Arrays;
import java.util.Map;

public class ProjectBrazierClient {

	public static void modConstructor(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::setupClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::registerModelLoaders);
		MinecraftForge.EVENT_BUS.addListener(DecorListener::bakeChunk);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(DecorClientListener::registerModels);

		MinecraftForge.EVENT_BUS.addListener(TertiaryInteractionListener::renderBlockOverlay);
		MinecraftForge.EVENT_BUS.addListener(TertiaryInteractionListener::renderGameOverlay);
		MinecraftForge.EVENT_BUS.addListener(TertiaryInteractionListener::renderWorldLastEvent);

		MinecraftForge.EVENT_BUS.addListener(SelectiveBlockItemListeners::renderGameOverlay);
		MinecraftForge.EVENT_BUS.addListener(SelectiveBlockItemListeners::keyInput);
		MinecraftForge.EVENT_BUS.addListener(SelectiveBlockItemListeners::mouseScroll);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::registerBlockColors);
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
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "emissive"), new EmissiveModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "quality_model"), new QualityModelLoader());

		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "roof"), new RoofModelLoader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "simple_pane_connected_model"), new SimplePaneConnectedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "axis_connected_model"), new AxisConnectedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "builtin_mixed"), new BuiltinMixedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "pane_corner_model"), new PaneCornerModel.Loader());

		registerItemOverrides();
	}

	public static void registerRenderLayers(){
		RenderTypeLookup.setRenderLayer(BrazierBlocks.GLIMMERTAIL.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.CAULIFLOWER.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.WHITE_CABBAGE.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.HANGING_HORN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.IRON_BRAZIER_COAL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.IRON_FIRE_BOWL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.SOUL_IRON_BRAZIER_COAL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(BrazierBlocks.SOUL_IRON_FIRE_BOWL.get(), layer -> layer == RenderType.getCutout() || layer == RenderType.getSolid());

		MaterialRegistryObject[] cutouts = {
				BrazierBlocks.FLOWER_BARRELS,
				BrazierBlocks.FLOWER_BUCKET,
				BrazierBlocks.HOLLOW_LOG,
				BrazierBlocks.STRIPPED_HOLLOW_LOG,
				BrazierBlocks.WOOD_LATTICE_1,
				BrazierBlocks.WOOD_LATTICE_1_C,
				BrazierBlocks.WOOD_LATTICE_2,
				BrazierBlocks.WOOD_LATTICE_2_C,
				BrazierBlocks.WOOD_LATTICE_3,
				BrazierBlocks.WOOD_LATTICE_3_C,
				BrazierBlocks.WOOD_LATTICE_4,
				BrazierBlocks.WOOD_LATTICE_4_C,
				BrazierBlocks.WOOD_LATTICE_5,
				BrazierBlocks.WOOD_LATTICE_5_C
		};

		Arrays.stream(cutouts)
				.flatMap(b -> b.values().stream())
				.map(b -> ((RegistryObject<? extends Block>)b).get())
				.forEach(b -> RenderTypeLookup.setRenderLayer((Block) b, RenderType.getCutoutMipped()));

		//TODO Move to TER registration event?
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
		ItemModelsProperties.registerProperty(BrazierItems.STONE_ARROW_SLIT.get(), new ResourceLocation(ProjectBrazier.MODID, "variant"), (stack, world, entity) -> {
			if (entity != null && entity instanceof PlayerEntity)
				return ((SelectiveBlockItem)stack.getItem()).getCurrentIndex(((PlayerEntity) entity).getGameProfile());
			return 0;
		});

//		MedievalItems.TIMBERING_NOTES.get().addPropertyOverride(new ResourceLocation(ProjectBrazier.MODID, "positions"), (stack, world, entity) -> {
//			CompoundNBT nbt = stack.getOrCreateTag();
//			float res = 0;
//			if (!nbt.contains("TimberingData")) return 0;
//			res += nbt.getCompound("TimberingData").contains("Start") ? 0.3 : 0;
//			res += nbt.getCompound("TimberingData").contains("Target") ? 0.6 : 0;
//			return res;
//		});
	}

	public static void registerBlockColors(ColorHandlerEvent.Block event){
		event.getBlockColors().register(
				(state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColors.get(0.5D, 1.0D),
				BrazierBlocks.GLIMMERTAIL.get());
	}
}
