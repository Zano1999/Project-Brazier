package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		MachicolationType type = state.getValue(TYPE);
		return type == MachicolationType.STRAIGHT ?
				shapes.get(state.getValue(HORIZONTAL_FACING)) :
					type == MachicolationType.OUTER_CORNER ?
							outerShapes.get(state.getValue(HORIZONTAL_FACING)) :
							innerShapes.get(state.getValue(HORIZONTAL_FACING));
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(TYPE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getHorizontalDirection().getOpposite();
		BlockPos pos = context.getClickedPos();
		Level world = context.getLevel();

		BlockState sourceState = null;
		if(!context.getPlayer().isCrouching() && (sourceState = world.getBlockState(pos.below())).getBlock() instanceof MachicolationBlock){
			return this.defaultBlockState().setValue(HORIZONTAL_FACING, sourceState.getValue(HORIZONTAL_FACING)).setValue(TYPE, sourceState.getValue(TYPE));
		}

		MachicolationType type = MachicolationType.STRAIGHT;
		if(!context.getPlayer().isCrouching()) {
			BlockState other;
			if ((other = world.getBlockState(pos.relative(direction))).getBlock() == this) {
				if (other.getValue(HORIZONTAL_FACING) == direction.getClockWise()) {
					type = MachicolationType.INNER_CORNER;
				} else if (other.getValue(HORIZONTAL_FACING) == direction.getCounterClockWise()) {
					type = MachicolationType.INNER_CORNER;
					direction = direction.getCounterClockWise();
				}
			} else if ((other = world.getBlockState(pos.relative(direction.getOpposite()))).getBlock() == this) {
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
