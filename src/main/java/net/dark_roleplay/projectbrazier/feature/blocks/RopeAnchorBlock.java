package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class RopeAnchorBlock extends HFacedDecoBlock implements ITertiaryInteractor {

	protected final HFacedVoxelShape droppedShapes;

	public static final BooleanProperty IS_DROPPED = BooleanProperty.create("dropped");

	public RopeAnchorBlock(Properties props, String shapeName, String droppedShape) {
		super(props, shapeName);
		this.registerDefaultState(this.defaultBlockState().setValue(IS_DROPPED, false));
		this.droppedShapes = new HFacedVoxelShape(VoxelShapeLoader.getVoxelShape(droppedShape));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(IS_DROPPED) ? droppedShapes.get(state.getValue(HORIZONTAL_FACING)) : shapes.get(state.getValue(HORIZONTAL_FACING));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(IS_DROPPED);
	}

	@Override
	public boolean hasInteraction(BlockGetter world, BlockPos pos, BlockState state, Player palyer) {
		return true;
	}

	@Override
	public void executeInteraction(Level world, BlockPos pos, BlockState state, Player palyer) {
		boolean isDropped = state.getValue(IS_DROPPED);
		Direction dir = state.getValue(HORIZONTAL_FACING);

		List<BlockPos> ropePositions = new ArrayList<BlockPos>();

		BlockPos.Mutable iterPos = pos.mutable();
		iterPos.move(dir).move(Direction.DOWN);

		for(int i = 0; i < 32; i++){
			BlockState targetState = world.getBlockState(iterPos);
			if(!isDropped && targetState.isAir(world, iterPos))
				ropePositions.add(iterPos.immutable());
			else if(isDropped && targetState.getBlock() == BrazierBlocks.ROPE.get() && targetState.getValue(HORIZONTAL_FACING) == dir) {
				ropePositions.add(iterPos.immutable());
				if(targetState.getValue(RopeBlock.IS_END)) break;
			}else{
				break;
			}
			iterPos.move(0, -1, 0);
			if(iterPos.getY() < 0) break;
		}

		iterPos.setWithOffset(pos,dir).move(Direction.DOWN, ropePositions.size());
		for(int i = 0; i < ropePositions.size(); i++){
			if(!isDropped){
				world.setBlockAndUpdate(iterPos, BrazierBlocks.ROPE.get().defaultBlockState().setValue(HORIZONTAL_FACING, dir).setValue(RopeBlock.IS_END, i == 0));
			}else{
				world.setBlockAndUpdate(iterPos, Blocks.AIR.defaultBlockState());
			}
			iterPos.move(Direction.UP);
		}

		world.setBlockAndUpdate(pos, state.setValue(IS_DROPPED, !isDropped));
	}

	@Override
	public TextComponent getInteractionTooltip(Level world, BlockPos pos, BlockState state, Player player) {
		return state.getValue(IS_DROPPED) ?
				new TranslatableComponent("interaction.projectbrazier.rope_anchor.pull_up") :
				new TranslatableComponent("interaction.projectbrazier.rope_anchor.drop");
	}

	@Override
	public int getDurationInMS(Level world, BlockPos pos, BlockState state) {
		return state.getValue(IS_DROPPED) ? 750 : 500;
	}
}
