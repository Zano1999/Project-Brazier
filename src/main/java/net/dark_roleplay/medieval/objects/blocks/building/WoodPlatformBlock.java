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
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6.5, 11, 0, 9.5, 14, 16),Block.makeCuboidShape(0, 14, 0, 16, 16, 16), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 11, 6.5, 16, 14, 9.5), Block.makeCuboidShape(0, 14, 0, 16, 16, 16), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6.5, 3, 0, 9.5, 6, 16), Block.makeCuboidShape(0, 6, 0, 16, 8, 16), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 3, 6.5, 16, 6, 9.5), Block.makeCuboidShape(0, 6, 0, 16, 8, 16), IBooleanFunction.OR)
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