package net.dark_roleplay.projectbrazier.feature.blocks.templates;

import net.dark_roleplay.projectbrazier.util.blocks.AxisVoxelShape;
import net.dark_roleplay.projectbrazier.util.blocks.RotatedPillarVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class AxisDecoBlock extends Block {

	protected static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	protected final RotatedPillarVoxelShape shapes;

	public AxisDecoBlock(Properties properties, String shapeName) {
		super(properties);
		this.shapes = new RotatedPillarVoxelShape(VoxelShapeLoader.getVoxelShape(shapeName));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(AXIS, context.getFace().getAxis());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shapes.get(state.get(AXIS));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction.Axis currentAxis = state.get(AXIS);
		Direction newDir = rot.rotate(currentAxis == Direction.Axis.X ? Direction.EAST : Direction.NORTH);
		return state.with(AXIS, newDir.getAxis());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}
}
