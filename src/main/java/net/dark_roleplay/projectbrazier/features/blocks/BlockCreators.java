package net.dark_roleplay.projectbrazier.features.blocks;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.projectbrazier.features.blocks.lattice_block.AxisLatticeBlock;
import net.dark_roleplay.projectbrazier.features.blocks.lattice_block.FacedLatticeBlock;
import net.dark_roleplay.projectbrazier.features.blocks.nail.HangUpItemBlock;
import net.dark_roleplay.projectbrazier.features.blocks.nail.NailBlock;
import net.dark_roleplay.projectbrazier.features.blocks.special.BrazierBlock;
import net.dark_roleplay.projectbrazier.features.blocks.templates.ConnectedHAxisDecoBlock;
import net.dark_roleplay.projectbrazier.features.blocks.templates.DecoBlock;
import net.dark_roleplay.projectbrazier.features.blocks.templates.HAxisDecoBlock;
import net.dark_roleplay.projectbrazier.handler.MedievalBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;

import java.util.Map;

public class BlockCreators {

	public static Block createChair(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new ChairBlock(properties, "placeholder");
	}

	public static Block createClosedBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new DecoBlock(properties, "closed_barrel");
	}

	public static Block createOpenBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new DecoBlock(properties, "open_barrel");
	}

	public static Block createWoodPlatform(IMaterial material, boolean isTop){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new HAxisDecoBlock(properties, isTop ? "top_wood_platform" : "bottom_wood_platform");
	}


	public static Block createWoodBench(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new ConnectedHAxisDecoBlock(properties, "default_wood_bench", "positive_wood_bench", "negative_wood_bench", "centered_wood_bench");
	}

	public static Block createPolsteredWoodBench(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new ConnectedHAxisDecoBlock(properties, "default_polstered_wood_bench", "positive_polstered_wood_bench", "negative_polstered_wood_bench", "centered_polstered_wood_bench");
	}

	public static Block createJailLattice(){
		Block.Properties properties =
				Block.Properties.create(Material.IRON, MaterialColor.IRON)
						.hardnessAndResistance(5.0F, 1200.0F)
						.sound(SoundType.ANVIL).notSolid();
		return new FacedLatticeBlock(properties, "lattice", MedievalBlocks.JAIL_LATTICE_CENTERED);
	}

	public static Block createJailLatticeB(){
		Block.Properties properties =
				Block.Properties.create(Material.IRON, MaterialColor.IRON)
						.hardnessAndResistance(5.0F, 1200.0F)
						.sound(SoundType.ANVIL).notSolid();
		return new AxisLatticeBlock(properties, "lattice_centered");
	}

	public static Block createWoodWindow(IMaterial material, Map<IMaterial, RegistryObject<Block>> centeredWindows){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new FacedLatticeBlock(properties, "lattice", centeredWindows.get(material));
	}

	public static Block createWoodWindowB(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new AxisLatticeBlock(properties, "lattice_centered");
	}

	public static Block createNail(){
		Block.Properties properties =
				Block.Properties.create(Material.ANVIL, MaterialColor.IRON)
						.hardnessAndResistance(5.0F, 1200.0F)
						.sound(SoundType.ANVIL).notSolid();
		return new NailBlock(properties, "nail");
	}

	public static Block createIronBrazier(){
		Block.Properties properties =
				Block.Properties.create(Material.ANVIL, MaterialColor.IRON)
						.hardnessAndResistance(5.0F, 1200.0F)
						.sound(SoundType.ANVIL).notSolid();
		return new BrazierBlock(properties);
	}

	public static Block createHangingHorn(){
		Block.Properties properties =
				Block.Properties.create(Material.ANVIL, MaterialColor.IRON)
						.hardnessAndResistance(5.0F, 1200.0F)
						.sound(SoundType.ANVIL).notSolid();
		return new HangUpItemBlock(properties, "hanging_horn", 6);
	}

	public static Block createHangingSpyglass(){
		Block.Properties properties =
				Block.Properties.create(Material.ANVIL, MaterialColor.IRON)
						.hardnessAndResistance(5.0F, 1200.0F)
						.sound(SoundType.ANVIL).notSolid();
		return new HangUpItemBlock(properties, "hanging_horn", 10);
	}

	public static Block createMachicolations(){
		Block.Properties properties =
				Block.Properties.
						create(Material.ROCK, MaterialColor.STONE)
						.hardnessAndResistance(1.5F, 6.0F)
						.sound(SoundType.STONE)
						.notSolid();
		return new Machicolations(properties, "machicolations");
	}

	public static Block createCrenellations(){
		Block.Properties properties =
				Block.Properties.
						create(Material.ROCK, MaterialColor.STONE)
						.hardnessAndResistance(1.5F, 6.0F)
						.sound(SoundType.STONE)
						.notSolid();
		return new Machicolations(properties, "crenellations");
	}

//	public interface IBlockGenerator{
//		public Block mkBlock(Block.Properties
//	}
}
