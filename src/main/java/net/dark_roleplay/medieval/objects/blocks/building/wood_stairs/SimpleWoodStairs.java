package net.dark_roleplay.medieval.objects.blocks.building.wood_stairs;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.dark_roleplay.medieval.objects.enums.WoodStairsType;
import net.dark_roleplay.medieval.util.blocks.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.stream.Stream;

public class SimpleWoodStairs extends HorizontalBlock {

    public static final BooleanProperty GROUNDED = BooleanProperty.create("grounded");
    public static final EnumProperty<WoodStairsType> TYPE = EnumProperty.create("type", WoodStairsType.class);

    protected final EnumMap<Direction, VoxelShape> doubleShapes = new EnumMap<Direction, VoxelShape>(Direction.class);

    public SimpleWoodStairs(Properties properties) {
        super(properties);
        setShapes(
                Stream.of(
                        Block.makeCuboidShape(0, 6, 0, 16, 7, 8),
                        Block.makeCuboidShape(0, 14, 8, 16, 15, 16),
                        Block.makeCuboidShape(1, 5, 0, 2, 6, 9),
                        Block.makeCuboidShape(1, 13, 8, 2, 14, 16),
                        Block.makeCuboidShape(14, 5, 0, 15, 6, 9),
                        Block.makeCuboidShape(14, 13, 8, 15, 14, 16),
                        Block.makeCuboidShape(1, 0, 0, 2, 1, 4),
                        Block.makeCuboidShape(14, 0, 0, 15, 1, 4),
                        Block.makeCuboidShape(1, 1, 0.5, 2, 2, 5),
                        Block.makeCuboidShape(14, 1, 0.5, 15, 2, 5),
                        Block.makeCuboidShape(1, 2, 1.5, 2, 3, 6),
                        Block.makeCuboidShape(14, 2, 1.5, 15, 3, 6),
                        Block.makeCuboidShape(1, 3, 2.5, 2, 4, 7),
                        Block.makeCuboidShape(14, 3, 2.5, 15, 4, 7),
                        Block.makeCuboidShape(1, 4, 3.5, 2, 5, 8),
                        Block.makeCuboidShape(14, 4, 3.5, 15, 5, 8),
                        Block.makeCuboidShape(1, 6, 5.5, 2, 7, 10),
                        Block.makeCuboidShape(14, 6, 5.5, 15, 7, 10),
                        Block.makeCuboidShape(1, 7, 6.5, 2, 8, 11),
                        Block.makeCuboidShape(14, 7, 6.5, 15, 8, 11),
                        Block.makeCuboidShape(1, 8, 7.5, 2, 9, 12),
                        Block.makeCuboidShape(14, 8, 7.5, 15, 9, 12),
                        Block.makeCuboidShape(1, 9, 8.5, 2, 10, 13),
                        Block.makeCuboidShape(14, 9, 8.5, 15, 10, 13),
                        Block.makeCuboidShape(1, 10, 9.5, 2, 11, 14),
                        Block.makeCuboidShape(14, 10, 9.5, 15, 11, 14),
                        Block.makeCuboidShape(1, 11, 10.5, 2, 12, 15),
                        Block.makeCuboidShape(14, 11, 10.5, 15, 12, 15),
                        Block.makeCuboidShape(1, 12, 11.5, 2, 13, 16),
                        Block.makeCuboidShape(14, 12, 11.5, 15, 13, 16)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
        setDoubleShapes(Stream.of(
                Block.makeCuboidShape(0, 6, 0, 16, 7, 8),
                Block.makeCuboidShape(0, 14, 8, 16, 15, 16),
                Block.makeCuboidShape(7.5, 5, 0, 8.5, 6, 9),
                Block.makeCuboidShape(7.5, 13, 8, 8.5, 14, 16),
                Block.makeCuboidShape(7.5, 0, 0, 8.5, 1, 4),
                Block.makeCuboidShape(7.5, 1, 0.5, 8.5, 2, 5),
                Block.makeCuboidShape(7.5, 2, 1.5, 8.5, 3, 6),
                Block.makeCuboidShape(7.5, 3, 2.5, 8.5, 4, 7),
                Block.makeCuboidShape(7.5, 4, 3.5, 8.5, 5, 8),
                Block.makeCuboidShape(7.5, 6, 5.5, 8.5, 7, 10),
                Block.makeCuboidShape(7.5, 7, 6.5, 8.5, 8, 11),
                Block.makeCuboidShape(7.5, 8, 7.5, 8.5, 9, 12),
                Block.makeCuboidShape(7.5, 9, 8.5, 8.5, 10, 13),
                Block.makeCuboidShape(7.5, 10, 9.5, 8.5, 11, 14),
                Block.makeCuboidShape(7.5, 11, 10.5, 8.5, 12, 15),
                Block.makeCuboidShape(7.5, 12, 11.5, 8.5, 13, 16)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
        this.setDefaultState(this.getDefaultState().with(GROUNDED, true).with(TYPE, WoodStairsType.SINGLE));
    }


    protected void setDoubleShapes(VoxelShape north) {
        this.doubleShapes.put(Direction.NORTH, north);
        this.doubleShapes.put(Direction.EAST, VoxelShapeHelper.rotateShape(north, Direction.EAST));
        this.doubleShapes.put(Direction.SOUTH, VoxelShapeHelper.rotateShape(north, Direction.SOUTH));
        this.doubleShapes.put(Direction.WEST, VoxelShapeHelper.rotateShape(north, Direction.WEST));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
        return state.get(TYPE) == WoodStairsType.SINGLE ? shapes.get(state.get(HORIZONTAL_FACING)) : doubleShapes.get(state.get(HORIZONTAL_FACING));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(GROUNDED, TYPE);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());

        Direction facing = state.get(HORIZONTAL_FACING);

        IBlockReader world = context.getWorld();
        BlockPos pos = context.getPos();

        state = state.with(GROUNDED, world.getBlockState(pos.down()).func_224755_d(world, pos.down(), Direction.UP));
        state = state.with(TYPE, (world.getBlockState(pos.offset(facing.rotateY())).getBlock() == this ||
                world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() == this) ? WoodStairsType.DOUBLE : WoodStairsType.SINGLE);

        return state;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.DOWN)
            return state.with(GROUNDED, world.getBlockState(facingPos).func_224755_d(world, facingPos, Direction.UP));
        else if(facing == state.get(HORIZONTAL_FACING).rotateY() || facing == state.get(HORIZONTAL_FACING).rotateYCCW())
            return state.with(TYPE, (facingState.getBlock() == this ? WoodStairsType.DOUBLE : WoodStairsType.SINGLE));
        else
            return state;
    }
}