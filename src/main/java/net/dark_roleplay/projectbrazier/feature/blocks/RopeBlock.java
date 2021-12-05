package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

public class RopeBlock extends HFacedDecoBlock {

	protected final HFacedVoxelShape droppedShapes;

	public static final BooleanProperty IS_END = BooleanProperty.create("end");

	public RopeBlock(Properties props, String shapeName, String droppedShape) {
		super(props, shapeName);
		this.droppedShapes = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape(droppedShape));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(IS_END) ? droppedShapes.get(state.getValue(HORIZONTAL_FACING)) : shapes.get(state.getValue(HORIZONTAL_FACING));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(IS_END);
	}

	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if(facing == Direction.UP && facingState.isAir()){
			if(world.getBlockState(facingPos.relative(state.getValue(HORIZONTAL_FACING).getOpposite())).getBlock() != BrazierBlocks.ROPE_ANCHOR.get())
				return Blocks.AIR.defaultBlockState();
		}

		return state;
	}
}
