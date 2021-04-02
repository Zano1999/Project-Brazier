package net.dark_roleplay.projectbrazier.handler;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.marg.common.material.MaterialCondition;
import net.dark_roleplay.projectbrazier.features.blocks.BlockCreators;
import net.dark_roleplay.projectbrazier.features.blocks.drawbridge.DrawbridgeAnchorBlock;
import net.dark_roleplay.projectbrazier.features.blocks.lattice_block.AxisLatticeBlock;
import net.dark_roleplay.projectbrazier.features.blocks.roofs.RoofType;
import net.dark_roleplay.projectbrazier.features.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.EnumRegistryObject;
import net.dark_roleplay.projectbrazier.util.marg.ConditionHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MedievalBlocks {
	public static final DeferredRegister<Block> BLOCKS = MedievalRegistries.BLOCKS;
	public static final DeferredRegister<Block> BLOCKS_NO_ITEMS = MedievalRegistries.BLOCKS_NO_ITEMS;

	public static MaterialCondition planksOnly = ConditionHelper.createItemCondition("wood", "planks");
	public static MaterialCondition planksStrippedLogs = ConditionHelper.createItemCondition("wood", "planks", "stripped_log");
	public static MaterialCondition logsOnly = ConditionHelper.createItemCondition("wood", "log");
	public static MaterialCondition logsStrippedLogs = ConditionHelper.createItemCondition("wood", "log", "stripped_log");

	private static final Block.Properties wood = AbstractBlock.Properties.create(net.minecraft.block.material.Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
	private static final Block.Properties stoneProps = Block.Properties.create(net.minecraft.block.material.Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE);
	private static final Block.Properties snowProps = Block.Properties.create(net.minecraft.block.material.Material.SNOW).hardnessAndResistance(0.3F).sound(SoundType.SNOW);
	private static final Block.Properties packedIceProps = Block.Properties.create(net.minecraft.block.material.Material.PACKED_ICE).slipperiness(0.98F).hardnessAndResistance(0.5F).sound(SoundType.GLASS);


	public static final RegistryObject<Block>
			HOOF_FUNGUS						 = register("hoof_fungus", BlockCreators::createFungi),
			STONE_MACHICOLATIONS			 = register("stone_machicolations", BlockCreators::createMachicolations),
			STONE_CRENELLATIONS			 = register("stone_crenellations", BlockCreators::createCrenellations),
			DRAWBRIDGE_ANCHOR				 = register("drawbridge_anchor", DrawbridgeAnchorBlock::new, stoneProps),
			ANDESITE_BRICKS             = register("andesite_bricks", Block::new, stoneProps),
			DIORITE_BRICKS              = register("diorite_bricks", Block::new, stoneProps),
			GRANITE_BRICKS              = register("granite_bricks", Block::new, stoneProps),
			ANDESITE_PILLAR             = register("andesite_pillar", RotatedPillarBlock::new, stoneProps),
			DIORITE_PILLAR              = register("diorite_pillar", RotatedPillarBlock::new, stoneProps),
			GRANITE_PILLAR              = register("granite_pillar", RotatedPillarBlock::new, stoneProps),
			SNOW_BRICKS             	 = register("snow_bricks", Block::new, snowProps),
			IRON_BRAZIER_COAL				 = register("iron_brazier_coal", BlockCreators::createIronBrazier),
			IRON_FIRE_BOWL			 		 = register("iron_fire_bowl", BlockCreators::createIronBrazier),
			SOUL_IRON_BRAZIER_COAL		 = register("soul_iron_brazier_coal", BlockCreators::createIronBrazier),
			SOUL_IRON_FIRE_BOWL			 = register("soul_iron_fire_bowl", BlockCreators::createIronBrazier),
			JAIL_LATTICE          		 = register("jail_lattice", BlockCreators::createJailLattice),
			JAIL_LATTICE_CENTERED       = registerNoItem("jail_lattice_centered", BlockCreators::createJailLatticeB),
			NAIL          					 = register("nail", BlockCreators::createNail),
			HANGING_HORN					 = registerNoItem("hanging_bone_horn", BlockCreators::createHangingHorn),
			HANGING_SILVER_SPYGLASS		 = registerNoItem("hanging_silver_spyglass", BlockCreators::createHangingSpyglass),
			HANGING_GOLD_SPYGLASS		 = registerNoItem("hanging_gold_spyglass", BlockCreators::createHangingSpyglass),

			APPLE_PLANK						 = register("apple_planks", Block::new, wood),
			APPLE_LOG			 			 = register("apple_log", RotatedPillarBlock::new, wood),
			STRIPPED_APPLE_LOG			 = register("stripped_apple_log", RotatedPillarBlock::new, wood),
			APPLE_WOOD						 = register("apple_wood", RotatedPillarBlock::new, wood),
			STRIPPED_APPLE_WOOD			 = register("stripped_apple_wood", RotatedPillarBlock::new, wood),

			ORANGE_PLANK					 = register("orange_planks", Block::new, wood),
			ORANGE_LOG			 			 = register("orange_log", RotatedPillarBlock::new, wood),
			STRIPPED_ORANGE_LOG			 = register("stripped_orange_log", RotatedPillarBlock::new, wood),
			ORANGE_WOOD						 = register("orange_wood", RotatedPillarBlock::new, wood),
			STRIPPED_ORANGE_WOOD			 = register("stripped_orange_wood", RotatedPillarBlock::new, wood),

			RIVERSTONE                  = register("riverstone", Block::new, stoneProps),
			LARGE_RIVERSTONE            = register("large_riverstone", Block::new, stoneProps),
			DARK_LARGE_RIVERSTONE       = register("dark_large_riverstone", Block::new, stoneProps),
			COLORFUL_COBBLESTONE        = register("colorful_cobblestone", Block::new, stoneProps),
			PALE_COLORFUL_COBBLESTONE   = register("pale_colorful_cobblestone", Block::new, stoneProps),
			V_STONE_BRICK_ARROW_SLIT	 = register("horizontal_stone_brick_arrow_slit", prop -> new HFacedDecoBlock(prop, "arrow_slits/stone_bricks/horizontal_arrow_slit"), stoneProps),
			H_STONE_BRICK_ARROW_SLIT	 = register("vertical_stone_brick_arrow_slit", prop -> new HFacedDecoBlock(prop, "arrow_slits/stone_bricks/vertical_arrow_slit"), stoneProps),
			C_STONE_BRICK_ARROW_SLIT	 = register("cross_stone_brick_arrow_slit", prop -> new HFacedDecoBlock(prop, "arrow_slits/stone_bricks/cross_arrow_slit"), stoneProps);

	public static final EnumRegistryObject<RoofType, Block>
			STRAIGHT_ROOFS					= registerRoof("%s_clay_shingle_roof", BlockCreators::createRoofBlock);

	public static final Map<MargMaterial, RegistryObject<Block>>
			FLOWER_BUCKET					= register("${material}_flower_bucket", planksOnly, BlockCreators::createFlowerBucket),
			FLOWER_BARREL					= register("${material}_flower_barrel", planksOnly, BlockCreators::createFlowerBarrel),
			PLANK_CHAIR						= register("${material}_plank_chair", planksOnly, BlockCreators::createChair),
			CHAIR								= register("${material}_solid_chair", planksOnly, BlockCreators::createChair),
			STOOL								= register("${material}_stool", planksOnly, BlockCreators::createStool),
			ARMREST_CHAIR					= register("${material}_armrest_chair", planksOnly, BlockCreators::createChair),
			LOG_CHAIR						= register("${material}_log_chair", logsStrippedLogs, BlockCreators::createLogChair),
			FIREWOOD							= register("${material}_firewood", logsStrippedLogs, BlockCreators::createFirewood),
			WOOD_LATTICE_1_C 				= registerNoItems("${material}_cross_lattice_centered", planksOnly, BlockCreators::createWoodWindowB),
			WOOD_LATTICE_1 				= register("${material}_cross_lattice", planksOnly, wood -> BlockCreators.createWoodWindow(wood, WOOD_LATTICE_1_C)),
			WOOD_LATTICE_2_C 				= registerNoItems("${material}_dense_vertical_lattice_centered", planksOnly, BlockCreators::createWoodWindowB),
			WOOD_LATTICE_2 				= register("${material}_dense_vertical_lattice", planksOnly, wood -> BlockCreators.createWoodWindow(wood, WOOD_LATTICE_2_C)),
			WOOD_LATTICE_3_C 				= registerNoItems("${material}_diamond_lattice_centered", planksOnly, BlockCreators::createWoodWindowB),
			WOOD_LATTICE_3 				= register("${material}_diamond_lattice", planksOnly, wood -> BlockCreators.createWoodWindow(wood, WOOD_LATTICE_3_C)),
			WOOD_LATTICE_4_C 				= registerNoItems("${material}_grid_lattice_centered", planksOnly, BlockCreators::createWoodWindowB),
			WOOD_LATTICE_4 				= register("${material}_grid_lattice", planksOnly, wood -> BlockCreators.createWoodWindow(wood, WOOD_LATTICE_4_C)),
			WOOD_LATTICE_5_C 				= registerNoItems("${material}_vertical_lattice_centered", planksOnly, BlockCreators::createWoodWindowB),
			WOOD_LATTICE_5 				= register("${material}_vertical_lattice", planksOnly, wood -> BlockCreators.createWoodWindow(wood, WOOD_LATTICE_5_C)),
			OPEN_BARREL 					= register("${material}_open_barrel", planksOnly, BlockCreators::createOpenBarrel),
			CLOSED_BARREL 					= register("${material}_closed_barrel", planksOnly, BlockCreators::createClosedBarrel),
			WOOD_BENCH				 		= register("${material}_bench", planksStrippedLogs, BlockCreators::createWoodBench),
			BOTTOM_WOOD_PLATFORM 		= registerNoItems("bottom_${material}_platform", planksStrippedLogs, mat -> BlockCreators.createWoodPlatform(mat)),
			TOP_WOOD_PLATFORM 			= registerNoItems("top_${material}_platform", planksStrippedLogs, mat -> BlockCreators.createWoodPlatform(mat, BOTTOM_WOOD_PLATFORM));

	public static final Map<DyeColor, Map<MargMaterial, RegistryObject<Block>>>
			POLSTERED_WOOD_BENCH			= registerInColors("${color}_polstered_${material}_bench", planksStrippedLogs, BlockCreators::createPolsteredWoodBench);

	public static void postBlockRegistryCallback(RegistryEvent.Register<Item> event){
		((AxisLatticeBlock) JAIL_LATTICE_CENTERED.get()).initMainBlock(JAIL_LATTICE);
		initLatticeHelper(WOOD_LATTICE_1_C, WOOD_LATTICE_1);
		initLatticeHelper(WOOD_LATTICE_2_C, WOOD_LATTICE_2);
		initLatticeHelper(WOOD_LATTICE_3_C, WOOD_LATTICE_3);
		initLatticeHelper(WOOD_LATTICE_4_C, WOOD_LATTICE_4);
		initLatticeHelper(WOOD_LATTICE_5_C, WOOD_LATTICE_5);
	}

	private static void initLatticeHelper(Map<MargMaterial, RegistryObject<Block>> center, Map<MargMaterial, RegistryObject<Block>> main){
		for(Map.Entry<MargMaterial, RegistryObject<Block>> entry : center.entrySet()){
			AxisLatticeBlock centerBlock = (AxisLatticeBlock) entry.getValue().get();
			centerBlock.initMainBlock(main.get(entry.getKey()));
		}
	}

	private static RegistryObject<Block> register(String name, Supplier<Block> suplier) {
		return BLOCKS.register(name, suplier);
	}

	private static RegistryObject<Block> registerNoItem(String name, Supplier<Block> suplier) {
		return BLOCKS_NO_ITEMS.register(name, suplier);
	}

	private static RegistryObject<Block> register(String name, Function<Block.Properties, Block> suplier, Block.Properties props) {
		return BLOCKS.register(name, () -> suplier.apply(props));
	}

	private static EnumRegistryObject<RoofType, Block> registerRoof(String name, Supplier<Block> suplier) {
		EnumRegistryObject<RoofType, Block> regObj = new EnumRegistryObject<>(RoofType.class);
		for(RoofType type : RoofType.values())
			regObj.register(type, register(String.format(name, type.getTypeName()), suplier));
		return regObj;
	}

	private static RegistryObject<Block> registerNoItem(String name, Function<Block.Properties, Block> suplier) {
		return BLOCKS_NO_ITEMS.register(name, () -> suplier.apply(null));
	}

	private static Map<MargMaterial, RegistryObject<Block>> register(String name, MaterialCondition condition, Function<MargMaterial, Block> suplier){
		return register(BLOCKS, name, condition, suplier);
	}

	private static Map<DyeColor, Map<MargMaterial, RegistryObject<Block>>> registerInColors(String name, MaterialCondition condition, Function<MargMaterial, Block> suplier){
		Map<DyeColor, Map<MargMaterial, RegistryObject<Block>>> blocks = new EnumMap<>(DyeColor.class);

		for(DyeColor color : DyeColor.values()){
			blocks.put(color, register(BLOCKS, name.replace("${color}", color.getTranslationKey()), condition, suplier));
		}

		return blocks;
	}

	private static Map<MargMaterial, RegistryObject<Block>> registerNoItems(String name, MaterialCondition condition, Function<MargMaterial, Block> suplier){
		return register(BLOCKS_NO_ITEMS, name, condition, suplier);
	}

	private static Map<MargMaterial, RegistryObject<Block>> register(DeferredRegister<Block> registrerer, String name, MaterialCondition condition, Function<MargMaterial, Block> suplier){
		Map<MargMaterial, RegistryObject<Block>> blocks = new HashMap<>();

		condition.forEach(material -> {
			String registryName = material.getTextProvider().apply(name);
			blocks.put(material, registrerer.register(registryName, () -> suplier.apply(material)));
		});

		return blocks;
	}
}