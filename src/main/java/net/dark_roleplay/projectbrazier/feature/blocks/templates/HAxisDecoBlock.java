package net.dark_roleplay.projectbrazier.feature.blocks.templates;

import net.dark_roleplay.projectbrazier.util.blocks.AxisVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HAxisDecoBlock extends Block {

	protected static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	protected final AxisVoxelShape shapes;

	public HAxisDecoBlock(Properties properties, String shapeName) {
		super(properties);
		this.shapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(shapeName));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getClockWise().getAxis());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return shapes.get(state.getValue(HORIZONTAL_AXIS));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction.Axis currentAxis = state.getValue(HORIZONTAL_AXIS);
		Direction newDir = rot.rotate(currentAxis == Direction.Axis.X ? Direction.EAST : Direction.NORTH);
		return state.setValue(HORIZONTAL_AXIS, newDir.getAxis());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_AXIS);
	}
}