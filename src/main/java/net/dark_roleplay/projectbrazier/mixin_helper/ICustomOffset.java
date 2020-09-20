package net.dark_roleplay.projectbrazier.mixin_helper;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public interface ICustomOffset {

	Vector3d getOffset(BlockState state, IBlockReader access, BlockPos pos);
}
