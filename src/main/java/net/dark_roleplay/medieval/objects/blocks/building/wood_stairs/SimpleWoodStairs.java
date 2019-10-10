package net.dark_roleplay.medieval.objects.blocks.building.wood_stairs;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.dark_roleplay.medieval.objects.enums.WoodStairsType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class SimpleWoodStairs extends HorizontalBlock {

    public static final BooleanProperty GROUNDED = BooleanProperty.create("grounded");
    public static final EnumProperty<WoodStairsType> TYPE = EnumProperty.create("type", WoodStairsType.class);


    public SimpleWoodStairs(Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(GROUNDED, TYPE);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());

        Direction facing = state.get(HORIZONTAL_FACING);

        IBlockReader world = context.getWorld();
        BlockPos pos = context.getPos();

        state = state.with(GROUNDED, world.getBlockState(pos.down()).func_224755_d(world, pos.down(), Direction.UP));
        state = state.with(TYPE, (world.getBlockState(pos.offset(facing.rotateY())).getBlock() == this ||
                world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() == this) ? WoodStairsType.SINGLE : WoodStairsType.DOUBLE);

        return state;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if(facing != Direction.DOWN) return state;
        return state.with(GROUNDED, world.getBlockState(facingPos).func_224755_d(world, facingPos, Direction.UP));
    }
}