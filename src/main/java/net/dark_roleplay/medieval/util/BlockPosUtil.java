package net.dark_roleplay.medieval.util;

import net.minecraft.util.math.BlockPos;

public class BlockPosUtil {

    public static BlockPos getMin(BlockPos a, BlockPos b){
        return new BlockPos(Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()));
    }


    public static BlockPos getMax(BlockPos a, BlockPos b){
        return new BlockPos(Math.max(a.getX(), b.getX()), Math.max(a.getY(), b.getY()), Math.max(a.getZ(), b.getZ()));
    }
}
