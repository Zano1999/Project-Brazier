package net.dark_roleplay.projectbrazier.mixin_helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;

public interface ICustomOffset {

	Vec3 getOffset(BlockState state, BlockGetter access, BlockPos pos);
}
