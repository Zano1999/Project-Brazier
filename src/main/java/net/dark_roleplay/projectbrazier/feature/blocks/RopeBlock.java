package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class RopeBlock extends HFacedDecoBlock {

	protected final HFacedVoxelShape droppedShapes;

	public static final BooleanProperty IS_END = BooleanProperty.create("end");

	public RopeBlock(Properties props, String shapeName, String droppedShape) {
		super(props, shapeName);
		this.droppedShapes = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape(droppedShape));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(IS_END) ? droppedShapes.get(state.get(HORIZONTAL_FACING)) : shapes.get(state.get(HORIZONTAL_FACING));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(IS_END);
	}

	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if(facing == Direction.UP && facingState.isAir(world, facingPos)){
			if(world.getBlockState(facingPos.offset(state.get(HORIZONTAL_FACING).getOpposite())).getBlock() != BrazierBlocks.ROPE_ANCHOR.get())
				return Blocks.AIR.getDefaultState();
		}

		return state;
	}
}
