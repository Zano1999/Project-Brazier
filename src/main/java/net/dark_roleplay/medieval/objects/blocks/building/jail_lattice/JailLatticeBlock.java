package net.dark_roleplay.medieval.objects.blocks.building.jail_lattice;

import javax.annotation.Nullable;

import net.dark_roleplay.medieval.objects.blocks.templates.AxisBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class JailLatticeBlock extends AxisBlock{

	public static final BooleanProperty TOP = BooleanProperty.create("top");
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");
	
	public JailLatticeBlock(Properties properties) {
		super(properties);
		setShapes(
			Block.makeCuboidShape(0, 0, 7, 16, 16, 9),
			Block.makeCuboidShape(7, 0, 0, 9, 16, 16)
		);
		this.setDefaultState(this.getDefaultState().with(TOP, false).with(BOTTOM, false).with(LEFT, false).with(RIGHT, false));
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = this.getDefaultState().with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().rotateY().getAxis());

		IBlockReader world = context.getWorld();
		BlockPos pos = context.getPos();

		BlockState workerState = world.getBlockState(pos.up());
		state = state.with(TOP, workerState.getBlock() == this && workerState.get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS));

		workerState = world.getBlockState(pos.down());
		state = state.with(BOTTOM, workerState.getBlock() == this && workerState.get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS));

		workerState = world.getBlockState(state.get(HORIZONTAL_AXIS) == Direction.Axis.X ? pos.west() : pos.north());
		state = state.with(RIGHT, workerState.getBlock() == this && workerState.get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS));

		workerState = world.getBlockState(state.get(HORIZONTAL_AXIS) == Direction.Axis.X ? pos.east() : pos.south());
		state = state.with(LEFT, workerState.getBlock() == this && workerState.get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS));

		return state;
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 */
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		BlockState workerState = world.getBlockState(facingPos);
		if(facing == Direction.UP){
			state = state.with(TOP, workerState.getBlock() == this && workerState.get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS));
		}else if(facing == Direction.DOWN){
			state = state.with(BOTTOM, workerState.getBlock() == this && workerState.get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS));
		}else if(facing == Direction.WEST && state.get(HORIZONTAL_AXIS) == Direction.Axis.X || facing == Direction.NORTH && state.get(HORIZONTAL_AXIS) == Direction.Axis.Z){
			state = state.with(RIGHT, workerState.getBlock() == this && workerState.get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS));
		}else if(facing == Direction.EAST && state.get(HORIZONTAL_AXIS) == Direction.Axis.X || facing == Direction.SOUTH && state.get(HORIZONTAL_AXIS) == Direction.Axis.Z){
			state = state.with(LEFT, workerState.getBlock() == this && workerState.get(HORIZONTAL_AXIS) == state.get(HORIZONTAL_AXIS));
		}

		return state;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(TOP, BOTTOM, LEFT, RIGHT);
	}
}
