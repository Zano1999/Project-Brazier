package net.dark_roleplay.projectbrazier.feature.blocks.templates;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class WallHFacedDecoBlock extends HFacedDecoBlock {

	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	public WallHFacedDecoBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if(context.getFace().getAxis().getPlane() != Direction.Plane.HORIZONTAL) return null;
		BlockState state = this.getDefaultState().with(HORIZONTAL_FACING, context.getFace());
		return isValidPosition(state, context.getWorld(), context.getPos()) ? state : null;
	}

	@Override
	@Deprecated
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing.getOpposite() == state.get(HORIZONTAL_FACING) && !state.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	@Deprecated
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		Direction direction = state.get(HORIZONTAL_FACING);
		BlockPos otherPos = pos.offset(direction.getOpposite());
		BlockState otherState = world.getBlockState(otherPos);
		return direction.getAxis().isHorizontal() && otherState.isSolidSide(world, otherPos, direction) && !otherState.canProvidePower();
	}
}