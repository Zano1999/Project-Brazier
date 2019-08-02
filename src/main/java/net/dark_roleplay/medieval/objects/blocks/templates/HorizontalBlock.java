package net.dark_roleplay.medieval.objects.blocks.templates;

import java.util.EnumMap;

import javax.annotation.Nullable;

import net.dark_roleplay.medieval.util.blocks.VoxelShapeHelper;
import net.dark_roleplay.medieval.util.blocks.VoxelShapeHelper.RotationAmount;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public abstract class HorizontalBlock extends Block {

	protected static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	protected final EnumMap<Direction, VoxelShape> shapes = new EnumMap<Direction, VoxelShape>(Direction.class);

	public HorizontalBlock(Properties properties) {
		super(properties);
	}

	protected void setShapes(VoxelShape north) {
		this.shapes.put(Direction.NORTH, north);
		this.shapes.put(Direction.EAST, VoxelShapeHelper.rotateShape(north, RotationAmount.NINETY));
		this.shapes.put(Direction.SOUTH, VoxelShapeHelper.rotateShape(north, RotationAmount.HUNDRED_EIGHTY));
		this.shapes.put(Direction.WEST, VoxelShapeHelper.rotateShape(north, RotationAmount.TWO_HUNDRED_SEVENTY));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return shapes.get(state.get(HORIZONTAL_FACING));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING);
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if (!Block.func_220064_c(context.getWorld(), context.getPos().down()))
			return Blocks.AIR.getDefaultState();

		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
}
