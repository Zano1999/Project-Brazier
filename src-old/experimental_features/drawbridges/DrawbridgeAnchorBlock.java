package net.dark_roleplay.projectbrazier.experimental_features.drawbridges;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;

public class DrawbridgeAnchorBlock extends Block {

	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	public DrawbridgeAnchorBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}


	@Override
	public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
		return new DrawbridgeAnchorTileEntity(state.getValue(HORIZONTAL_FACING));
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		BlockEntity te = world.getBlockEntity(pos);

		if(te != null && te instanceof DrawbridgeAnchorTileEntity){
			DrawbridgeAnchorTileEntity anchorTe = (DrawbridgeAnchorTileEntity) te;
			if(world.hasNeighborSignal(pos)) anchorTe.startLowering();
			else anchorTe.startRaising();
		}
	}
}
