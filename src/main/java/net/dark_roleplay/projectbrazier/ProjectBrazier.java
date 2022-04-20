package net.dark_roleplay.projectbrazier;

import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.creative_tabs.CreativeTabFixedData;
import net.dark_roleplay.projectbrazier.feature.mechanics.spreader.SpreadBehaviors;
import net.dark_roleplay.projectbrazier.feature.mechanics.spreader.SpreaderType;
import net.dark_roleplay.projectbrazier.feature.player_actions.zipline_creation.ZiplineCreationAction;
import net.dark_roleplay.projectbrazier.feature.registrars.*;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierWorldGen;
import net.dark_roleplay.projectbrazier.tests.GameTestDemo;
import net.minecraft.SharedConstants;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.gametest.framework.TestCommand;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(ProjectBrazier.MODID)
public class ProjectBrazier {

	public static final String MODID = "projectbrazier";

	static{
		if(!FMLEnvironment.production){
			//SharedConstants.IS_RUNNING_IN_IDE = true;
		}
	}

	public ProjectBrazier() {
		if(!ModList.get().isLoaded("marg")) return;

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		BrazierPackets.registerPackets();

		MinecraftForge.EVENT_BUS.addListener(BrazierCommands::registerCommands);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(BrazierWorldGen::setup);
		MinecraftForge.EVENT_BUS.addListener(BrazierWorldGen::biomeLoad);

		MinecraftForge.EVENT_BUS.addListener(ProjectBrazier::registerCommands);


		modEventBus.addListener(CreativeTabFixedData::load);
		modEventBus.addGenericListener(Item.class, BrazierRegistries.ITEMS_FD::registryListener);

		BrazierRegistries.BLOCKS.register(modEventBus);
		BrazierRegistries.BLOCKS_NO_ITEMS.register(modEventBus);
		BrazierRegistries.BLOCK_ENTITIES.register(modEventBus);
		BrazierRegistries.ITEMS.register(modEventBus);
		BrazierRegistries.CONTAINERS.register(modEventBus);
		BrazierRegistries.ENTITIES.register(modEventBus);
		BrazierRegistries.SOUNDS.register(modEventBus);
		BrazierRegistries.FEATURES.register(modEventBus);

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

	public void hackyHackToByPassLoadingOrder(NewRegistryEvent event) {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		BrazierBlocks.preRegistry();
		BrazierBlockEntities.preRegistry();
		BrazierItems.preRegistry();
		BrazierContainers.preRegistry();
		BrazierEntities.preRegistry();
		BrazierSounds.preRegistry();
		BrazierWorldGen.preRegistry();

		modEventBus.addListener(BrazierBlocks::postRegistry);
		modEventBus.addListener(BrazierBlockEntities::postRegistry);
		modEventBus.addGenericListener(Item.class, BrazierItems::registerItemBlocks);
		modEventBus.addListener(BrazierItems::postRegistry);
		modEventBus.addListener(BrazierContainers::postRegistry);
		modEventBus.addListener(BrazierEntities::postRegistry);
		modEventBus.addListener(BrazierSounds::postRegistry);
	}

	public void setupCommonStuff(FMLCommonSetupEvent event) {
		SpreadBehaviors.addSpreaderBehavior(
				BrazierBlocks.CLAY_IN_DIRT.get(),
				SpreaderType.GRASS,
				(state, level, pos) ->
						BrazierBlocks.CLAY_IN_GRASSY_DIRT.get().defaultBlockState()
								.setValue(BlockStateProperties.SNOWY, level.getBlockState(pos.above()).is(Blocks.SNOW))
		);

		SpreadBehaviors.addSpreaderBehavior(
				BrazierBlocks.CLAY_IN_GRASSY_DIRT.get(),
				SpreaderType.REVERT,
				(state, level, pos) -> BrazierBlocks.CLAY_IN_DIRT.get().defaultBlockState()
		);


		SpreadBehaviors.addSpreaderBehavior(
				Blocks.DIRT,
				SpreaderType.GRASS,
				(state, level, pos) ->
						Blocks.GRASS_BLOCK.defaultBlockState()
								.setValue(BlockStateProperties.SNOWY, level.getBlockState(pos.above()).is(Blocks.SNOW))
		);
	}

	public static void registerCommands(RegisterCommandsEvent event){
		TestCommand.register(event.getDispatcher());
		GameTestRegistry.register(GameTestDemo.class);
	}

	public void setupServerStuff(FMLDedicatedServerSetupEvent event) {
	}
}
