package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.dark_roleplay.projectbrazier.util.blocks.HFacedVoxelShape;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RopeAnchorBlock extends HFacedDecoBlock implements ITertiaryInteractor {
	private static int maxLength = 36;
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
	public void executeInteraction(Level world, BlockPos pos, BlockState state, Player player) {
		boolean isDropped = state.getValue(IS_DROPPED);
		if(isDropped){
			pullUpRope(world, pos, state, player);
		}else{
			if(!dropDownRope(world, pos, state, player.getY() + 1 < pos.getY() ? player.getDirection().getOpposite() : player.getDirection()))
				dropDownRope(world, pos, state, player.getY() + 1 < pos.getY() ? player.getDirection() : player.getDirection().getOpposite());
		}
	}

	@Override
	public Component getInteractionTooltip(Level world, BlockPos pos, BlockState state, Player player) {
		return state.getValue(IS_DROPPED) ?
				new TranslatableComponent("interaction.projectbrazier.rope_anchor.pull_up") :
				new TranslatableComponent("interaction.projectbrazier.rope_anchor.drop");
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block otherBlock, BlockPos otherPos, boolean p_55671_) {
		if (!world.isClientSide)
			if (world.hasNeighborSignal(pos) && !state.getValue(IS_DROPPED))
				dropDownRope(world, pos, state, state.getValue(HORIZONTAL_FACING));
	}

	private boolean pullUpRope(Level world, BlockPos pos, BlockState state, Player player){
		boolean isDropped = state.getValue(IS_DROPPED);

		if(!isDropped) return false;
		Direction dir = state.getValue(HORIZONTAL_FACING);

		BlockPos.MutableBlockPos iterPos = pos.mutable();
		iterPos.move(dir).move(Direction.DOWN);

		BlockState targetState = world.getBlockState(iterPos);
		while(targetState.getBlock() == BrazierBlocks.ROPE.get() && targetState.getValue(HORIZONTAL_FACING) == dir){
			iterPos.move(0, -1, 0);
			targetState = world.getBlockState(iterPos);
		}
		iterPos.move(0, 1,0);

		if(world.getEntities(player, new AABB(pos, iterPos).expandTowards(0.5, 0.5, 0.5), otherMob -> otherMob instanceof LivingEntity).size() > 0) {
			if(player != null) player.displayClientMessage(new TranslatableComponent("interaction.projectbrazier.rope_anchor.pull_up_fail").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
			return false;
		}

		while(iterPos.getY() < pos.getY()) {
			world.setBlockAndUpdate(iterPos, Blocks.AIR.defaultBlockState());
			iterPos.move(0, 1, 0);
		}

		world.setBlockAndUpdate(pos, state.setValue(IS_DROPPED, false));
		return true;
	}

	private boolean dropDownRope(Level world, BlockPos pos, BlockState state, Direction dir){
		boolean isDropped = state.getValue(IS_DROPPED);

		if(isDropped) return false;

		BlockState ropeState = BrazierBlocks.ROPE.get().defaultBlockState().setValue(HORIZONTAL_FACING, dir).setValue(RopeBlock.IS_END, false);

		BlockPos.MutableBlockPos iterPos = pos.mutable();
		iterPos.move(dir).move(Direction.DOWN);

		for(int i = 0; i < maxLength; i++){
			BlockState targetState = world.getBlockState(iterPos);
			if(targetState.isAir())
				world.setBlockAndUpdate(iterPos, ropeState);
			else{
				if(i == 0)
					return false;
				break;
			}

			iterPos.move(0, -1, 0);
			if(iterPos.getY() < world.getMinBuildHeight()) break;
		}
		iterPos.move(0, 1, 0);
		world.setBlockAndUpdate(iterPos, ropeState.setValue(RopeBlock.IS_END, true));

		world.setBlockAndUpdate(pos, state.setValue(IS_DROPPED, true).setValue(HORIZONTAL_FACING, dir));
		return true;
	}

	@Override
	public int getDurationInMS(Level world, BlockPos pos, BlockState state) {
		return state.getValue(IS_DROPPED) ? 750 : 500;
	}
}
