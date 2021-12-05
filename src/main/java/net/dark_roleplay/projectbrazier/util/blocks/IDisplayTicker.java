package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

import java.util.Random;

public interface IDisplayTicker {
	void animateTick(BlockState state, Level world, BlockPos pos, Random rand);
}
