package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.api.materials.ItemMaterialCondition;
import net.dark_roleplay.medieval.features.blocks.BlockCreators;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MedievalBlocks {
	public static final DeferredRegister<Block> BLOCKS = MedievalRegistries.BLOCKS;
	public static final DeferredRegister<Block> BLOCKS_NO_ITEMS = MedievalRegistries.BLOCKS_NO_ITEMS;

	private static IMaterialCondition planksOnly = new ItemMaterialCondition("wood", "planks");
	private static IMaterialCondition planksStrippedLogs = new ItemMaterialCondition("wood", "planks", "stripped_log");

	private static final Block.Properties stoneProps = Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE);
	private static final Block.Properties snowProps = Block.Properties.create(Material.SNOW).hardnessAndResistance(0.3F).sound(SoundType.SNOW);
	private static final Block.Properties packedIceProps = Block.Properties.create(Material.PACKED_ICE).slipperiness(0.98F).hardnessAndResistance(0.5F).sound(SoundType.GLASS);


	public static final RegistryObject<Block>
			ANDESITE_BRICKS             = register("andesite_bricks", Block::new, stoneProps),
			DIORITE_BRICKS              = register("diorite_bricks", Block::new, stoneProps),
			GRANITE_BRICKS              = register("granite_bricks", Block::new, stoneProps),
			ANDESITE_PILLAR             = register("andesite_pillar", RotatedPillarBlock::new, stoneProps),
			DIORITE_PILLAR              = register("diorite_pillar", RotatedPillarBlock::new, stoneProps),
			GRANITE_PILLAR              = register("granite_pillar", RotatedPillarBlock::new, stoneProps),
			SNOW_BRICKS             	 = register("snow_bricks", Block::new, snowProps),
			PACKED_ICE_BRICKS           = register("packed_ice_bricks", Block::new, packedIceProps),

			RIVERSTONE                  = register("riverstone", Block::new, stoneProps),
			LARGE_RIVERSTONE            = register("large_riverstone", Block::new, stoneProps),
			DARK_LARGE_RIVERSTONE       = register("dark_large_riverstone", Block::new, stoneProps),
			COLORFUL_COBBLESTONE        = register("colorful_cobblestone", Block::new, stoneProps),
			PALE_COLORFUL_COBBLESTONE   = register("pale_colorful_cobblestone", Block::new, stoneProps);

	public static final Map<IMaterial, RegistryObject<Block>>
			OPEN_BARREL 					= register("${material}_open_barrel", planksOnly, BlockCreators::createOpenBarrel),
			CLOSED_BARREL 					= register("${material}_closed_barrel", planksOnly, BlockCreators::createClosedBarrel),
			TOP_WOOD_PLATFORM 			= registerNoItems("top_${material}_platform", planksStrippedLogs, mat -> BlockCreators.createWoodPlatform(mat, true)),
			BOTTOM_WOOD_PLATFORM 		= registerNoItems("bottom_${material}_platform", planksStrippedLogs, mat -> BlockCreators.createWoodPlatform(mat, false));

	private static RegistryObject<Block> register(String name, Function<Block.Properties, Block> suplier, Block.Properties props) {
		return BLOCKS.register(name, () -> suplier.apply(props));
	}

	private static RegistryObject<Block> registerNoItem(String name, Function<Block.Properties, Block> suplier) {
		return BLOCKS_NO_ITEMS.register(name, () -> suplier.apply(null));
	}

	private static Map<IMaterial, RegistryObject<Block>> register(String name, IMaterialCondition condition, Function<IMaterial, Block> suplier){
		return register(BLOCKS, name, condition, suplier);
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
