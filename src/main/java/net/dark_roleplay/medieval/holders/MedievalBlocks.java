package net.dark_roleplay.medieval.holders;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(DarkRoleplayMedieval.MODID)
public class MedievalBlocks {

	/* Decoration */
	public static final Block
		JAIL_LATTICE				= null,
		RIVERSTONE					= null,
		RIVERSTONE_COLORED			= null,
		RIVERSTONE_COLORED_PALE		= null,
		LARGE_RIVERSTONE			= null,			
		LARGE_RIVERSTONE_DARK		= null,
		ADVENT_WREATH				= null,
		TORCH_HOLDER				= null,
		OAK_PLANK_CHAIR				= null,
		OAK_SOLID_CHAIR				= null,
		OAK_SOLID_CHAIR_ARMREST		= null,
		OAK_SOLID_BENCH				= null,
		OAK_PLATFORM				= null,
		WALL_BRAZIER				= null;
}

//reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "riverstone");
//reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "large_riverstone");
//reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "large_riverstone_dark");
//reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "riverstone_colored");
//reg(new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)), "riverstone_colored_pale");