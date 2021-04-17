package net.dark_roleplay.projectbrazier.datagen.woods;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ProjectBrazier.MODID)
public class WoodDataGenerator extends BlockStateProvider {

	private static final String MODID = ProjectBrazier.MODID;
	private static final ResourceLocation CUBE_COLUMN = new ResourceLocation("minecraft", "block/cube_column");
	private static final ResourceLocation CUBE_ALL = new ResourceLocation("minecraft", "block/cube_all");


	public WoodDataGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, ProjectBrazier.MODID, exFileHelper);
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(new WoodDataGenerator(event.getGenerator(), event.getExistingFileHelper()));
	}

	@Override
	protected void registerStatesAndModels() {
		generateWoods("apple", new WoodGeneratorData(
				BrazierBlocks.APPLE_LOG,
				BrazierBlocks.STRIPPED_APPLE_LOG,
				BrazierBlocks.APPLE_PLANK,
				BrazierBlocks.APPLE_WOOD,
				BrazierBlocks.STRIPPED_APPLE_WOOD
		));
		generateWoods("orange", new WoodGeneratorData(
				BrazierBlocks.ORANGE_LOG,
				BrazierBlocks.STRIPPED_ORANGE_LOG,
				BrazierBlocks.ORANGE_PLANK,
				BrazierBlocks.ORANGE_WOOD,
				BrazierBlocks.STRIPPED_ORANGE_WOOD
		));
	}

	private void generateWoods(String woodType, WoodGeneratorData data){
		generateAxisColumn(data.getLog(), woodType + "_log", "woods/" + woodType);
		generateAxisColumn(data.getWood(), woodType + "_wood", "woods/" + woodType, woodType + "_log", woodType + "_log");
		generateAxisColumn(data.getStrippedLog(), "stripped_" + woodType + "_log", "woods/" + woodType);
		generateAllSidedBlock(data.getPlanks(), woodType + "_planks", "woods/" + woodType, woodType + "_planks");
		generateAxisColumn(data.getStrippedWood(), "stripped_" + woodType + "_wood", "woods/" + woodType, "stripped_" + woodType + "_log", "stripped_" + woodType + "_log");
	}

	private void generateAllSidedBlock(Block block, String assetName, String originPath, String textureName){
		BlockModelBuilder allSidedModel = models().getBuilder(MODID + ":block/" + originPath + "/" + assetName)
				.parent(models().getExistingFile(CUBE_ALL))
				.texture("all", new ResourceLocation(MODID, "block/" + originPath + "/" + textureName));
		VariantBlockStateBuilder stateBuilder = getVariantBuilder(block);
		stateBuilder.addModels(stateBuilder.partialState(),
				new ConfiguredModel(allSidedModel, 0, 0, false, 1)
		);

		ItemModelBuilder plankItem = itemModels().getBuilder(MODID + ":item/" + block.getRegistryName().getPath()).parent(allSidedModel);
	}

	private void generateAxisColumn(Block block, String assetName, String basePath){
		generateAxisColumn(block, assetName, basePath, assetName, assetName + "_top");
	}

	private void generateAxisColumn(Block block, String assetName, String basePath, String sideTexture){
		generateAxisColumn(block, assetName, basePath, sideTexture, sideTexture + "_top");
	}

	private void generateAxisColumn(Block block, String assetName, String basePath, String sideTexture, String endTexture){
		BlockModelBuilder logModel = models().getBuilder(MODID + ":block/" + basePath + "/" + assetName)
				.parent(models().getExistingFile(CUBE_COLUMN))
				.texture("end", new ResourceLocation(MODID, "block/" + basePath + "/" + endTexture))
				.texture("side", new ResourceLocation(MODID, "block/" + basePath + "/" + sideTexture));

		VariantBlockStateBuilder stateBuilder = getVariantBuilder(block);
		stateBuilder.addModels(
				stateBuilder.partialState().with(BlockStateProperties.AXIS, Direction.Axis.X),
				new ConfiguredModel(logModel, 90, 90, false, 1)
		).addModels(
				stateBuilder.partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y),
				new ConfiguredModel(logModel, 0, 0, false, 1)
		).addModels(
				stateBuilder.partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z),
				new ConfiguredModel(logModel, 90, 0, false, 1)
		);

		ItemModelBuilder logItem = itemModels().getBuilder(MODID + ":item/" + block.getRegistryName().getPath()).parent(logModel);
	}
}
