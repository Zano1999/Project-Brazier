package net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.BlockGetter;

public interface ITertiaryInteractor {
	boolean hasInteraction(BlockGetter world, BlockPos pos, BlockState state, Player palyer);

	void executeInteraction(Level world, BlockPos pos, BlockState state, Player palyer);

	Component getInteractionTooltip(Level world, BlockPos pos, BlockState state, Player player);

	int getDurationInMS(Level world, BlockPos pos, BlockState state);
}
