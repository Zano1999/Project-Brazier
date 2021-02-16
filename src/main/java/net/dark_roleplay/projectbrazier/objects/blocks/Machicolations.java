package net.dark_roleplay.projectbrazier.objects.blocks;

import net.dark_roleplay.projectbrazier.objects.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Machicolations extends HFacedDecoBlock {

	public static final EnumProperty<MachicolationType> TYPE = EnumProperty.create("type", MachicolationType.class);

	protected final HFacedVoxelShape innerShapes;
	protected final HFacedVoxelShape outerShapes;


	public Machicolations(Properties props, String shapeName) {
		super(props, "straight_" + shapeName);
		innerShapes = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape("inner_" + shapeName));
		outerShapes = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape("outer_" + shapeName));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		MachicolationType type = state.get(TYPE);
		return type == MachicolationType.STRAIGHT ?
				shapes.get(state.get(HORIZONTAL_FACING)) :
					type == MachicolationType.OUTER_CORNER ?
							outerShapes.get(state.get(HORIZONTAL_FACING)) :
							innerShapes.get(state.get(HORIZONTAL_FACING));
	}


	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(TYPE);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getPlacementHorizontalFacing().getOpposite();
		BlockPos pos = context.getPos();
		World world = context.getWorld();

		BlockState sourceState = null;
		if(!context.getPlayer().isCrouching() && (sourceState = world.getBlockState(pos.down())).getBlock() instanceof Machicolations){
			return this.getDefaultState().with(HORIZONTAL_FACING, sourceState.get(HORIZONTAL_FACING)).with(TYPE, sourceState.get(TYPE));
		}

		MachicolationType type = MachicolationType.STRAIGHT;
		if(!context.getPlayer().isCrouching()) {
			BlockState other;
			if ((other = world.getBlockState(pos.offset(direction))).getBlock() == this.getBlock()) {
				if (other.get(HORIZONTAL_FACING) == direction.rotateY()) {
					type = MachicolationType.INNER_CORNER;
				} else if (other.get(HORIZONTAL_FACING) == direction.rotateYCCW()) {
					type = MachicolationType.INNER_CORNER;
					direction = direction.rotateYCCW();
				}
			} else if ((other = world.getBlockState(pos.offset(direction.getOpposite()))).getBlock() == this.getBlock()) {
				if (other.get(HORIZONTAL_FACING) == direction.rotateYCCW()) {
					type = MachicolationType.OUTER_CORNER;
					direction = direction.rotateYCCW();
				} else if (other.get(HORIZONTAL_FACING) == direction.rotateY()) {
					type = MachicolationType.OUTER_CORNER;
				}
			}
		}

		return this.getDefaultState().with(HORIZONTAL_FACING, direction).with(TYPE, type);
	}

	public enum MachicolationType implements IStringSerializable {
		STRAIGHT("straight"),
		INNER_CORNER("inner_corner"),
		OUTER_CORNER("outer_corner");

		private final String name;

		MachicolationType(String name){
			this.name = name;
		}

		@Override
		public String getString() {
			return this.name;
		}
	}
}
