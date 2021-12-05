package net.dark_roleplay.projectbrazier.feature.blocks.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;

public class WallHFacedDecoBlock extends HFacedDecoBlock {

	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	public WallHFacedDecoBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if(context.getClickedFace().getAxis().getPlane() != Direction.Plane.HORIZONTAL) return null;
		BlockState state = this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getClickedFace());
		return canSurvive(state, context.getLevel(), context.getClickedPos()) ? state : null;
	}

	@Override
	@Deprecated
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing.getOpposite() == state.getValue(HORIZONTAL_FACING) && !state.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Deprecated
	public boolean canSurvive(BlockState state, BlockGetter world, BlockPos pos) {
		Direction direction = state.getValue(HORIZONTAL_FACING);
		BlockPos otherPos = pos.relative(direction.getOpposite());
		BlockState otherState = world.getBlockState(otherPos);
		return direction.getAxis().isHorizontal() && otherState.isFaceSturdy(world, otherPos, direction) && !otherState.isSignalSource();
	}
}