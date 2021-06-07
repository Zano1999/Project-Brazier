package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model.PaneCornerType;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class PaneConnectedBlock extends Block {
	private static Direction[] ORDERED_HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
	protected final VoxelShape unconditional;
	protected final VoxelShape[] inner_corner = new VoxelShape[4], outer_corner = new VoxelShape[4], vertical = new VoxelShape[4], horizontal = new VoxelShape[4], none = new VoxelShape[4];

	public PaneConnectedBlock(AbstractBlock.Properties properties, String shapeFolder) {
		super(properties);
		this.unconditional = VoxelShapeLoader.getVoxelShape(shapeFolder + "/unconditional");

		for(int i = 0; i < 4; i++){
			inner_corner[i] = VoxelShapeLoader.getVoxelShape(shapeFolder + "/inner_corner_" + (i+1));
			outer_corner[i] = VoxelShapeLoader.getVoxelShape(shapeFolder + "/outer_corner_" + (i+1));
			vertical[i] = VoxelShapeLoader.getVoxelShape(shapeFolder + "/vertical_" + (i+1));
			horizontal[i] = VoxelShapeLoader.getVoxelShape(shapeFolder + "/horizontal_" + (i+1));
			none[i] = VoxelShapeLoader.getVoxelShape(shapeFolder + "/none_" + (i+1));
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		VoxelShape shape = unconditional;

		for(int i = 0; i < 4; i++){
			PaneCornerType type = PaneCornerType.getCornerType(world, pos, state, ORDERED_HORIZONTALS[i]);
			shape = VoxelShapes.combineAndSimplify(shape, getQuadsForTypeAndIndex(type, i), IBooleanFunction.OR);
		}

		return shape;
	}

	private VoxelShape getQuadsForTypeAndIndex(PaneCornerType type, int index){
		switch(type){
			case HORZIONTAL: return this.horizontal[index];
			case VERTICAL: return this.vertical[index];
			case INNER_CORNER: return this.inner_corner[index];
			case OUTER_CORNER: return this.outer_corner[index];
			case NONE:
			default:
				return this.none[index];
		}
	}
}
