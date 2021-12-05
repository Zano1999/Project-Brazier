package net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public enum PaneCornerType {
	NONE, HORZIONTAL, VERTICAL, INNER_CORNER, OUTER_CORNER;

	public static PaneCornerType getCornerType(BlockGetter world, BlockPos pos, BlockState state, Direction dir) {
		boolean front = world.getBlockState(pos.relative(dir)) == state;
		boolean right = world.getBlockState(pos.relative(dir.getCounterClockWise())) == state;
		boolean diagonal = front && right && world.getBlockState(pos.relative(dir).relative(dir.getCounterClockWise())) == state;

		return diagonal ? PaneCornerType.NONE :
				front && right ? PaneCornerType.OUTER_CORNER :
						front ? (dir.getAxis() == Direction.Axis.Z ? PaneCornerType.VERTICAL : PaneCornerType.HORZIONTAL) :
								right ? (dir.getAxis() == Direction.Axis.Z ? PaneCornerType.HORZIONTAL : PaneCornerType.VERTICAL) : PaneCornerType.INNER_CORNER;
	}
}
