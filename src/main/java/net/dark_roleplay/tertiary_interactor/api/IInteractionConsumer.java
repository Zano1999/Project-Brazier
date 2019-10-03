package net.dark_roleplay.tertiary_interactor.api;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * The tick method will be called each tick while a Player has an active TertiaryInteraction
 */
public interface IInteractionConsumer {
    void apply(World world, BlockPos pos, BlockState state, PlayerEntity player);
}
