package net.dark_roleplay.medieval.objects.blocks.decoration.chairs;

import java.util.stream.Stream;

import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.template.ChairBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;

public class PlankChairBlock extends ChairBlock{

	public PlankChairBlock(Properties properties) {
		super(properties);

		setShapes(
				Stream.of(
				Block.makeCuboidShape(1.5, 0, 3, 3.5, 7, 5),
				Block.makeCuboidShape(12.5, 0, 3, 14.5, 7, 5),
				Block.makeCuboidShape(12.5, 0, 13, 14.5, 18, 15),
				Block.makeCuboidShape(1.5, 0, 13, 3.5, 18, 15),
				VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 5.5, 3.5, 14, 7, 14.5), Block.makeCuboidShape(3, 5.5, 4.5, 13, 7, 13.5), IBooleanFunction.ONLY_FIRST),
				Block.makeCuboidShape(1, 7, 2.5, 15, 8, 15),
				Block.makeCuboidShape(3.5, 11, 13.5, 12.5, 17, 14.5)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
		);
	}

}
