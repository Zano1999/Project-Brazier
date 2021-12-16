package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.dark_roleplay.projectbrazier.feature.helpers.SittingHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class LogBenchBlock extends HFacedDecoBlock {

	protected final HFacedVoxelShape connectedShape;

	public LogBenchBlock(Properties props, String shapeName, String connectedShapeName) {
		super(props, shapeName);
		this.connectedShape = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape(connectedShapeName));
	}

	@Deprecated
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		SittingHelper.sitOnBlockWithRotation(world, pos, player, state.getValue(HORIZONTAL_FACING), state.getValue(HORIZONTAL_FACING), -0.25F, state);
		return InteractionResult.SUCCESS;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return isSingle(world, pos, state) ? shapes.get(state.getValue(HORIZONTAL_FACING)) : connectedShape.get(state.getValue(HORIZONTAL_FACING));
	}

	private boolean isSingle(BlockGetter world, BlockPos pos, BlockState state){
		Direction facing = state.getValue(HORIZONTAL_FACING);
		BlockState otherState =
				world.getBlockState(pos.relative(facing.getClockWise()));
		if(otherState.getBlock() == this && otherState.getValue(HORIZONTAL_FACING) == facing)
			return false;

		otherState =
				world.getBlockState(pos.relative(facing.getCounterClockWise()));

		return !(otherState.getBlock() == this && otherState.getValue(HORIZONTAL_FACING) == facing);
	}
}
