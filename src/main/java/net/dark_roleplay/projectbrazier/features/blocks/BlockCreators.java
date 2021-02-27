package net.dark_roleplay.projectbrazier.features.blocks;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.projectbrazier.features.blocks.barrel.BarrelBlock;
import net.dark_roleplay.projectbrazier.features.blocks.flowerpot.FlowerContainerBlock;
import net.dark_roleplay.projectbrazier.features.blocks.lattice_block.AxisLatticeBlock;
import net.dark_roleplay.projectbrazier.features.blocks.lattice_block.FacedLatticeBlock;
import net.dark_roleplay.projectbrazier.features.blocks.nail.HangUpItemBlock;
import net.dark_roleplay.projectbrazier.features.blocks.nail.NailBlock;
import net.dark_roleplay.projectbrazier.features.blocks.brazier.BrazierBlock;
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

	public static Block createFungi(){
		Block.Properties properties =
				Block.Properties.
						create(Material.PLANTS, MaterialColor.WOOD)
						.hardnessAndResistance(1.5F, 6.0F)
						.sound(SoundType.PLANT)
						.notSolid();
		return new WallFungi(properties);
	}

	public static Block createChair(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new ChairBlock(properties, "placeholder");
	}

	public static Block createFlowerBucket(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new FlowerContainerBlock(properties, "placeholder");
	}

	public static Block createFlowerBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new FlowerContainerBlock(properties, "closed_barrel");
	}

	public static Block createClosedBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new BarrelBlock(properties, "closed_barrel", true);
	}

	public static Block createOpenBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new BarrelBlock(properties, "open_barrel", false);
	}

	public static Block createWoodPlatform(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new HAxisDecoBlock(properties, "bottom_wood_platform");
	}

	public static Block createWoodPlatform(IMaterial material, Map<IMaterial, RegistryObject<Block>> bottom){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new PlatformBlock(properties, "top_wood_platform", bottom.get(material));
	}


	public static Block createWoodBench(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new BenchBlock(properties, "default_wood_bench", "positive_wood_bench", "negative_wood_bench", "centered_wood_bench");
	}

	public static Block createPolsteredWoodBench(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD).notSolid();
		return new BenchBlock(properties, "default_polstered_wood_bench", "positive_polstered_wood_bench", "negative_polstered_wood_bench", "centered_polstered_wood_bench");
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