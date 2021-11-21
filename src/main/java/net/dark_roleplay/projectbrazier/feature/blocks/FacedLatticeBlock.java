package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.util.blocks.FacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class FacedLatticeBlock extends Block {

	protected static final DirectionProperty FACING = BlockStateProperties.FACING;

	protected final FacedVoxelShape shapes;
	protected Block otherBlock;

	public FacedLatticeBlock(Properties props, String shapeName) {
		super(props);
		this.shapes = new FacedVoxelShape(VoxelShapeLoader.getVoxelShape(shapeName));
	}

	public void initOtherBlock(Block otherBlock) {
		this.otherBlock = otherBlock;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shapes.get(state.getValue(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		double hitX = context.getClickLocation().x() - context.getClickedPos().getX();
		double hitY = context.getClickLocation().y() - context.getClickedPos().getY();
		double hitZ = context.getClickLocation().z() - context.getClickedPos().getZ();

		Direction.Axis playerAxis = context.getHorizontalDirection().getAxis();

		if (context.getClickedFace().getAxis() == Direction.Axis.Y) {
			if (hitX > 0.75 && playerAxis == Direction.Axis.X) {
				return this.defaultBlockState().setValue(FACING, Direction.EAST);
			} else if (hitX < 0.25 && playerAxis == Direction.Axis.X) {
				return this.defaultBlockState().setValue(FACING, Direction.WEST);
			} else if (hitZ > 0.75 && playerAxis == Direction.Axis.Z) {
				return this.defaultBlockState().setValue(FACING, Direction.SOUTH);
			} else if (hitZ < 0.25 && playerAxis == Direction.Axis.Z) {
				return this.defaultBlockState().setValue(FACING, Direction.NORTH);
			} else {
				return this.otherBlock.defaultBlockState().setValue(AxisLatticeBlock.AXIS, context.getHorizontalDirection().getAxis());
			}
		} else if (context.getClickedFace().getAxis() == Direction.Axis.Z) {
			if (hitY > 0.75) {
				return this.defaultBlockState().setValue(FACING, Direction.UP);
			} else if (hitY < 0.25) {
				return this.defaultBlockState().setValue(FACING, Direction.DOWN);
			}
			if (hitX > 0.75) {
				return this.defaultBlockState().setValue(FACING, Direction.EAST);
			} else if (hitX < 0.25) {
				return this.defaultBlockState().setValue(FACING, Direction.WEST);
			} else {
				return this.otherBlock.defaultBlockState().setValue(AxisLatticeBlock.AXIS, Direction.Axis.Y);
			}
		} else if (context.getClickedFace().getAxis() == Direction.Axis.X) {
			if (hitY > 0.75) {
				return this.defaultBlockState().setValue(FACING, Direction.UP);
			} else if (hitY < 0.25) {
				return this.defaultBlockState().setValue(FACING, Direction.DOWN);
			} else if (hitZ > 0.75) {
				return this.defaultBlockState().setValue(FACING, Direction.SOUTH);
			} else if (hitZ < 0.25) {
				return this.defaultBlockState().setValue(FACING, Direction.NORTH);
			} else {
				return this.otherBlock.defaultBlockState().setValue(AxisLatticeBlock.AXIS, Direction.Axis.Y);
			}
		}

		return this.defaultBlockState().setValue(FACING, Direction.UP);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
		return false;
	}
}