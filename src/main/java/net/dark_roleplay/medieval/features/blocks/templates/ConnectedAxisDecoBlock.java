package net.dark_roleplay.medieval.features.blocks.templates;

import net.dark_roleplay.medieval.features.model_loaders.connected_models.ConnectionType;
import net.dark_roleplay.medieval.util.blocks.AxisVoxelShape;
import net.dark_roleplay.medieval.util.json.VoxelShapeLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ConnectedAxisDecoBlock extends AxisDecoBlock {

	protected final AxisVoxelShape positiveShapes;
	protected final AxisVoxelShape negativeShapes;
	protected final AxisVoxelShape centeredShapes;

	public ConnectedAxisDecoBlock(Properties properties, String shapeName, String positiveShapeName, String negativeShapeName,String centeredShapeName) {
		super(properties, shapeName);
		this.positiveShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(positiveShapeName));
		this.negativeShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(negativeShapeName));
		this.centeredShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(centeredShapeName));
	}

	@Override
	@Deprecated
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return shapes.get(state.get(HORIZONTAL_AXIS));
	}

	//TODO Optimize getRaytraceShape
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		System.out.println("Test");
		ConnectionType type = ConnectionType.getConnections(world, pos, state);
		switch(type){
			case DEFAULT:
				return shapes.get(state.get(HORIZONTAL_AXIS));
			case POSITIVE:
				return positiveShapes.get(state.get(HORIZONTAL_AXIS));
			case NEGATIVE:
				return negativeShapes.get(state.get(HORIZONTAL_AXIS));
			case CENTERED:
				return centeredShapes.get(state.get(HORIZONTAL_AXIS));
		}

		return shapes.get(state.get(HORIZONTAL_AXIS));
	}
}