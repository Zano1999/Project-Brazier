package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RopeAnchorBlock extends HFacedDecoBlock implements ITertiaryInteractor {

	protected final HFacedVoxelShape droppedShapes;

	public static final BooleanProperty IS_DROPPED = BooleanProperty.create("dropped");

	public RopeAnchorBlock(Properties props, String shapeName, String droppedShape) {
		super(props, shapeName);
		this.setDefaultState(this.getDefaultState().with(IS_DROPPED, false));
		this.droppedShapes = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape(droppedShape));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(IS_DROPPED) ? droppedShapes.get(state.get(HORIZONTAL_FACING)) : shapes.get(state.get(HORIZONTAL_FACING));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(IS_DROPPED);
	}

	@Override
	public boolean hasInteraction(IBlockReader world, BlockPos pos, BlockState state, PlayerEntity palyer) {
		return true;
	}

	@Override
	public void executeInteraction(World world, BlockPos pos, BlockState state, PlayerEntity palyer) {
		boolean isDropped = state.get(IS_DROPPED);
		Direction dir = state.get(HORIZONTAL_FACING);

		List<BlockPos> ropePositions = new ArrayList<BlockPos>();

		BlockPos.Mutable iterPos = pos.toMutable();
		iterPos.move(dir).move(Direction.DOWN);

		for(int i = 0; i < 32; i++){
			BlockState targetState = world.getBlockState(iterPos);
			if(!isDropped && targetState.isAir(world, iterPos))
				ropePositions.add(iterPos.toImmutable());
			else if(isDropped && targetState.getBlock() == BrazierBlocks.ROPE.get() && targetState.get(HORIZONTAL_FACING) == dir) {
				ropePositions.add(iterPos.toImmutable());
				if(targetState.get(RopeBlock.IS_END)) break;
			}else{
				break;
			}
			iterPos.move(0, -1, 0);
			if(iterPos.getY() < 0) break;
		}

		iterPos.setAndMove(pos,dir).move(Direction.DOWN, ropePositions.size());
		for(int i = 0; i < ropePositions.size(); i++){
			if(!isDropped){
				world.setBlockState(iterPos, BrazierBlocks.ROPE.get().getDefaultState().with(HORIZONTAL_FACING, dir).with(RopeBlock.IS_END, i == 0));
			}else{
				world.setBlockState(iterPos, Blocks.AIR.getDefaultState());
			}
			iterPos.move(Direction.UP);
		}

		world.setBlockState(pos, state.with(IS_DROPPED, !isDropped));
	}

	@Override
	public ITextComponent getInteractionTooltip(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		return state.get(IS_DROPPED) ?
				new TranslationTextComponent("interaction.projectbrazier.rope_anchor.pull_up") :
				new TranslationTextComponent("interaction.projectbrazier.rope_anchor.drop");
	}

	@Override
	public int getDurationInMS(World world, BlockPos pos, BlockState state) {
		return state.get(IS_DROPPED) ? 750 : 500;
	}
}
