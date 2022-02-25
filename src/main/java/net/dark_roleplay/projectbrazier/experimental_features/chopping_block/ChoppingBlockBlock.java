package net.dark_roleplay.projectbrazier.experimental_features.chopping_block;

import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ChoppingBlockBlock extends Block implements ITertiaryInteractor, EntityBlock {

	public ChoppingBlockBlock(Properties props) {
		super(props);
	}


	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if(!(level.getBlockEntity(pos) instanceof ChoppingBlockBlockEntity be)) return InteractionResult.FAIL;
		if(!be.isEmpty()) return InteractionResult.PASS;
		if(player.getItemInHand(hand).isEmpty()) return InteractionResult.PASS;

		//TODO Check if logs
		be.addHeldItem(player.getItemInHand(hand));
		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	@Override
	public boolean hasInteraction(BlockGetter world, BlockPos pos, BlockState state, Player player) {
		return false;
	}

	@Override
	public void executeInteraction(Level world, BlockPos pos, BlockState state, Player player) {

	}

	@Override
	public Component getInteractionTooltip(Level world, BlockPos pos, BlockState state, Player player) {
		return null;
	}

	@Override
	public int getDurationInMS(Level world, BlockPos pos, BlockState state) {
		return 0;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ChoppingBlockBlockEntity(pos, state);
	}
}
