package net.dark_roleplay.projectbrazier.experimental_features.rope_maker;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RopeMakerBlock extends HFacedDecoBlock implements ITertiaryInteractor, EntityBlock {

	public RopeMakerBlock(Properties props, String shapeName) {
		super(props, shapeName);
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
		//TODO Properly implement
		return new TextComponent("Lorem Ipsum");
	}

	@Override
	public int getDurationInMS(Level world, BlockPos pos, BlockState state) {
		return 1;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new RopeMakerBlockEntity(pos, state);
	}
}
