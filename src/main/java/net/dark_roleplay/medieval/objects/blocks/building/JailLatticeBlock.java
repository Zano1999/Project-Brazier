package net.dark_roleplay.medieval.objects.blocks.building;

import net.dark_roleplay.medieval.objects.blocks.templates.AxisBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;

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
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(TOP, BOTTOM, LEFT, RIGHT);
	}
}
