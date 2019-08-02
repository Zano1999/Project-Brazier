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

import javax.annotation.Nullable;

public class SimpleTable extends Block {

    protected static final BooleanProperty LEG_NORTH_EAST = BooleanProperty.create("leg_north_east");
    protected static final BooleanProperty LEG_NORTH_WEST = BooleanProperty.create("leg_north_west");
    protected static final BooleanProperty LEG_SOUTH_EAST = BooleanProperty.create("leg_south_east");
    protected static final BooleanProperty LEG_SOUTH_WEST = BooleanProperty.create("leg_south_west");


    public SimpleTable(Properties properties) {
        super(properties);
    }


    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEG_NORTH_EAST);
        builder.add(LEG_NORTH_WEST);
        builder.add(LEG_SOUTH_EAST);
        builder.add(LEG_SOUTH_WEST);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return null;
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
