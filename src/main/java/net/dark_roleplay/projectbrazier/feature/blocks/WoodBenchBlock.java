package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HAxisDecoBlock;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.axis_connected_models.AxisConnectionType;
import net.dark_roleplay.projectbrazier.util.blocks.AxisVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.dark_roleplay.projectbrazier.util.sitting.SittingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class WoodBenchBlock extends HAxisDecoBlock {

	protected final AxisVoxelShape positiveShapes;
	protected final AxisVoxelShape negativeShapes;
	protected final AxisVoxelShape centeredShapes;

	public WoodBenchBlock(Properties properties, String shapeName, String positiveShapeName, String negativeShapeName, String centeredShapeName) {
		super(properties, shapeName);
		this.positiveShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(positiveShapeName));
		this.negativeShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(negativeShapeName));
		this.centeredShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(centeredShapeName));
	}


	@Deprecated
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		Direction.Axis axis = state.getValue(HORIZONTAL_AXIS);

		Direction facing = null;

		float yaw = player.getYHeadRot();

		if(axis == Direction.Axis.Z && yaw > 0 && yaw< 180) facing = Direction.WEST;
		else if(axis == Direction.Axis.Z) facing = Direction.EAST;
		else if(axis == Direction.Axis.X && yaw > -90 && yaw < 90) facing = Direction.SOUTH;
		else if(axis == Direction.Axis.X) facing = Direction.NORTH;

		SittingUtil.sitOnBlock(world, pos, player, facing, -0.18F, state);

		return InteractionResult.SUCCESS;
	}

	@Override
	@Deprecated
	public VoxelShape getOcclusionShape(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return shapes.get(state.getValue(HORIZONTAL_AXIS));
	}

	//TODO Optimize getRaytraceShape
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		AxisConnectionType type = AxisConnectionType.getConnections(world, pos, state);
		switch(type){
			case DEFAULT:
				return shapes.get(state.getValue(HORIZONTAL_AXIS));
			case POSITIVE:
				return positiveShapes.get(state.getValue(HORIZONTAL_AXIS));
			case NEGATIVE:
				return negativeShapes.get(state.getValue(HORIZONTAL_AXIS));
			case CENTERED:
				return centeredShapes.get(state.getValue(HORIZONTAL_AXIS));
		}

		return shapes.get(state.getValue(HORIZONTAL_AXIS));
	}
}