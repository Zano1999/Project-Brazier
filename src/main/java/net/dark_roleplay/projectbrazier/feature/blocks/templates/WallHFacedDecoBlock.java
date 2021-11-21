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

import net.minecraft.block.AbstractBlock.Properties;

public class WallHFacedDecoBlock extends HFacedDecoBlock {

	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	public WallHFacedDecoBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if(context.getClickedFace().getAxis().getPlane() != Direction.Plane.HORIZONTAL) return null;
		BlockState state = this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getClickedFace());
		return canSurvive(state, context.getLevel(), context.getClickedPos()) ? state : null;
	}

	@Override
	@Deprecated
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing.getOpposite() == state.getValue(HORIZONTAL_FACING) && !state.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	@Deprecated
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		Direction direction = state.getValue(HORIZONTAL_FACING);
		BlockPos otherPos = pos.relative(direction.getOpposite());
		BlockState otherState = world.getBlockState(otherPos);
		return direction.getAxis().isHorizontal() && otherState.isFaceSturdy(world, otherPos, direction) && !otherState.isSignalSource();
	}
}