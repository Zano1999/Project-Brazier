package net.dark_roleplay.medieval.objects.blocks.decoration.chairs;

import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.template.ChairBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.stream.Stream;

public class BarrelChairBlock extends ChairBlock {

    public BarrelChairBlock(Properties properties) {
        super(properties);

        setShapes(
                VoxelShapes.combineAndSimplify(VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 0, 16, 8, 16), Stream.of(
                        Block.makeCuboidShape(0, 0, 0, 2, 8, 2),
                        Block.makeCuboidShape(14, 0, 0, 16, 8, 2),
                        Block.makeCuboidShape(14, 0, 14, 16, 8, 16),
                        Block.makeCuboidShape(0, 0, 14, 2, 8, 16)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(), IBooleanFunction.ONLY_FIRST), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 8, 5, 16, 16, 16), Stream.of(
                        Block.makeCuboidShape(3, 8, 5, 13, 16, 13),
                        Block.makeCuboidShape(14, 8, 14, 16, 16, 16),
                        Block.makeCuboidShape(0, 8, 14, 2, 16, 16)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(), IBooleanFunction.ONLY_FIRST), IBooleanFunction.OR)
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        return false;
    }
}