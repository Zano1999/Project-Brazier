package net.dark_roleplay.tertiary_interactor.api;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IInteractorPredicate {
    boolean test(World world, BlockPos pos, BlockState state, PlayerEntity player);
}
