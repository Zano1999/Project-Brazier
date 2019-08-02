package net.dark_roleplay.medieval.objects.blocks.decoration.chairs;

import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;

public class SolidChairArmrestBlock extends SolidChairBlock {
	
	public SolidChairArmrestBlock(Properties properties) {
		super(properties);
		this.shapes.clear();
		
		setShapes(
				Stream.of(
					Block.makeCuboidShape(1.5, 0, 3, 3.5, 7, 5),
					Block.makeCuboidShape(12.5, 0, 3, 14.5, 7, 5),
					Block.makeCuboidShape(12.5, 0, 13, 14.5, 18, 15),
					Block.makeCuboidShape(1.5, 0, 13, 3.5, 18, 15),
					Block.makeCuboidShape(1, 7, 2.5, 15, 8, 15),
					Block.makeCuboidShape(3.5, 11, 13.5, 12.5, 17, 14.5),
					Block.makeCuboidShape(1.4982501130105916, 7.997374374737585, 3.0043709336656583, 3.4982501130105916, 11.997374374737586, 5.004370933665658),
					Block.makeCuboidShape(12.498250113010592, 7.997374374737585, 3.0043709336656583, 14.498250113010592, 11.997374374737586, 5.004370933665658),
					Block.makeCuboidShape(12.248250113010592, 11.997374374737586, 2.5043709336656583, 14.748250113010592, 12.997374374737586, 13.004370933665658),
					Block.makeCuboidShape(1.2482501130105916, 11.997374374737586, 2.5043709336656583, 3.7482501130105916, 12.997374374737586, 13.004370933665658),
					VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 5.5, 3.5, 14, 7, 14.5), Block.makeCuboidShape(3, 5.5, 4.5, 13, 7, 13.5), IBooleanFunction.ONLY_FIRST)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
			);
		
		setShapesCompartment(
			Stream.of(
				Block.makeCuboidShape(1.5, 0, 3, 3.5, 7, 5),
				Block.makeCuboidShape(12.5, 0, 3, 14.5, 7, 5),
				Block.makeCuboidShape(12.5, 0, 13, 14.5, 18, 15),
				Block.makeCuboidShape(1.5, 0, 13, 3.5, 18, 15),
				Block.makeCuboidShape(1, 7, 2.5, 15, 8, 15),
				Block.makeCuboidShape(3.5, 11, 13.5, 12.5, 17, 14.5),
				Block.makeCuboidShape(1.4982501130105916, 7.997374374737585, 3.0043709336656583, 3.4982501130105916, 11.997374374737586, 5.004370933665658),
				Block.makeCuboidShape(12.498250113010592, 7.997374374737585, 3.0043709336656583, 14.498250113010592, 11.997374374737586, 5.004370933665658),
				Block.makeCuboidShape(12.248250113010592, 11.997374374737586, 2.5043709336656583, 14.748250113010592, 12.997374374737586, 13.004370933665658),
				Block.makeCuboidShape(1.2482501130105916, 11.997374374737586, 2.5043709336656583, 3.7482501130105916, 12.997374374737586, 13.004370933665658),
				Block.makeCuboidShape(6.5, 6.5, 14.5, 9.5, 7, 14.75),
				Block.makeCuboidShape(2, 5.5, 3.5, 14, 7, 14.5)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
		);
		
		setButtons(
			Block.makeCuboidShape(6.5, 6.5, 14.5, 9.5, 7, 14.75),
			Block.makeCuboidShape(1.25, 6.5, 6.5, 1.5, 7, 9.5),
			Block.makeCuboidShape(6.5, 6.5, 1.25, 9.5, 7, 1.5), 
			Block.makeCuboidShape(14.5, 6.5, 6.5, 14.75, 7, 9.5)
		);
	}
}
