package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.util.blocks.AxisVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AxisLatticeBlock extends Block {

	protected static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	protected Item item;
	protected Block otherBlock;

	protected final AxisVoxelShape shapes;

	public AxisLatticeBlock(Properties properties, String shapeName) {
		super(properties);
		this.shapes = new AxisVoxelShape(VoxelShapeLoader.getVoxelShape(shapeName), true);
	}

	public void initOtherBlock(Block otherBlock) {
		this.otherBlock = otherBlock;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return shapes.get(state.getValue(AXIS));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(AXIS, context.getHorizontalDirection().getClockWise().getAxis());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction.Axis currentAxis = state.getValue(AXIS);
		if (currentAxis != Direction.Axis.Y) ;
		Direction newDir = rot.rotate(currentAxis == Direction.Axis.X ? Direction.EAST : Direction.NORTH);
		return state.setValue(AXIS, newDir.getAxis());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	public Item asItem() {
		if (this.item == null) {
			this.item = Item.byBlock(this.otherBlock);
		}

		return this.item.delegate.get(); //Forge: Vanilla caches the items, update with registry replacements.
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		return false;
	}
}