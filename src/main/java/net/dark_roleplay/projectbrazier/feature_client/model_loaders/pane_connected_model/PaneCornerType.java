package net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;

public enum PaneCornerType {
	NONE, HORZIONTAL, VERTICAL, INNER_CORNER, OUTER_CORNER;

	public static PaneCornerType getCornerType(IBlockReader world, BlockPos pos, BlockState state, Direction dir){
		boolean front = world.getBlockState(pos.offset(dir)) == state;
		boolean right = world.getBlockState(pos.offset(dir.rotateYCCW())) == state;
		boolean diagonal = front && right && world.getBlockState(pos.offset(dir).offset(dir.rotateYCCW())) == state;

		return diagonal ? PaneCornerType.NONE :
				front && right ? PaneCornerType.OUTER_CORNER :
						front ? (dir.getAxis() == Direction.Axis.Z ? PaneCornerType.VERTICAL : PaneCornerType.HORZIONTAL) :
								right ? (dir.getAxis() == Direction.Axis.Z ? PaneCornerType.HORZIONTAL : PaneCornerType.VERTICAL) : PaneCornerType.INNER_CORNER;
	}
}
