package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature_client.model_loaders.pane_connected_model.PaneCornerType;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.*;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;

public class PaneConnectedBlock extends Block {
	private static Direction[] ORDERED_HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
	protected final VoxelShape unconditional;
	protected final VoxelShape[] inner_corner = new VoxelShape[4], outer_corner = new VoxelShape[4], vertical = new VoxelShape[4], horizontal = new VoxelShape[4], none = new VoxelShape[4];

	public PaneConnectedBlock(BlockBehaviour.Properties properties, String shapeFolder) {
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
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		VoxelShape shape = unconditional;

		for(int i = 0; i < 4; i++){
			PaneCornerType type = PaneCornerType.getCornerType(world, pos, state, ORDERED_HORIZONTALS[i]);
			shape = Shapes.join(shape, getQuadsForTypeAndIndex(type, i), BooleanOp.OR);
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
