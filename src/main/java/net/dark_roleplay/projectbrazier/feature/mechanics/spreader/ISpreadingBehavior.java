package net.dark_roleplay.projectbrazier.feature.mechanics.spreader;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ISpreadingBehavior {
	BlockState getSpreadingState(BlockState state, Level level, BlockPos pos);
}
