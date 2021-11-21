package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.dark_roleplay.projectbrazier.util.sitting.SittingUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class LogBenchBlock extends HFacedDecoBlock {

	protected final HFacedVoxelShape connectedShape;

	public LogBenchBlock(Properties props, String shapeName, String connectedShapeName) {
		super(props, shapeName);
		this.connectedShape = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape(connectedShapeName));
	}

	@Deprecated
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		SittingUtil.sitOnBlockWithRotation(world, pos, player, state.getValue(HORIZONTAL_FACING), state.getValue(HORIZONTAL_FACING), -0.25F, state);
		return ActionResultType.SUCCESS;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return isSingle(world, pos, state) ? shapes.get(state.getValue(HORIZONTAL_FACING)) : connectedShape.get(state.getValue(HORIZONTAL_FACING));
	}

	private boolean isSingle(IBlockReader world, BlockPos pos, BlockState state){
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
