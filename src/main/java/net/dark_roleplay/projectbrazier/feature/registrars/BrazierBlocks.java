package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.marg.common.material.MaterialCondition;
import net.dark_roleplay.projectbrazier.experimental_features.roofs.RoofBlock;
import net.dark_roleplay.projectbrazier.experimental_features.roofs.RoofCornerBlock;
import net.dark_roleplay.projectbrazier.experimental_features.roofs.RoofType;
import net.dark_roleplay.projectbrazier.feature.blocks.*;
import net.dark_roleplay.projectbrazier.feature.blocks.BarrelBlock;
import net.dark_roleplay.projectbrazier.feature.blocks.crops.AgedCropBlock;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.*;
import net.dark_roleplay.projectbrazier.feature_client.blocks.DisplayTickers;
import net.dark_roleplay.projectbrazier.util.EnumMaterialRegistryObject;
import net.dark_roleplay.projectbrazier.util.EnumRegistryObject;
import net.dark_roleplay.projectbrazier.util.MaterialRegistryObject;
import net.dark_roleplay.projectbrazier.util.marg.ConditionHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BrazierBlocks extends Registrar {

	public static final MaterialCondition WOOD_LATTICE_CON = ConditionHelper.createItemCondition("wood", "planks");
	public static final MaterialCondition BARREL_CON = ConditionHelper.createItemCondition("wood", "planks");
	public static final MaterialCondition WOOD_BUCKET_CON = ConditionHelper.createItemCondition("wood", "planks");
	public static final MaterialCondition WOOD_CHAIR_CON = ConditionHelper.createItemCondition("wood", "planks");
	public static final MaterialCondition WOOD_CHAIR_BENCH = ConditionHelper.createItemCondition("wood", "planks", "stripped_log");
	public static final MaterialCondition LOG_CHAIR_CON = ConditionHelper.createItemCondition("wood", "stripped_log", "log");
	public static final MaterialCondition STRIPPED_LOG_CHAIR_CON = ConditionHelper.createItemCondition("wood", "stripped_log");
	public static final MaterialCondition FIREWOOD_CON = ConditionHelper.createItemCondition("wood", "planks", "stripped_log");
	public static final MaterialCondition WOOD_PLATFORM_CON = ConditionHelper.createItemCondition("wood", "planks", "stripped_log");

	public static final MaterialCondition SOLID_TABLE_CON = ConditionHelper.createItemCondition("wood", "planks", "stripped_log");

	public static final MaterialCondition LOG_CON = ConditionHelper.createItemCondition("wood", "log");
	public static final MaterialCondition STRIPPED_LOG_CON = ConditionHelper.createItemCondition("wood", "stripped_log");
	public static final MaterialCondition STRIPPED_LOG_AND_LOG_CON = ConditionHelper.createItemCondition("wood", "log", "stripped_log");



	public static final MaterialRegistryObject<Block>
			OPEN_BARRELS = registerBlock("${material}_open_barrel", BARREL_CON, (mat, prop) -> new BarrelBlock(mat, prop, "open_barrel", false), Registrar::MARG_WOOD, true),
			CLOSED_BARRELS = registerBlock("${material}_closed_barrel", BARREL_CON, (mat, prop) -> new BarrelBlock(mat, prop, "closed_barrel", true), Registrar::MARG_WOOD, true),
			FLOWER_BARRELS = registerBlock("${material}_flower_barrel", BARREL_CON, (mat, prop) -> new FlowerContainerBlock(prop, "flower_barrel", "flower_barrel_flower_area"), Registrar::MARG_WOOD, true),
			FLOWER_BUCKET = registerBlock("${material}_flower_bucket", WOOD_BUCKET_CON, (mat, prop) -> new FlowerContainerBlock(prop, "flower_bucket", "flower_bucket_flower_area"), Registrar::MARG_WOOD, true),
			PLANK_CHAIR = registerBlock("${material}_plank_chair", WOOD_CHAIR_CON, (mat, prop) -> new WoodChairBlock(prop, "simple_chair"), Registrar::MARG_WOOD, true),
			CHAIR = registerBlock("${material}_solid_chair", WOOD_CHAIR_CON, (mat, prop) -> new WoodChairBlock(prop, "simple_chair"), Registrar::MARG_WOOD, true),
			STOOL = registerBlock("${material}_stool", WOOD_CHAIR_CON, (mat, prop) -> new WoodStoolBlock(prop, "stool"), Registrar::MARG_WOOD, true),
			ARMREST_CHAIR = registerBlock("${material}_armrest_chair", WOOD_CHAIR_CON, (mat, prop) -> new WoodChairBlock(prop, "armrest_chair"), Registrar::MARG_WOOD, true),
			FIREWOOD = registerBlock("${material}_firewood", FIREWOOD_CON, (mat, prop) -> new HAxisDecoBlock(prop, "full_block"), Registrar::MARG_WOOD, true),
			WOOD_BENCH = registerBlock("${material}_bench", WOOD_CHAIR_BENCH, (mat, prop) -> new WoodBenchBlock(prop, "default_wood_bench", "positive_wood_bench", "negative_wood_bench", "centered_wood_bench"), Registrar::MARG_WOOD, true),
			LOG_CHAIR = registerBlock("${material}_log_chair", LOG_CHAIR_CON, (mat, prop) -> new WoodChairBlock(prop, "log_chair"), Registrar::MARG_WOOD, true),
			LOG_BENCH = registerBlock("${material}_log_bench", LOG_CHAIR_CON, (mat, prop) -> new LogBenchBlock(prop, "log_bench_single", "log_bench_multi"), Registrar::MARG_WOOD, true),
			STRIPPED_LOG_CHAIR = registerBlock("stripped_${material}_log_chair", STRIPPED_LOG_CHAIR_CON, (mat, prop) -> new WoodChairBlock(prop, "log_chair"), Registrar::MARG_WOOD, true),
			STRIPPED_LOG_BENCH = registerBlock("stripped_${material}_log_bench", STRIPPED_LOG_CHAIR_CON, (mat, prop) -> new LogBenchBlock(prop, "log_bench_single", "log_bench_multi"), Registrar::MARG_WOOD, true),
			BOTTOM_WOOD_PLATFORM = registerBlock("bottom_${material}_platform", WOOD_PLATFORM_CON, (mat, prop) -> new HAxisDecoBlock(prop, "bottom_wood_platform"), Registrar::MARG_WOOD, false),
			TOP_WOOD_PLATFORM = registerBlock("top_${material}_platform", WOOD_PLATFORM_CON, (mat, prop) -> new HAxisDecoBlock(prop, "top_wood_platform"), Registrar::MARG_WOOD, false),
			BOTTOM_WOOD_PLATFORM_STAIRS = registerBlock("bottom_${material}_platform_stairs", WOOD_PLATFORM_CON, (mat, prop) -> new HFacedDecoBlock(prop, "bottom_wood_platform_stairs"), Registrar::MARG_WOOD, false),
			TOP_WOOD_PLATFORM_STAIRS = registerBlock("top_${material}_platform_stairs", WOOD_PLATFORM_CON, (mat, prop) -> new HFacedDecoBlock(prop, "top_wood_platform_stairs"), Registrar::MARG_WOOD, false),
			HOLLOW_LOG = registerBlock("hollow_${material}_log", STRIPPED_LOG_CON, (mat, prop) -> new AxisDecoBlock(prop, "hollow_log"), Registrar::MARG_WOOD, true),
			STRIPPED_HOLLOW_LOG = registerBlock("stripped_hollow_${material}_log", STRIPPED_LOG_CON, (mat, prop) -> new AxisDecoBlock(prop, "hollow_log"), Registrar::MARG_WOOD, true),
			SOLID_TABLE = registerBlock("solid_${material}_table", SOLID_TABLE_CON, (mat, prop) -> new PaneConnectedBlock(prop, "solid_table"), Registrar::MARG_WOOD, true);

	public static final MaterialRegistryObject<FacedLatticeBlock>
			WOOD_LATTICE_1 = registerBlock("${material}_cross_lattice", WOOD_LATTICE_CON, (mat, prop) -> new FacedLatticeBlock(prop, "lattice"), Registrar::MARG_WOOD, true),
			WOOD_LATTICE_2 = registerBlock("${material}_dense_vertical_lattice", WOOD_LATTICE_CON, (mat, prop) -> new FacedLatticeBlock(prop, "lattice"), Registrar::MARG_WOOD, true),
			WOOD_LATTICE_3 = registerBlock("${material}_diamond_lattice", WOOD_LATTICE_CON, (mat, prop) -> new FacedLatticeBlock(prop, "lattice"), Registrar::MARG_WOOD, true),
			WOOD_LATTICE_4 = registerBlock("${material}_grid_lattice", WOOD_LATTICE_CON, (mat, prop) -> new FacedLatticeBlock(prop, "lattice"), Registrar::MARG_WOOD, true),
			WOOD_LATTICE_5 = registerBlock("${material}_vertical_lattice", WOOD_LATTICE_CON, (mat, prop) -> new FacedLatticeBlock(prop, "lattice"), Registrar::MARG_WOOD, true);

	public static final MaterialRegistryObject<AxisLatticeBlock>
			WOOD_LATTICE_1_C = registerBlock("${material}_cross_lattice_centered", WOOD_LATTICE_CON, (mat, prop) -> new AxisLatticeBlock(prop, "lattice_centered"), Registrar::MARG_WOOD, false),
			WOOD_LATTICE_2_C = registerBlock("${material}_dense_vertical_lattice_centered", WOOD_LATTICE_CON, (mat, prop) -> new AxisLatticeBlock(prop, "lattice_centered"), Registrar::MARG_WOOD, false),
			WOOD_LATTICE_3_C = registerBlock("${material}_diamond_lattice_centered", WOOD_LATTICE_CON, (mat, prop) -> new AxisLatticeBlock(prop, "lattice_centered"), Registrar::MARG_WOOD, false),
			WOOD_LATTICE_4_C = registerBlock("${material}_grid_lattice_centered", WOOD_LATTICE_CON, (mat, prop) -> new AxisLatticeBlock(prop, "lattice_centered"), Registrar::MARG_WOOD, false),
			WOOD_LATTICE_5_C = registerBlock("${material}_vertical_lattice_centered", WOOD_LATTICE_CON, (mat, prop) -> new AxisLatticeBlock(prop, "lattice_centered"), Registrar::MARG_WOOD, false);


	public static final EnumMaterialRegistryObject<DyeColor, Block>
			POLSTERED_WOOD_BENCH	= registerBlock("%s_polstered_${material}_bench", DyeColor.class, WOOD_CHAIR_BENCH, prop -> new WoodBenchBlock(prop, "default_polstered_wood_bench", "positive_polstered_wood_bench", "negative_polstered_wood_bench", "centered_polstered_wood_bench"), Registrar::MARG_WOOD, true);

	public static final RegistryObject<Block>
			HOOF_FUNGUS = registerBlock("hoof_fungus", WallFungiBlock::new, Registrar.PLANT_FUNGI, true),
			ANDESITE_BRICKS = registerBlock("andesite_bricks", Block::new, Registrar.STONE_SOLID, true),
			DIORITE_BRICKS = registerBlock("diorite_bricks", Block::new, Registrar.STONE_SOLID, true),
			GRANITE_BRICKS = registerBlock("granite_bricks", Block::new, Registrar.STONE_SOLID, true),
			ANDESITE_PILLAR = registerBlock("andesite_pillar", RotatedPillarBlock::new, Registrar.STONE_SOLID, true),
			DIORITE_PILLAR = registerBlock("diorite_pillar", RotatedPillarBlock::new, Registrar.STONE_SOLID, true),
			GRANITE_PILLAR = registerBlock("granite_pillar", RotatedPillarBlock::new, Registrar.STONE_SOLID, true),
			SNOW_BRICKS = registerBlock("snow_bricks", Block::new, Registrar.SNOW_SOLID, true),
			STONE_MACHICOLATIONS = registerBlock("stone_machicolations", prop -> new MachicolationBlock(prop, "machicolations"), Registrar.STONE, true),
			STONE_CRENELLATIONS = registerBlock("stone_crenellations", prop -> new MachicolationBlock(prop, "crenellations"), Registrar.STONE, true),
			RIVERSTONE = registerBlock("riverstone", Block::new, Registrar.STONE_SOLID, true),
			LARGE_RIVERSTONE = registerBlock("large_riverstone", Block::new, Registrar.STONE_SOLID, true),
			DARK_LARGE_RIVERSTONE = registerBlock("dark_large_riverstone", Block::new, Registrar.STONE_SOLID, true),
			COLORFUL_COBBLESTONE = registerBlock("colorful_cobblestone", Block::new, Registrar.STONE_SOLID, true),
			PALE_COLORFUL_COBBLESTONE = registerBlock("pale_colorful_cobblestone", Block::new, Registrar.STONE_SOLID, true),
			H_STONE_BRICK_ARROW_SLIT = registerBlock("horizontal_stone_brick_arrow_slit", prop -> new HFacedDecoBlock(prop, "arrow_slits/stone_bricks/horizontal_arrow_slit"), Registrar.STONE, false),
			V_STONE_BRICK_ARROW_SLIT = registerBlock("vertical_stone_brick_arrow_slit", prop -> new HFacedDecoBlock(prop, "arrow_slits/stone_bricks/vertical_arrow_slit"), Registrar.STONE, false),
			C_STONE_BRICK_ARROW_SLIT = registerBlock("cross_stone_brick_arrow_slit", prop -> new HFacedDecoBlock(prop, "arrow_slits/stone_bricks/cross_arrow_slit"), Registrar.STONE, false),
			NAIL = registerBlock("nail", (prop) -> new NailBlock(prop, "nail"), Registrar.METAL, true),
			HANGING_HORN = registerBlock("hanging_bone_horn", (prop) -> new HangingItemBlock(prop, "hanging_horn", 6), Registrar.METAL, false),
			HANGING_SILVER_SPYGLASS = registerBlock("hanging_silver_spyglass", (prop) -> new HangingItemBlock(prop, "hanging_spyglass", 12), Registrar.METAL, false),
			HANGING_GOLD_SPYGLASS = registerBlock("hanging_gold_spyglass", (prop) -> new HangingItemBlock(prop, "hanging_spyglass", 14), Registrar.METAL, false),
			IRON_BRAZIER_COAL = registerBlock("iron_brazier", (prop) -> new BrazierBlock(prop, 1, "brazier"), Registrar.METAL, true),
			IRON_FIRE_BOWL = registerBlock("iron_fire_bowl", (prop) -> new BrazierBlock(prop, 1, "fire_bowl"), Registrar.METAL, true),
			SOUL_IRON_BRAZIER_COAL = registerBlock("soul_iron_brazier", (prop) -> new BrazierBlock(prop, 2, "brazier"), Registrar.METAL, true),
			SOUL_IRON_FIRE_BOWL = registerBlock("soul_iron_fire_bowl", (prop) -> new BrazierBlock(prop, 2, "fire_bowl"), Registrar.METAL, true),
			EMPTY_CANDLE_HOLDER = registerBlock("empty_candle_holder", (prop) -> new EmptyBurningBlock(prop, "empty_candle_holder"), Registrar.METAL, true),
			CANDLE_HOLDER = registerBlock("candle_holder", (prop) -> new BurningBlock(prop, "candle_holder", 15, DisplayTickers::animatedCandleHolder), Registrar.METAL, true),
			EMPTY_WALL_CANDLE_HOLDER = registerBlock("empty_wall_candle_holder", prop -> new EmptyWallBurningBlock(prop, "empty_wall_candle_holder"), Registrar.METAL, true),
			WALL_CANDLE_HOLDER = registerBlock("wall_candle_holder", prop -> new WallBurningBlock(prop, "wall_candle_holder", 15, DisplayTickers::animatedWallCandleHolder), Registrar.METAL, true),
			EMPTY_WALL_TORCH_HOLDER = registerBlock("empty_wall_torch_holder", prop -> new EmptyWallBurningBlock(prop, "empty_torch_holder"), Registrar.METAL, true),
			WALL_TORCH_HOLDER = registerBlock("wall_torch_holder", prop -> new WallBurningBlock(prop, "torch_holder", 15, DisplayTickers::animateTorchHolder), Registrar.METAL, true),
			WALL_SOUL_TORCH_HOLDER = registerBlock("wall_soul_torch_holder", prop -> new WallBurningBlock(prop, "torch_holder", 10, DisplayTickers::animateSoulTorchHolder), Registrar.METAL, true),
			JAIL_LATTICE_CENTERED = registerBlock("jail_lattice_centered", prop -> new AxisLatticeBlock(prop, "lattice_centered"), Registrar.METAL, false),
			JAIL_LATTICE = registerBlock("jail_lattice", prop -> new FacedLatticeBlock(prop, "lattice"), Registrar.METAL, true),
			APPLE_PLANK = registerBlock("apple_planks", Block::new, Registrar.WOOD_SOLID, true),
			APPLE_LOG = registerBlock("apple_log", RotatedPillarBlock::new, Registrar.WOOD_SOLID, true),
			STRIPPED_APPLE_LOG = registerBlock("stripped_apple_log", RotatedPillarBlock::new, Registrar.WOOD_SOLID, true),
			APPLE_WOOD = registerBlock("apple_wood", RotatedPillarBlock::new, Registrar.WOOD_SOLID, true),
			STRIPPED_APPLE_WOOD = registerBlock("stripped_apple_wood", RotatedPillarBlock::new, Registrar.WOOD_SOLID, true),
			ORANGE_PLANK = registerBlock("orange_planks", Block::new, Registrar.WOOD_SOLID, true),
			ORANGE_LOG = registerBlock("orange_log", RotatedPillarBlock::new, Registrar.WOOD_SOLID, true),
			STRIPPED_ORANGE_LOG = registerBlock("stripped_orange_log", RotatedPillarBlock::new, Registrar.WOOD_SOLID, true),
			ORANGE_WOOD = registerBlock("orange_wood", RotatedPillarBlock::new, Registrar.WOOD_SOLID, true),
			STRIPPED_ORANGE_WOOD = registerBlock("stripped_orange_wood", RotatedPillarBlock::new, Registrar.WOOD_SOLID, true),
			CAULIFLOWER = registerBlock("cauliflower", prop -> new AgedCropBlock(prop, BrazierItems.CAULIFLOWER_SEEDS){
				@Override
				public int getMaxAge() { return 6; }
			}, Registrar.CROP, false),
			WHITE_CABBAGE = registerBlock("white_cabbage", prop -> new AgedCropBlock(prop, BrazierItems.WHITE_CABBAGE_SEEDS){
				@Override
				public int getMaxAge() { return 7; }
			}, Registrar.CROP, false),
			ROPE_ANCHOR = registerBlock("rope_anchor", prop -> new RopeAnchorBlock(prop, "rope_anchor", "dropped_rope_anchor"), Registrar.ROPE, true),
			ROPE = registerBlock("rope", prop -> new RopeBlock(prop, "rope", "rope_end"), Registrar.ROPE, false),
			GLIMMERTAIL = registerBlock("glimmertail", TallGrassBlock::new, AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS), false);

	//Experimental
//	public static final RegistryObject<Block>
//			DRAWBRIDGE_ANCHOR = registerBlock("drawbridge_anchor", DrawbridgeAnchorBlock::new, Registrar.STONE_SOLID, false);
//
	//public static final EnumRegistryObject<RoofType, Block>
			//STRAIGHT_ROOFS	= registerBlock("clay_shingle_roof", RoofType.class, prop -> new RoofBlock(prop, "placeholder"), Registrar.STONE, false);
			//INNER_CORNER_ROOFS	= registerBlock("inner_corner_%s_clay_shingle_roof", RoofType.class, type -> type.doesGenerateCorners(), prop -> new RoofCornerBlock(prop, "placeholder"), Registrar.STONE, false),
			//OUTER_CORNER_ROOFS	= registerBlock("outer_corner_%s_clay_shingle_roof", RoofType.class, type -> type.doesGenerateCorners(), prop -> new RoofCornerBlock(prop, "placeholder"), Registrar.STONE, false);

	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {
		EmptyWallBurningBlock emptyTorchHolder = (EmptyWallBurningBlock) EMPTY_WALL_TORCH_HOLDER.get();
		emptyTorchHolder.addItem(Items.TORCH, WALL_TORCH_HOLDER.get());
		emptyTorchHolder.addItem(Items.SOUL_TORCH, WALL_SOUL_TORCH_HOLDER.get());

		EmptyWallBurningBlock wallCandleHolder = (EmptyWallBurningBlock) EMPTY_WALL_CANDLE_HOLDER.get();
		wallCandleHolder.addItem(Items.TORCH, WALL_CANDLE_HOLDER.get());

		EmptyBurningBlock candleHolder = (EmptyBurningBlock) EMPTY_CANDLE_HOLDER.get();
		candleHolder.addItem(Items.TORCH, CANDLE_HOLDER.get());

		((FacedLatticeBlock)JAIL_LATTICE.get()).initOtherBlock(JAIL_LATTICE_CENTERED.get());
		((AxisLatticeBlock)JAIL_LATTICE_CENTERED.get()).initOtherBlock(JAIL_LATTICE.get());

		for (MargMaterial material : WOOD_LATTICE_CON) {
			WOOD_LATTICE_1.get(material).initOtherBlock(WOOD_LATTICE_1_C.get(material));
			WOOD_LATTICE_1_C.get(material).initOtherBlock(WOOD_LATTICE_1.get(material));
			WOOD_LATTICE_2.get(material).initOtherBlock(WOOD_LATTICE_2_C.get(material));
			WOOD_LATTICE_2_C.get(material).initOtherBlock(WOOD_LATTICE_2.get(material));
			WOOD_LATTICE_3.get(material).initOtherBlock(WOOD_LATTICE_3_C.get(material));
			WOOD_LATTICE_3_C.get(material).initOtherBlock(WOOD_LATTICE_3.get(material));
			WOOD_LATTICE_4.get(material).initOtherBlock(WOOD_LATTICE_4_C.get(material));
			WOOD_LATTICE_4_C.get(material).initOtherBlock(WOOD_LATTICE_4.get(material));
			WOOD_LATTICE_5.get(material).initOtherBlock(WOOD_LATTICE_5_C.get(material));
			WOOD_LATTICE_5_C.get(material).initOtherBlock(WOOD_LATTICE_5.get(material));
		}

		for (MargMaterial material : BARREL_CON) {
			BarrelBlock openBarrel = (BarrelBlock) OPEN_BARRELS.get(material);
			BarrelBlock closedBarrel = (BarrelBlock) CLOSED_BARRELS.get(material);
			openBarrel.setOtherBlock(closedBarrel);
			closedBarrel.setOtherBlock(openBarrel);
		}
	}
}