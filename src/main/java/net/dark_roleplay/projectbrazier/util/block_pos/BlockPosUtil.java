package net.dark_roleplay.projectbrazier.util.block_pos;

import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

public class BlockPosUtil {

    public static BlockPos getMin(BlockPos a, BlockPos b){
        return new BlockPos(Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()));
    }


    public static BlockPos getMax(BlockPos a, BlockPos b){
        return new BlockPos(Math.max(a.getX(), b.getX()), Math.max(a.getY(), b.getY()), Math.max(a.getZ(), b.getZ()));
    }

    public static void walkRegion(BlockPos startIn, BlockPos targetIn, BiConsumer<BlockPos, BlockPos> consumer){
        BlockPos start = getMin(startIn, targetIn);
        BlockPos target = getMax(startIn, targetIn);
        BlockPos.Mutable mutable = new BlockPos.Mutable(start.getX(), start.getY(), start.getZ());
        BlockPos.Mutable offset = new BlockPos.Mutable();

        int width = target.getX() - start.getX() + 1;
        int height = target.getY() - start.getY() + 1;
        int length = target.getZ() - start.getZ() + 1;

        for (int y = 0; y < height; y ++) {
            for (int z = 0; z < length; z++) {
                for(int x = 0; x < width; x++) {
                    consumer.accept(mutable, offset.set(x, y, z));
                    mutable.move(1, 0, 0);
                }
                mutable.move(-width, 0, 1);
            }
            mutable.move(0, 1, -length);
        }
    }
}
