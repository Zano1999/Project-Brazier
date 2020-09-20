package net.dark_roleplay.projectbrazier.handler;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.api.materials.ItemMaterialCondition;
import net.dark_roleplay.projectbrazier.features.blocks.BlockCreators;
import net.dark_roleplay.projectbrazier.features.blocks.drawbridge.DrawbridgeAnchorBlock;
import net.dark_roleplay.projectbrazier.features.blocks.lattice_block.AxisLatticeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MedievalBlocks {
	public static final DeferredRegister<Block> BLOCKS = MedievalRegistries.BLOCKS;
	public static final DeferredRegister<Block> BLOCKS_NO_ITEMS = MedievalRegistries.BLOCKS_NO_ITEMS;

	private static IMaterialCondition planksOnly = new ItemMaterialCondition("wood", "planks");
	private static IMaterialCondition planksStrippedLogs = new ItemMaterialCondition("wood", "planks", "stripped_log");
	private static IMaterialCondition logsOnly = new ItemMaterialCondition("wood", "log");

	private static final Block.Properties stoneProps = Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE);
	private static final Block.Properties snowProps = Block.Properties.create(Material.SNOW).hardnessAndResistance(0.3F).sound(SoundType.SNOW);
	private static final Block.Properties packedIceProps = Block.Properties.create(Material.PACKED_ICE).slipperiness(0.98F).hardnessAndResistance(0.5F).sound(SoundType.GLASS);


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
			JAIL_LATTICE          		 = register("jail_lattice", BlockCreators::createJailLattice),
			JAIL_LATTICE_CENTERED       = registerNoItem("jail_lattice_centered", BlockCreators::createJailLatticeB),
			NAIL          					 = register("nail", BlockCreators::createNail),
			HANGING_HORN					 = registerNoItem("hanging_bone_horn", BlockCreators::createHangingHorn),
			HANGING_SILVER_SPYGLASS		 = registerNoItem("hanging_silver_spyglass", BlockCreators::createHangingSpyglass),
			HANGING_GOLD_SPYGLASS		 = registerNoItem("hanging_gold_spyglass", BlockCreators::createHangingSpyglass),

			RIVERSTONE                  = register("riverstone", Block::new, stoneProps),
			LARGE_RIVERSTONE            = register("large_riverstone", Block::new, stoneProps),
			DARK_LARGE_RIVERSTONE       = register("dark_large_riverstone", Block::new, stoneProps),
			COLORFUL_COBBLESTONE        = register("colorful_cobblestone", Block::new, stoneProps),
			PALE_COLORFUL_COBBLESTONE   = register("pale_colorful_cobblestone", Block::new, stoneProps);

	public static final Map<IMaterial, RegistryObject<Block>>
			PLANK_CHAIR						= register("${material}_plank_chair", planksOnly, BlockCreators::createChair),
			CHAIR								= register("${material}_solid_chair", planksOnly, BlockCreators::createChair),
			ARMREST_CHAIR					= register("${material}_armrest_chair", planksOnly, BlockCreators::createChair),
			LOG_CHAIR						= register("${material}_log_chair", logsOnly, BlockCreators::createChair),
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
			TOP_WOOD_PLATFORM 			= registerNoItems("top_${material}_platform", planksStrippedLogs, mat -> BlockCreators.createWoodPlatform(mat, true)),
			BOTTOM_WOOD_PLATFORM 		= registerNoItems("bottom_${material}_platform", planksStrippedLogs, mat -> BlockCreators.createWoodPlatform(mat, false));

	public static final Map<DyeColor, Map<IMaterial, RegistryObject<Block>>>
			POLSTERED_WOOD_BENCH			= registerInColors("${color}_polstered_${material}_bench", planksStrippedLogs, BlockCreators::createPolsteredWoodBench);

	public static void postBlockRegistryCallback(RegistryEvent.Register<Item> event){
		((AxisLatticeBlock) JAIL_LATTICE_CENTERED.get()).initMainBlock(JAIL_LATTICE);
		initLatticeHelper(WOOD_LATTICE_1_C, WOOD_LATTICE_1);
		initLatticeHelper(WOOD_LATTICE_2_C, WOOD_LATTICE_2);
		initLatticeHelper(WOOD_LATTICE_3_C, WOOD_LATTICE_3);
		initLatticeHelper(WOOD_LATTICE_4_C, WOOD_LATTICE_4);
		initLatticeHelper(WOOD_LATTICE_5_C, WOOD_LATTICE_5);
	}

	private static void initLatticeHelper(Map<IMaterial, RegistryObject<Block>> center, Map<IMaterial, RegistryObject<Block>> main){
		for(Map.Entry<IMaterial, RegistryObject<Block>> entry : center.entrySet()){
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

	private static RegistryObject<Block> registerNoItem(String name, Function<Block.Properties, Block> suplier) {
		return BLOCKS_NO_ITEMS.register(name, () -> suplier.apply(null));
	}

	private static Map<IMaterial, RegistryObject<Block>> register(String name, IMaterialCondition condition, Function<IMaterial, Block> suplier){
		return register(BLOCKS, name, condition, suplier);
	}

	private static Map<DyeColor, Map<IMaterial, RegistryObject<Block>>> registerInColors(String name, IMaterialCondition condition, Function<IMaterial, Block> suplier){
		Map<DyeColor, Map<IMaterial, RegistryObject<Block>>> blocks = new EnumMap<>(DyeColor.class);

		for(DyeColor color : DyeColor.values()){
			blocks.put(color, register(BLOCKS, name.replace("${color}", color.getTranslationKey()), condition, suplier));
		}

		return blocks;
	}

	private static Map<IMaterial, RegistryObject<Block>> registerNoItems(String name, IMaterialCondition condition, Function<IMaterial, Block> suplier){
		return register(BLOCKS_NO_ITEMS, name, condition, suplier);
	}

	private static Map<IMaterial, RegistryObject<Block>> register(DeferredRegister<Block> registrerer, String name, IMaterialCondition condition, Function<IMaterial, Block> suplier){
		Map<IMaterial, RegistryObject<Block>> blocks = new HashMap<>();

		condition.forEach(material -> {
			String registryName = material.getTextProvider().apply(name);
			blocks.put(material, registrerer.register(registryName, () -> suplier.apply(material)));
		});

		return blocks;
	}
}