package net.dark_roleplay.medieval;

import net.dark_roleplay.IModule;
import net.dark_roleplay.bedrock_entities.tester.ModelTesterTileEntity;
import net.dark_roleplay.bedrock_entities.tester.ModelTesterTileEntityRenderer;
import net.dark_roleplay.library.networking.NetworkHelper;
import net.dark_roleplay.medieval.handler.KeybindHandler;
import net.dark_roleplay.medieval.handler_2.*;
import net.dark_roleplay.medieval.holders.MedievalConfigs;
import net.dark_roleplay.medieval.objects.blocks.building.roofs.RoofTileEntity;
import net.dark_roleplay.medieval.objects.blocks.building.roofs.RoofTileEntityRenderer;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntity;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntityRenderer;
import net.dark_roleplay.medieval.objects.blocks.utility.chopping_block.ChoppingTileEntity;
import net.dark_roleplay.medieval.objects.blocks.utility.chopping_block.ChoppingTileEntityRenderer;
import net.dark_roleplay.medieval.objects.packets.RoadSignEditSignPacket;
import net.dark_roleplay.medieval.objects.packets.RoadSignPlacementPacket;
import net.dark_roleplay.tertiary_interactor.TertiaryInteractionModule;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.io.IOException;

@Mod(DarkRoleplayMedieval.MODID)
public class DarkRoleplayMedieval {

	public static final String MODID = "drpmedieval";
	public static SimpleChannel MOD_CHANNEL;

	private IModule[] modules = {new TertiaryInteractionModule()};

	public DarkRoleplayMedieval() {
		//Biomes o' Plenty Support
		//JsonReader reader = new JsonReader(new InputStreamReader(Marg.class.getClassLoader().getResourceAsStream("data/bop_woods.json")));
		//Marg.MARG_GSON.fromJson(reader, Material.class);

		MedievalBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MedievalItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MedievalTileEntities.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		MedievalEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		MedievalContainers.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());

		DarkRoleplayMedieval.MOD_CHANNEL = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(DarkRoleplayMedieval.MODID, "main_channel"))
				.clientAcceptedVersions("1.0"::equals)
				.serverAcceptedVersions("1.0"::equals)
				.networkProtocolVersion(() -> "1.0")
				.simpleChannel();

		NetworkHelper.initChannel(MOD_CHANNEL);
		NetworkHelper.registerPacket(RoadSignPlacementPacket.class);
		NetworkHelper.registerPacket(RoadSignEditSignPacket.class);

		for(IModule module : modules)
			module.registerPackets();

		NetworkHelper.clearChannel();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MedievalConfigs.WORLD_GENS_SPEC, "World Generation.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MedievalConfigs.Misc.REGENERATING_ORES_SPEC, "Regenerating Ores.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommonStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupServerStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClientStuff);

        BatModelExporter exporter = new BatModelExporter();
		try {
			exporter.writeModel();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setupCommonStuff(FMLCommonSetupEvent event) {
//		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> MedievalGuis::openGui);
	}
	
	public void setupServerStuff(FMLDedicatedServerSetupEvent event) {
		
	}
	
	public void setupClientStuff(FMLClientSetupEvent event) {
		OBJLoader.INSTANCE.addDomain(MODID);
		
		//ModelLoaderRegistry.registerLoader(ModelQualityModelLoader.INSTANCE);
		ClientRegistry.bindTileEntitySpecialRenderer(RoadSignTileEntity.class, new RoadSignTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(ChoppingTileEntity.class, new ChoppingTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(RoofTileEntity.class, new RoofTileEntityRenderer());


		ClientRegistry.bindTileEntitySpecialRenderer(ModelTesterTileEntity.class, new ModelTesterTileEntityRenderer());
		ClientRegistry.registerKeyBinding(KeybindHandler.BLOCK_INTERACTOR);
	}

//	@EventHandler
//	public static void init(FMLInitializationEvent event) {
//		//TODO Port to data driven crafting system
//		ChoppingBlockActivation.firewoodRecipes = new HashMap<ItemStack, ItemStack>();
//
//		VillagerRegistryHandler.init(event);
//		MissingMappingRegistryHandler.init(event);
//		PacketRegistryHandler.init();
//		proxy.init(event);
//
//		((UnlitWallMount)MedievalBlocks.CANDLE_HOLDER_UNLIT).init(MedievalBlocks.CANDLE_HOLDER_LIT, Item.getItemFromBlock(MedievalBlocks.BEESWAX_CANDLE));;
//		((LitWallMount)MedievalBlocks.CANDLE_HOLDER_LIT).init(MedievalBlocks.CANDLE_HOLDER_UNLIT, Item.getItemFromBlock(MedievalBlocks.BEESWAX_CANDLE));
//		((UnlitWallMount)MedievalBlocks.TORCH_HOLDER_UNLIT).init(MedievalBlocks.TORCH_HOLDER_LIT, Item.getItemFromBlock(MedievalBlocks.TORCH));
//		((LitWallMount)MedievalBlocks.TORCH_HOLDER_LIT).init(MedievalBlocks.TORCH_HOLDER_UNLIT, Item.getItemFromBlock(MedievalBlocks.TORCH));
//		((EmptyWallMount)MedievalBlocks.CANDLE_HOLDER_EMPTY).init(MedievalBlocks.CANDLE_HOLDER_UNLIT, Item.getItemFromBlock(MedievalBlocks.BEESWAX_CANDLE));
//		((EmptyWallMount)MedievalBlocks.TORCH_HOLDER_EMPTY).init(MedievalBlocks.TORCH_HOLDER_UNLIT, Item.getItemFromBlock(MedievalBlocks.TORCH));
//
//		((AdvancedOre)MedievalBlocks.SALPETER_ORE).init(MedievalItems.SALPETER_ORE_CHUNK);
//		((AdvancedOre)MedievalBlocks.SILVER_ORE).init(MedievalItems.SILVER_ORE_CHUNK);
//		((AdvancedOre)MedievalBlocks.TIN_ORE).init(MedievalItems.TIN_ORE_CHUNK);
//		((AdvancedOre)MedievalBlocks.COPPER_ORE).init(MedievalItems.COPPER_ORE_CHUNK);
//		((AdvancedOre)MedievalBlocks.SULFUR_ORE).init(MedievalItems.SULFUR_ORE_CHUNK);
//
//		((RegeneratingOre)MedievalBlocks.REGENERATING_EMERALD_ORE).setOre(MedievalBlocks.EMERALD_ORE);
//		((RegeneratingOre)MedievalBlocks.REGENERATING_DIAMOND_ORE).setOre(MedievalBlocks.DIAMOND_ORE);
//		((RegeneratingOre)MedievalBlocks.REGENERATING_LAPIS_ORE).setOre(MedievalBlocks.LAPIS_ORE);
//		((RegeneratingOre)MedievalBlocks.REGENERATING_REDSTONE_ORE).setOre(MedievalBlocks.REDSTONE_ORE);
//		((RegeneratingOre)MedievalBlocks.REGENERATING_GOLD_ORE).setOre(MedievalBlocks.GOLD_ORE);
//		((RegeneratingOre)MedievalBlocks.REGENERATING_IRON_ORE).setOre(MedievalBlocks.IRON_ORE);
//		((RegeneratingOre)MedievalBlocks.REGENERATING_COAL_ORE).setOre(MedievalBlocks.COAL_ORE);
//		((RegeneratingOre)MedievalBlocks.REGENERATING_QUARTZ_ORE).setOre(MedievalBlocks.QUARTZ_ORE);
//
//		GameRegistry.addSmelting(MedievalItems.WHEAT_DOUGH, new ItemStack(MedievalItems.BREAD), 0.1f);
//		GameRegistry.addSmelting(MedievalItems.BARLEY_DOUGH, new ItemStack(MedievalItems.BREAD), 0.1f);
//		GameRegistry.addSmelting(MedievalItems.RAW_WOLF, new ItemStack(MedievalItems.COOKED_WOLF), 0.1f);
//		GameRegistry.addSmelting(MedievalItems.RAW_CATFISH, new ItemStack(MedievalItems.COOKED_CATFISH), 0.1f);
//		GameRegistry.addSmelting(MedievalItems.WHEAT_PUMPKIN_DOUGH, new ItemStack(MedievalItems.PUMPKIN_BREAD), 0.1f);
//		GameRegistry.addSmelting(MedievalItems.BARLEY_PUMPKIN_DOUGH, new ItemStack(MedievalItems.PUMPKIN_BREAD), 0.1f);
//		GameRegistry.addSmelting(Item.getItemFromBlock(MedievalBlocks.OBSIDIAN), new ItemStack(MedievalBlocks.OBSIDIAN_GLASS), 0.1f);
//		GameRegistry.addSmelting(Item.getItemFromBlock(MedievalBlocks.UNFIRED_VASE), new ItemStack(MedievalBlocks.FIRED_VASE), 0.1f);
//	}

//	public static class ClientProxy{
//
//		public static int TELESCOPE_LEVEL = 0;
//
//		//Searching for a fast way to fix the multiple mods bug
//		//Comming close!
//
//		@Override
//		public void preInit(FMLPreInitializationEvent event) {
//			ModelLoaderRegistry.registerLoader(new ModelLoaderHangingBridge());
//			ModelLoaderRegistry.registerLoader(new ModelLoaderRopeFence());
//			ModelLoaderRegistry.registerLoader(new CustomBlockstateLoader());
//			ModelLoaderRegistry.registerLoader(new ModelLoaderTimberedClay());
//
////			RenderingRegistry.<Entity_Fox>registerEntityRenderingHandler(Entity_Fox.class, Render_Fox.FACTORY);
////			RenderingRegistry.<Wheelbarrel>registerEntityRenderingHandler(Wheelbarrel.class, WheelbarrelRenderer.FACTORY);
//			ClientRegistry.bindTileEntityRenderer(TileEntityRoof.class, new SpecialRendererRoof());
//			ClientRegistry.bindTileEntityRenderer(TileEntityClockCore.class, new SpecialRendererClockCore());
//			ClientRegistry.bindTileEntityRenderer(TileEntityShopSign.class, new SpecialRendererShopSign());
//			ClientRegistry.bindTileEntityRenderer(TileEntityShelf.class, new SpecialRendererShelf(Minecraft.getInstance().getRenderItem()));
//			ClientRegistry.bindTileEntityRenderer(TileEnittyUniversalShelf.class, new SpecialRendererWallShelf(Minecraft.getInstance().getRenderItem()));
//			ClientRegistry.bindTileEntityRenderer(TileEntityFluidBarrel.class, new SpecialRendererFluidBarrel());
//			ClientRegistry.bindTileEntityRenderer(TileEntityFlowerContainer.class, new SpecialRendererFlowerContainer());
//			ClientRegistry.bindTileEntityRenderer(TE_Banner.class, new TESR_Banner());
//
//			ClientRegistry.bindTileEntityRenderer(TE_BuildingScanner.class, new TESR_BuildingScanner());
//
//
//			ClientRegistry.bindTileEntityRenderer(TileEntitySimpleChest.class, new AnimationTESR<TileEntitySimpleChest>(){
//				@Override
//			    public void renderTileEntityFast(TileEntitySimpleChest te, double x, double y, double z, float partialTick, int breakStage, float partial, BufferBuilder renderer) {
//					super.renderTileEntityFast(te, x, y, z, partialTick, breakStage, partial, renderer);
//				}
//
//				@Override
//				public void handleEvents(TileEntitySimpleChest chest, float time, Iterable<Event> pastEvents){
//					chest.handleEvents(time, pastEvents);
//				}
//			});
//			ClientRegistry.bindTileEntityRenderer(TileEntityHoneyCentrifuge.class, new SpecialRendererHoneyCentrifuge());
//			ClientRegistry.bindTileEntityRenderer(TileEntityGrindstone.class, new SpecialRenderGrindstone());
//
//
//			ClientRegistry.bindTileEntityRenderer(TileEntityAnvil.class, new SpecialRenderAnvil());
//			ClientRegistry.bindTileEntityRenderer(TileEntityHangingCauldron.class, new SpecialRenderHangingCauldron());
//			ClientRegistry.bindTileEntityRenderer(TileEntityCauldron.class, new SpecialRenderCauldron());
//			ClientRegistry.bindTileEntityRenderer(TileEntityChain.class, new SpecialRenderChain());
//			ClientRegistry.bindTileEntityRenderer(TileEntityHook.class, new SpecialRenderHook());
//			ClientRegistry.bindTileEntityRenderer(TileEntityKeyHanging.class, new SpecialRenderKeyHanging());
//			ClientRegistry.bindTileEntityRenderer(TileEntityTarget.class, new SpecialRenderTarget());
//			ClientRegistry.bindTileEntityRenderer(TileEntityRopeAnchor.class, new SpecialRenderRopeAnchor());
//			ClientRegistry.bindTileEntityRenderer(TileEntityFirepit.class, new SpecialRenderFirepit());
//
//			ClientRegistry.registerKeyBinding(Keybinds.debugging);
//		}
//
//		@Override
//		public void init(FMLInitializationEvent event) {
//			Minecraft.getInstance().getResourceManager();
//			ColorHandlerDryClayGrass color = new ColorHandlerDryClayGrass();
//			Minecraft.getInstance().getBlockColors().register(color, MedievalBlocks.DRY_CLAY_GRASS);
//			Minecraft.getInstance().getItemColors().register((IItemColor) (stack, tintIndex) -> {
//			    BlockState BlockState = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
//			    return color.colorMultiplier(BlockState, (IBlockAccess)null, (BlockPos)null, tintIndex);
//			}, MedievalBlocks.DRY_CLAY_GRASS);
//			Minecraft.getInstance().getItemColors().register(new ColorHandlerPaintBrush(), MedievalItems.DIRTY_PAINTBRUSH);
//
//			Minecraft.getInstance().getBlockColors().register(new ColorHandlerRoofs(),
////				MedievalBlocks.WHITE_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.ORANGE_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.MAGENTA_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.LIGHT_BLUE_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.YELLOW_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.LIGHT_GREEN_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.PINK_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.GRAY_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.LIGHT_GRAY_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.CYAN_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.PURPLE_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.BLUE_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.BROWN_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.GREEN_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.RED_CLAY_SHINGLE_ROOF,
//				MedievalBlocks.Roofs.BLACK_CLAY_SHINGLE_ROOF
//			);
//
//			Minecraft.getInstance().getItemColors().register(new ColorHandlerRoofItems(),
////					MedievalBlocks.WHITE_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.ORANGE_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.MAGENTA_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.LIGHT_BLUE_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.YELLOW_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.LIGHT_GREEN_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.PINK_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.GRAY_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.LIGHT_GRAY_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.CYAN_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.PURPLE_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.BLUE_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.BROWN_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.GREEN_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.RED_CLAY_SHINGLE_ROOF,
//					MedievalBlocks.Roofs.BLACK_CLAY_SHINGLE_ROOF
//				);
//		}
//
//		@Override
//		public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters){
//	        return ModelLoaderRegistry.loadASM(location, parameters);
//	    }
//	}
}
