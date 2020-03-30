package net.dark_roleplay.medieval.objects.blocks.building.timbered_clay;

import net.dark_roleplay.medieval.objects.blocks.templates.AxisBlock;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberedClayState;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayEdgeVariant;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TimberedClay extends AxisBlock{

	public static final BooleanProperty TOP = BooleanProperty.create("top");
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");

	private TimberedClayVariant variant;

	public TimberedClay(Properties properties, TimberedClayVariant variant) {
		super(properties);
		this.variant = variant;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return VoxelShapes.fullCube();
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

	public TimberedClayState toTmberedClayState(BlockState state){
		int flag = 0;
		if(state.get(BOTTOM)) flag |= 0x1;
		if(state.get(LEFT)) flag |= 0x2;
		if(state.get(TOP)) flag |= 0x4;
		if(state.get(RIGHT)) flag |= 0x8;

		return TimberedClayState.of(this.variant, TimberedClayEdgeVariant.edges[flag]);
	}
}
