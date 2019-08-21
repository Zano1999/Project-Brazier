package net.dark_roleplay.medieval.objects.blocks.decoration.tables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SimpleTable extends Block {

    protected static final BooleanProperty CONNECTED_N = BooleanProperty.create("connected_n");
    protected static final BooleanProperty CONNECTED_E = BooleanProperty.create("connected_e");
    protected static final BooleanProperty CONNECTED_S = BooleanProperty.create("connected_s");
    protected static final BooleanProperty CONNECTED_W = BooleanProperty.create("connected_w");

    protected static final BooleanProperty LEG_NE = BooleanProperty.create("leg_ne");
    protected static final BooleanProperty LEG_NW = BooleanProperty.create("leg_nw");
    protected static final BooleanProperty LEG_SE = BooleanProperty.create("leg_se");
    protected static final BooleanProperty LEG_SW = BooleanProperty.create("leg_sw");

    public SimpleTable(Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CONNECTED_N, CONNECTED_E, CONNECTED_S, CONNECTED_W);
        builder.add(LEG_NE, LEG_NW, LEG_SE, LEG_SW);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();

        BlockState state = this.getDefaultState();
        boolean n = world.getBlockState(pos.north()).getBlock() == this;
        boolean e = world.getBlockState(pos.east()).getBlock() == this;
        boolean s = world.getBlockState(pos.south()).getBlock() == this;
        boolean w = world.getBlockState(pos.west()).getBlock() == this;

        state = state.with(CONNECTED_N, n);
        state = state.with(CONNECTED_E, e);
        state = state.with(CONNECTED_S, s);
        state = state.with(CONNECTED_W, w);

        state = state.with(LEG_NE, !((n || e) && world.getBlockState(pos.north().east()).getBlock() == this));
        state = state.with(LEG_NW, !((n || w) && world.getBlockState(pos.north().west()).getBlock() == this));
        state = state.with(LEG_SE, !((s || e) && world.getBlockState(pos.south().east()).getBlock() == this));
        state = state.with(LEG_SW, !((s || w) && world.getBlockState(pos.south().west()).getBlock() == this));
        return state;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (facing != Direction.DOWN)
            return state;
        if (!Block.func_220064_c(world, facingPos))
            return Blocks.AIR.getDefaultState();
        return state;
    }
}
