package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface IDisplayTicker {
	void animateTick(BlockState state, World world, BlockPos pos, Random rand);
}
