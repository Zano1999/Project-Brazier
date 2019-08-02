package net.dark_roleplay.medieval.objects.blocks.building.jail_lattice;

import javax.annotation.Nullable;

import net.dark_roleplay.medieval.objects.blocks.templates.AxisBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class JailLatticeBlock extends AxisBlock{

	public static final BooleanProperty TOP = BooleanProperty.create("top");
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");
	
	public JailLatticeBlock(Properties properties) {
		super(properties);
		setShapes(
			Block.makeCuboidShape(0, 0, 7, 16, 16, 9),
			Block.makeCuboidShape(7, 0, 0, 9, 16, 16)
		);
		this.setDefaultState(this.getDefaultState().with(TOP, false).with(BOTTOM, false).with(LEFT, false).with(RIGHT, false));
	}
	

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(TOP, BOTTOM, LEFT, RIGHT);
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().rotateY().getAxis());
	}
}
