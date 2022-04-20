package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.experimental_features.builtin_mixed_model.BuiltinMixedModel;
import net.dark_roleplay.projectbrazier.experimental_features.chopping_block.ChoppingBlockBlockEntityRenderer;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItemListeners;
import net.dark_roleplay.projectbrazier.feature.registrars.*;
import net.dark_roleplay.projectbrazier.feature_client.blockentityrenderers.BarrelBlockEntityRenderer;
import net.dark_roleplay.projectbrazier.feature_client.blockentityrenderers.ZiplineBlockEntityRenderer;
import net.dark_roleplay.projectbrazier.feature_client.entity_renderers.ZiplineEntityRenderer;
import net.dark_roleplay.projectbrazier.feature_client.listeners.ResourceReloadUtil;
import net.dark_roleplay.projectbrazier.feature_client.listeners.TertiaryInteractionListener;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.axis_connected_models.AxisConnectedModel;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader.RoofModelLoader;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.emissive.EmissiveModel;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model.PaneCornerModel;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.quality_model.QualityModelLoader;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.simple_pane_conneted_model.SimplePaneConnectedModel;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.dark_roleplay.projectbrazier.feature_client.screens.GeneralContainerScreen;
import net.dark_roleplay.projectbrazier.util.MaterialRegistryObject;
import net.dark_roleplay.projectbrazier.feature_client.entity_renderers.SittableEntityRenderer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Map;

public class ProjectBrazierClient {

	public static void modConstructor(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::setupClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ProjectBrazierClient::registerModelLoaders);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ResourceReloadUtil::registerReloadListeners);
//		MinecraftForge.EVENT_BUS.addListener(DecorListener::bakeChunk);
//		FMLJavaModLoadingContext.get().getModEventBus().addListener(DecorClientListener::registerModels);

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
		ItemProperties.register(BrazierItems.ROPE.get(), new ResourceLocation(ProjectBrazier.MODID, "stack_size"), (stack, level, entity, val) -> stack.getCount());
		//TODO Experimental
//		PassiveScreenHelper.editKeybinds();

		MenuScreens.register(BrazierContainers.GENERAL_CONTAINER.get(), GeneralContainerScreen::new);
	}

	public static void registerModelLoaders(ModelRegistryEvent event){
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "emissive"), new EmissiveModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "quality_model"), new QualityModelLoader());

		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "roof"), new RoofModelLoader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "simple_pane_connected_model"), new SimplePaneConnectedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "axis_connected_model"), new AxisConnectedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "builtin_mixed"), new BuiltinMixedModel.Loader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(ProjectBrazier.MODID, "pane_corner_model"), new PaneCornerModel.Loader());

		ForgeModelBakery.addSpecialModel(new ResourceLocation(ProjectBrazier.MODID, "entity/zipline_handle"));


		registerItemOverrides();
	}

	public static void registerRenderLayers(){
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.CLAY_IN_GRASSY_DIRT.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.GLIMMERTAIL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.CAULIFLOWER.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.WHITE_CABBAGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.HANGING_HORN.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.IRON_BRAZIER_COAL.get(), layer -> layer == RenderType.cutout() || layer == RenderType.solid());
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.IRON_FIRE_BOWL.get(), layer -> layer == RenderType.cutout() || layer == RenderType.solid());
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.SOUL_IRON_BRAZIER_COAL.get(), layer -> layer == RenderType.cutout() || layer == RenderType.solid());
		ItemBlockRenderTypes.setRenderLayer(BrazierBlocks.SOUL_IRON_FIRE_BOWL.get(), layer -> layer == RenderType.cutout() || layer == RenderType.solid());

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
				.forEach(b -> ItemBlockRenderTypes.setRenderLayer((Block) b, RenderType.cutoutMipped()));

		//TODO Move to TER registration event?
//		ClientRegistry.bindTileEntityRenderer(BrazierBlockEntities.DRAWBRODGE_ANCHOR.get(), DrawbridgeAnchorTileEntityRenderer::new);
		BlockEntityRenderers.register(BrazierBlockEntities.BARREL_BLOCK_ENTITY.get(), BarrelBlockEntityRenderer::new);
		BlockEntityRenderers.register(BrazierBlockEntities.ZIPLINE_ANCHOR.get(), ZiplineBlockEntityRenderer::new);

//		BlockEntityRenderers.register(BrazierBlockEntities.CHOPPING_BLOCK.get(), ChoppingBlockBlockEntityRenderer::new);


		EntityRenderers.register(BrazierEntities.SITTABLE.get(), SittableEntityRenderer::new);
		EntityRenderers.register(BrazierEntities.ZIPLINE.get(), ZiplineEntityRenderer::new);
	}

	private static void setRenderLayer(RenderType type, Map<MargMaterial, RegistryObject<Block>>... materialBlockObjects){
		for(Map<MargMaterial, RegistryObject<Block>> materialBlocks : materialBlockObjects){
			for(RegistryObject<Block> blockObj : materialBlocks.values()){
				ItemBlockRenderTypes.setRenderLayer(blockObj.get(), type);
			}
		}
	}

	private static void setRenderLayer(RenderType type, RegistryObject<Block>... blockObjects){
		for(RegistryObject<Block> blockObj : blockObjects){
			ItemBlockRenderTypes.setRenderLayer(blockObj.get(), type);
		}
	}

	public static void registerItemOverrides(){
		ItemProperties.register(BrazierItems.STONE_ARROW_SLIT.get(), new ResourceLocation(ProjectBrazier.MODID, "variant"), (stack, world, entity, val) -> {
			if (entity != null && entity instanceof Player)
				return ((SelectiveBlockItem)stack.getItem()).getCurrentIndex(((Player) entity).getGameProfile());
			return 0;
		});

		ItemProperties.register(BrazierItems.DEEPSLATE_ARROW_SLIT.get(), new ResourceLocation(ProjectBrazier.MODID, "variant"), (stack, world, entity, val) -> {
			if (entity != null && entity instanceof Player)
				return ((SelectiveBlockItem)stack.getItem()).getCurrentIndex(((Player) entity).getGameProfile());
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
				(state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColor.get(0.5D, 1.0D),
				BrazierBlocks.GLIMMERTAIL.get());

		event.getBlockColors().register((state, reader, pos, color) -> {
			return reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColor.get(0.5D, 1.0D);
		}, BrazierBlocks.CLAY_IN_GRASSY_DIRT.get());
	}
}
