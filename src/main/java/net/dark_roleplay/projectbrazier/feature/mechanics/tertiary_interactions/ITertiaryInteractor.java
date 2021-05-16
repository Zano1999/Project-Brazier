package net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public interface ITertiaryInteractor {
	boolean hasInteraction(IBlockReader world, BlockPos pos, BlockState state, PlayerEntity palyer);

	void executeInteraction(World world, BlockPos pos, BlockState state, PlayerEntity palyer);

	ITextComponent getInteractionTooltip(World world, BlockPos pos, BlockState state, PlayerEntity player);

	int getDurationInMS(World world, BlockPos pos, BlockState state);
}
