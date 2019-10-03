package net.dark_roleplay.medieval.objects.blocks.building.wooden_window;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class WoodenWindowBlock extends HorizontalBlock {

    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    public WoodenWindowBlock(Properties properties) {
        super(properties);
        setShapes(
                Block.makeCuboidShape(0, 0, 0, 16, 16, 4)
        );
        this.setDefaultState(this.getDefaultState().with(TOP, false).with(BOTTOM, false).with(LEFT, false).with(RIGHT, false));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());

        Direction facing = state.get(HORIZONTAL_FACING);

        IBlockReader world = context.getWorld();
        BlockPos pos = context.getPos();

        return state;
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        BlockState workerState = world.getBlockState(facingPos);
        Direction facing2 = state.get(HORIZONTAL_FACING);

        if (facing == Direction.UP) {
            state = state.with(TOP, workerState.getBlock() == this && workerState.get(HORIZONTAL_FACING) == facing2);
        } else if (facing == Direction.DOWN) {
            state = state.with(BOTTOM, workerState.getBlock() == this && workerState.get(HORIZONTAL_FACING) == facing2);
        } else if (facing == facing2.rotateY()) {
            state = state.with(RIGHT, workerState.getBlock() == this && workerState.get(HORIZONTAL_FACING) == facing2);
        } else if (facing == facing2.rotateYCCW()) {
            state = state.with(LEFT, workerState.getBlock() == this && workerState.get(HORIZONTAL_FACING) == facing2);
        }
        return state;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(TOP, BOTTOM, LEFT, RIGHT);
    }
}
