package net.dark_roleplay.medieval.objects.blocks.building.platforms;

import java.util.EnumMap;

import net.dark_roleplay.medieval.objects.blocks.templates.AxisBlock;
import net.dark_roleplay.medieval.objects.enums.PlatformHeight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class WoodPlatformBlock extends AxisBlock{

	protected final EnumMap<Axis, VoxelShape> shapesFull = new EnumMap<Axis, VoxelShape>(Axis.class);
	protected final EnumMap<Axis, VoxelShape> shapesHalf = new EnumMap<Axis, VoxelShape>(Axis.class);

	public static final EnumProperty<PlatformHeight> PLATFORM_HEIGHT = EnumProperty.create("height", PlatformHeight.class);
	
	public WoodPlatformBlock(Properties properties) {
		super(properties);
		
		setShapes(
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 10, 6.5, 16, 13, 9.5), Block.makeCuboidShape(0, 13, 0, 16, 16, 16), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6.5, 10, 0, 9.5, 13, 16),Block.makeCuboidShape(0, 13, 0, 16, 16, 16), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 2, 6.5, 16, 5, 9.5), Block.makeCuboidShape(0, 5, 0, 16, 8, 16), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6.5, 2, 0, 9.5, 5, 16), Block.makeCuboidShape(0, 5, 0, 16, 8, 16), IBooleanFunction.OR)
		);
	}
	
	protected void setShapes(VoxelShape fullX, VoxelShape fullZ, VoxelShape halfX, VoxelShape halfZ) {
		this.shapesFull.put(Axis.X, fullX);
		this.shapesFull.put(Axis.Z, fullZ);
		this.shapesHalf.put(Axis.X, halfX);
		this.shapesHalf.put(Axis.Z, halfZ);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(PLATFORM_HEIGHT);
	}
	
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return state.get(PLATFORM_HEIGHT) == PlatformHeight.FULL ? shapesFull.get(state.get(HORIZONTAL_AXIS)) : shapesHalf.get(state.get(HORIZONTAL_AXIS));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState()
				.with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().rotateY().getAxis())
				.with(PLATFORM_HEIGHT, context.getFace() == Direction.UP ? PlatformHeight.HALF : context.getHitVec().y >= 0.5 ? PlatformHeight.FULL : PlatformHeight.HALF);
	}
}