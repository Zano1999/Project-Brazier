package net.dark_roleplay.projectbrazier.objects.blocks;

import net.dark_roleplay.projectbrazier.objects.blocks.templates.HAxisDecoBlock;
import net.dark_roleplay.projectbrazier.objects.model_loaders.axis_connected_models.AxisConnectionType;
import net.dark_roleplay.projectbrazier.util.blocks.AxisVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.dark_roleplay.projectbrazier.util.sitting.SittingUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BenchBlock extends HAxisDecoBlock {

	protected final AxisVoxelShape positiveShapes;
	protected final AxisVoxelShape negativeShapes;
	protected final AxisVoxelShape centeredShapes;

	public BenchBlock(Properties properties, String shapeName, String positiveShapeName, String negativeShapeName, String centeredShapeName) {
		super(properties, shapeName);
		this.positiveShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(positiveShapeName));
		this.negativeShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(negativeShapeName));
		this.centeredShapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(centeredShapeName));
	}


	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if(player.getPositionVec().squareDistanceTo(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) < 4) {
			if(!world.isRemote()){
				Direction.Axis axis = state.get(HORIZONTAL_AXIS);

				Direction facing = null;

				float yaw = player.getRotationYawHead();

				if(axis == Direction.Axis.Z && yaw > 0 && yaw< 180) facing = Direction.WEST;
				else if(axis == Direction.Axis.Z) facing = Direction.EAST;
				else if(axis == Direction.Axis.X && yaw > -90 && yaw < 90) facing = Direction.SOUTH;
				else if(axis == Direction.Axis.X) facing = Direction.NORTH;

				SittingUtil.sitOnBlock((ServerWorld) world, pos.getX(), pos.getY(), pos.getZ(), player, facing, 0.32F);
			}
		}else {
			player.sendStatusMessage(new TranslationTextComponent("interaction.drpmedieval.chair.to_far", state.getBlock().getTranslatedName()), true);
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	@Deprecated
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return shapes.get(state.get(HORIZONTAL_AXIS));
	}

	//TODO Optimize getRaytraceShape
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		AxisConnectionType type = AxisConnectionType.getConnections(world, pos, state);
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