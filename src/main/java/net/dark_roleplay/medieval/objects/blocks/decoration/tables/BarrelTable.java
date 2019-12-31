package net.dark_roleplay.medieval.objects.blocks.decoration.tables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class BarrelTable extends Block {

    VoxelShape shape = Stream.of(
            Block.makeCuboidShape(5, 0, 5, 11, 1, 11),
            Block.makeCuboidShape(7, 1, 7, 9, 15, 9),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public BarrelTable(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
        return shape;
    }
}
