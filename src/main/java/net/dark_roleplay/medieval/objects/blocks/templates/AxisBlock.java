package net.dark_roleplay.medieval.objects.blocks.templates;

import java.util.EnumMap;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class AxisBlock extends Block {

	protected static final EnumProperty<Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	protected final EnumMap<Axis, VoxelShape> shapes = new EnumMap<Axis, VoxelShape>(Axis.class);

	public AxisBlock(Properties properties) {
		super(properties);
	}

	protected void setShapes(VoxelShape x, VoxelShape y) {
		this.shapes.put(Axis.X, x);
		this.shapes.put(Axis.Z, y);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return shapes.get(state.get(HORIZONTAL_AXIS));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		//TODO IMPLEMENT LATER
//		return state.with(HORIZONTAL_AXIS, rot =));
		return state;
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		//TODO IMPLEMENT LATER
//		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
		return state;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_AXIS);
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if (!Block.hasSolidSideOnTop(context.getWorld(), context.getPos().down()))
			return Blocks.AIR.getDefaultState();

		return this.getDefaultState().with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().rotateY().getAxis());
	}
}
