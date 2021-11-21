package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class MachicolationBlock extends HFacedDecoBlock {

	public static final EnumProperty<MachicolationType> TYPE = EnumProperty.create("type", MachicolationType.class);

	protected final HFacedVoxelShape innerShapes;
	protected final HFacedVoxelShape outerShapes;


	public MachicolationBlock(Properties props, String shapeName) {
		super(props, "straight_" + shapeName);
		innerShapes = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape("inner_" + shapeName));
		outerShapes = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape("outer_" + shapeName));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		MachicolationType type = state.getValue(TYPE);
		return type == MachicolationType.STRAIGHT ?
				shapes.get(state.getValue(HORIZONTAL_FACING)) :
					type == MachicolationType.OUTER_CORNER ?
							outerShapes.get(state.getValue(HORIZONTAL_FACING)) :
							innerShapes.get(state.getValue(HORIZONTAL_FACING));
	}


	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(TYPE);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getHorizontalDirection().getOpposite();
		BlockPos pos = context.getClickedPos();
		World world = context.getLevel();

		BlockState sourceState = null;
		if(!context.getPlayer().isCrouching() && (sourceState = world.getBlockState(pos.below())).getBlock() instanceof MachicolationBlock){
			return this.defaultBlockState().setValue(HORIZONTAL_FACING, sourceState.getValue(HORIZONTAL_FACING)).setValue(TYPE, sourceState.getValue(TYPE));
		}

		MachicolationType type = MachicolationType.STRAIGHT;
		if(!context.getPlayer().isCrouching()) {
			BlockState other;
			if ((other = world.getBlockState(pos.relative(direction))).getBlock() == this.getBlock()) {
				if (other.getValue(HORIZONTAL_FACING) == direction.getClockWise()) {
					type = MachicolationType.INNER_CORNER;
				} else if (other.getValue(HORIZONTAL_FACING) == direction.getCounterClockWise()) {
					type = MachicolationType.INNER_CORNER;
					direction = direction.getCounterClockWise();
				}
			} else if ((other = world.getBlockState(pos.relative(direction.getOpposite()))).getBlock() == this.getBlock()) {
				if (other.getValue(HORIZONTAL_FACING) == direction.getCounterClockWise()) {
					type = MachicolationType.OUTER_CORNER;
					direction = direction.getCounterClockWise();
				} else if (other.getValue(HORIZONTAL_FACING) == direction.getClockWise()) {
					type = MachicolationType.OUTER_CORNER;
				}
			}
		}

		return this.defaultBlockState().setValue(HORIZONTAL_FACING, direction).setValue(TYPE, type);
	}

}
