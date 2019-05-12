package net.dark_roleplay.medieval.objects.blocks.building;

import java.util.EnumMap;

import net.dark_roleplay.medieval.objects.blocks.templates.AxisBlock;
import net.dark_roleplay.medieval.objects.enums.PlatformHeight;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
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
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(PLATFORM_HEIGHT);
	}
	
	
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return state.get(PLATFORM_HEIGHT) == PlatformHeight.FULL ? shapesFull.get(state.get(HORIZONTAL_AXIS)) : shapesHalf.get(state.get(HORIZONTAL_AXIS));
	}
	
	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState()
				.with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().rotateY().getAxis())
				.with(PLATFORM_HEIGHT, context.getFace() == EnumFacing.UP ? PlatformHeight.HALF : context.getHitY() >= 0.5 ? PlatformHeight.FULL : PlatformHeight.HALF);
	}
}