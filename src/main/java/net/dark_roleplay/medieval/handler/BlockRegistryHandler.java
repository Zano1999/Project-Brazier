package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.bedrock_entities.tester.ModelTesterBlock;
import net.dark_roleplay.marg.api.Constants;
import net.dark_roleplay.marg.api.MaterialRequirements;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.building.jail_lattice.JailLatticeBlock;
import net.dark_roleplay.medieval.objects.blocks.building.platforms.WoodPlatformBlock;
import net.dark_roleplay.medieval.objects.blocks.building.platforms.WoodPlatformStairsBlock;
import net.dark_roleplay.medieval.objects.blocks.building.timbered_clay.TimberedClay;
import net.dark_roleplay.medieval.objects.blocks.building.wooden_window.WoodenWindowBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.advent_wreath.AdventWreathBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.benches.BenchBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.PlankChairBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairArmrestBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairTileEntity;
import net.dark_roleplay.medieval.objects.blocks.decoration.light_sources.TorchHolderBlock;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSign;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntity;
import net.dark_roleplay.medieval.objects.blocks.decoration.tables.SimpleTable;
import net.dark_roleplay.medieval.objects.blocks.decoration.wall_brazier.WallBrazierBlock;
import net.dark_roleplay.medieval.objects.blocks.utility.*;
import net.dark_roleplay.medieval.objects.blocks.utility.chopping_block.ChoppingBlock;
import net.dark_roleplay.medieval.objects.blocks.utility.chopping_block.ChoppingTileEntity;
import net.dark_roleplay.medieval.objects.enums.TimberedClayEnums.TimberedClayType;
import net.dark_roleplay.medieval.util.AdvancedInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Bus.MOD)
public class BlockRegistryHandler {

	private static IForgeRegistry<Block>	registry	= null;

	private static Block.Properties			PLACEHOLDER	= Block.Properties.create(Material.WOOD);

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Block> registryEvent) {
		registry = registryEvent.getRegistry();

		reg(new TorchHolderBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL)), "torch_holder");
		reg(new AdventWreathBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.PLANT)), "advent_wreath");
		reg(new WallBrazierBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL)), "wall_brazier");

		reg(new JailLatticeBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.METAL)), "jail_lattice");

		reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "riverstone");
		reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "large_riverstone");
		reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "large_riverstone_dark");
		reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "riverstone_colored");
		reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "riverstone_colored_pale");

		reg(new RopeAnchor(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), "rope_anchor", AnchorTileEntity.class);
		reg(new Rope(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), "rope");
		reg(new RopeLadderAnchor(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), "oak_rope_ladder_anchor", AnchorTileEntity.class);
		reg(new RopeLadder(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), "oak_rope_ladder");

		reg(new ModelTesterBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD)), "model_tester");

		reg(new Block(PLACEHOLDER), "clean_timbered_clay");

		MaterialRequirements planks = new MaterialRequirements(Constants.MAT_WOOD, "planks");
		MaterialRequirements log = new MaterialRequirements(Constants.MAT_WOOD, Constants.MatWood.LOG_SIDE, Constants.MatWood.LOG_TOP);

		log.execute(material -> {
			reg(new ChoppingBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), material.getNamed("%wood%_chopping_block"), ChoppingTileEntity.class);
		});

		planks.execute(material -> {
			for(TimberedClayType type : TimberedClayType.values()) {
				if(type == TimberedClayType.CLEAN) continue;
				reg(new TimberedClay(PLACEHOLDER, type), String.format("%s_%s_timbered_clay", material.getName(), type.getName()));
			}
			reg(new TimberedClay(PLACEHOLDER, TimberedClayType.CLEAN), material.getNamed("clean_%wood%_timbered_clay"));

			reg(new WoodenWindowBlock(PLACEHOLDER), material.getNamed("vertical_%wood%_window"));
			reg(new WoodenWindowBlock(PLACEHOLDER), material.getNamed("dense_vertical_%wood%_window"));
			reg(new WoodenWindowBlock(PLACEHOLDER), material.getNamed("cross_%wood%_window"));
			reg(new WoodenWindowBlock(PLACEHOLDER), material.getNamed("grid_%wood%_window"));
			reg(new WoodenWindowBlock(PLACEHOLDER), material.getNamed("diamond_%wood%_window"));

			reg(new SimpleTable(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), material.getNamed("solid_%wood%_table"));
			reg(new PlankChairBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), material.getNamed("%wood%_plank_chair"));
			reg(new SolidChairBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), material.getNamed("%wood%_solid_chair"));
			reg(new BenchBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), material.getNamed("%wood%_solid_bench"));
			reg(new WoodPlatformBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), material.getNamed("%wood%_platform"));
			reg(new WoodPlatformStairsBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), material.getNamed("%wood%_platform_stairs"));
			reg(new RoadSign(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)),material.getNamed("%wood%_road_sign"), RoadSignTileEntity.class);

			//Also register for a TileEntity
			reg(new SolidChairArmrestBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0F, 3.0F).sound(SoundType.WOOD)), String.format("%s_solid_chair_armrest", material.getName()), SolidChairTileEntity.class);
		});
	}

	protected static void reg(Block block, String registryName) {
		block.setRegistryName(new ResourceLocation(DarkRoleplayMedieval.MODID, registryName));
		registry.register(block);
	}

	protected static void reg(Block block, String registryName, Class<? extends TileEntity> teClass) {
		reg(block, registryName);
		TileEntityRegistryHandler.addTileEntityBlock(teClass, block);
	}
}
