package net.dark_roleplay.medieval.objects.blocks.building.platforms;

import java.util.EnumMap;
import java.util.stream.Stream;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.dark_roleplay.medieval.objects.enums.PlatformHeight;
import net.dark_roleplay.medieval.util.blocks.VoxelShapeHelper;
import net.dark_roleplay.medieval.util.blocks.VoxelShapeHelper.RotationAmount;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class WoodPlatformStairsBlock extends HorizontalBlock{

	protected final EnumMap<Direction, VoxelShape> shapesFull = new EnumMap<Direction, VoxelShape>(Direction.class);
	protected final EnumMap<Direction, VoxelShape> shapesHalf = new EnumMap<Direction, VoxelShape>(Direction.class);

	public static final EnumProperty<PlatformHeight> PLATFORM_HEIGHT = EnumProperty.create("height", PlatformHeight.class);
	
	public WoodPlatformStairsBlock(Properties properties) {
		super(properties);
		
		setShapes(
			Stream.of(
				Block.makeCuboidShape(0, 2, 2.5, 16, 5, 5.5),
				Block.makeCuboidShape(0, 5, 0, 16, 8, 8),
				Block.makeCuboidShape(0, 10, 10.5, 16, 13, 13.5),
				Block.makeCuboidShape(0, 13, 8, 16, 16, 16)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 5, 8, 16, 8, 16), Block.makeCuboidShape(0, 2, 10.5, 16, 5, 13.5), IBooleanFunction.OR)
		);
	}
	
	protected void setShapes(VoxelShape full, VoxelShape half) {
		this.shapesFull.put(Direction.NORTH, full);
		this.shapesFull.put(Direction.EAST, VoxelShapeHelper.rotateShape(full, RotationAmount.NINETY));
		this.shapesFull.put(Direction.SOUTH, VoxelShapeHelper.rotateShape(full, RotationAmount.HUNDRED_EIGHTY));
		this.shapesFull.put(Direction.WEST, VoxelShapeHelper.rotateShape(full, RotationAmount.TWO_HUNDRED_SEVENTY));
		this.shapesHalf.put(Direction.NORTH, half);
		this.shapesHalf.put(Direction.EAST, VoxelShapeHelper.rotateShape(half, RotationAmount.NINETY));
		this.shapesHalf.put(Direction.SOUTH, VoxelShapeHelper.rotateShape(half, RotationAmount.HUNDRED_EIGHTY));
		this.shapesHalf.put(Direction.WEST, VoxelShapeHelper.rotateShape(half, RotationAmount.TWO_HUNDRED_SEVENTY));
	}
	

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(PLATFORM_HEIGHT);
	}
	
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return state.get(PLATFORM_HEIGHT) == PlatformHeight.FULL ? shapesFull.get(state.get(HORIZONTAL_FACING)) : shapesHalf.get(state.get(HORIZONTAL_FACING));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState()
				.with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().rotateY())
				.with(PLATFORM_HEIGHT, context.getFace() == Direction.UP ? PlatformHeight.HALF : context.getHitVec().y >= 0.5 ? PlatformHeight.FULL : PlatformHeight.HALF);
	}
}
