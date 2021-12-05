package net.dark_roleplay.projectbrazier.feature.blocks.templates;

import net.dark_roleplay.projectbrazier.util.blocks.RotatedPillarVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

public class AxisDecoBlock extends Block {

	protected static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	protected final RotatedPillarVoxelShape shapes;

	public AxisDecoBlock(Properties properties, String shapeName) {
		super(properties);
		this.shapes = new RotatedPillarVoxelShape(VoxelShapeLoader.getVoxelShape(shapeName));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return shapes.get(state.getValue(AXIS));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction.Axis currentAxis = state.getValue(AXIS);
		Direction newDir = rot.rotate(currentAxis == Direction.Axis.X ? Direction.EAST : Direction.NORTH);
		return state.setValue(AXIS, newDir.getAxis());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}
}
