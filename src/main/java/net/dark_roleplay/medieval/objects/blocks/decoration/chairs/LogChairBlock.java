package net.dark_roleplay.medieval.objects.blocks.decoration.chairs;

import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.template.ChairBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.stream.Stream;

public class LogChairBlock extends ChairBlock {

    public LogChairBlock(Properties properties) {
        super(properties);
        this.setShapes(Stream.of(
                Block.makeCuboidShape(13, 7, 1, 15, 11, 13),
                Block.makeCuboidShape(1, 0, 1, 15, 7, 13),
                Block.makeCuboidShape(1, 0, 13, 15, 16, 15),
                Block.makeCuboidShape(1, 7, 1, 3, 11, 13)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get());
    }
}
