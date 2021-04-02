package net.dark_roleplay.projectbrazier.features.blocks;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.features.blocks.barrel.BarrelBlock;
import net.dark_roleplay.projectbrazier.features.blocks.brazier.BrazierBlock;
import net.dark_roleplay.projectbrazier.features.blocks.flowerpot.FlowerContainerBlock;
import net.dark_roleplay.projectbrazier.features.blocks.lattice_block.AxisLatticeBlock;
import net.dark_roleplay.projectbrazier.features.blocks.lattice_block.FacedLatticeBlock;
import net.dark_roleplay.projectbrazier.features.blocks.nail.HangUpItemBlock;
import net.dark_roleplay.projectbrazier.features.blocks.nail.NailBlock;
import net.dark_roleplay.projectbrazier.features.blocks.roofs.RoofBlock;
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

	public static Block createStool(MargMaterial material){
		return new StoolBlock(mkDkWood(material), "stool");
	}

	public static Block createChair(MargMaterial material){
		return new ChairBlock(mkDkWood(material), "simple_chair");
	}

	public static Block createLogChair(MargMaterial material){
		return new ChairBlock(mkDkWood(material), "log_chair");
	}

	public static Block createFirewood(MargMaterial material){
		return new HAxisDecoBlock(mkDkWood(material), "full_block");
	}

	public static Block createFlowerBucket(MargMaterial material){
		return new FlowerContainerBlock(mkDkWood(material), "flower_bucket");
	}

	public static Block createFlowerBarrel(MargMaterial material){
		return new FlowerContainerBlock(mkDkWood(material), "closed_barrel");
	}

	public static Block createClosedBarrel(MargMaterial material){
		return new BarrelBlock(mkDkWood(material), "closed_barrel", true);
	}

	public static Block createOpenBarrel(MargMaterial material){
		return new BarrelBlock(mkDkWood(material), "open_barrel", false);
	}

	public static Block createWoodPlatform(MargMaterial material){
		return new HAxisDecoBlock(mkDkWood(material), "bottom_wood_platform");
	}

	public static Block createWoodPlatform(MargMaterial material, Map<MargMaterial, RegistryObject<Block>> bottom){
		return new PlatformBlock(mkDkWood(material), "top_wood_platform", bottom.get(material));
	}

	public static Block createWoodBench(MargMaterial material){
		return new BenchBlock(mkDkWood(material), "default_wood_bench", "positive_wood_bench", "negative_wood_bench", "centered_wood_bench");
	}

	public static Block createPolsteredWoodBench(MargMaterial material){
		return new BenchBlock(mkDkWood(material), "default_polstered_wood_bench", "positive_polstered_wood_bench", "negative_polstered_wood_bench", "centered_polstered_wood_bench");
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

	public static Block createWoodWindow(MargMaterial material, Map<MargMaterial, RegistryObject<Block>> centeredWindows){
		return new FacedLatticeBlock(mkDkWood(material), "lattice", centeredWindows.get(material));
	}

	public static Block createWoodWindowB(MargMaterial material){
		return new AxisLatticeBlock(mkDkWood(material), "lattice_centered");
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


	public static Block createRoofBlock(){
		Block.Properties properties =
				Block.Properties.
						create(Material.ROCK, MaterialColor.STONE)
						.hardnessAndResistance(1.5F, 6.0F)
						.sound(SoundType.STONE)
						.notSolid();
		return new RoofBlock(properties, "placeholder");
	}

	private static Block.Properties mkDkWood(MargMaterial material){
		return Block.Properties.create(Material.WOOD, material.getProperties().getMapColor())
				.hardnessAndResistance(2.0F, 3.0F)
				.sound(SoundType.WOOD).notSolid();
	}
//	public interface IBlockGenerator{
//		public Block mkBlock(Block.Properties
//	}
}