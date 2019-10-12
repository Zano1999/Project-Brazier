package net.dark_roleplay.medieval.objects.blocks.utility.roofers_table;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Roof {

    private Cuboid cuboids[];
    private BlockPos totalMin, totalMax;

    public Roof(BlockPos... positions) {
        if(positions.length >= 2) {
            this.cuboids = new Cuboid[positions.length / 2];

            this.totalMin = positions[0];
            this.totalMax = positions[0];

            for (BlockPos pos : positions) {
                this.totalMin = new BlockPos(Math.min(totalMin.getX(), pos.getX()), Math.min(totalMin.getY(), pos.getY()), Math.min(totalMin.getZ(), pos.getZ()));
                this.totalMax = new BlockPos(Math.max(totalMax.getX(), pos.getX()), Math.max(totalMax.getY(), pos.getY()), Math.max(totalMax.getZ(), pos.getZ()));
            }

            for(int i = 0; i < cuboids.length; i ++){
                this.cuboids[i] = new Cuboid(totalMin, positions[i * 2], positions[i * 2 + 1]);
            }
        }
    }

    public void place(World world, int maxHeight) {
        byte[][] data = new byte[totalMax.getX() - totalMin.getX() + 1][totalMax.getZ() - totalMin.getZ() + 1];
        for(int x = 0; x <= totalMax.getX() - totalMin.getX(); x++)
            for(int z = 0; z <= totalMax.getZ() - totalMin.getZ(); z++)
                data[x][z] = -1;

        for(Cuboid cube : cuboids)
            cube.placeBorder(data, 0, maxHeight - 1);

        for(int x = 0; x <= totalMax.getX() - totalMin.getX(); x++){
            for(int z = 0; z <= totalMax.getZ() - totalMin.getZ(); z++){
                if(data[x][z] >= 0)
                    world.setBlockState(totalMin.south(z).east(x).up(data[x][z]), Blocks.DIAMOND_BLOCK.getDefaultState());
            }
        }
    }

    private class Cuboid {
        private int offsetX, offsetZ;
        private BlockPos start;
        private BlockPos end;
        private int width, length;

        public Cuboid(BlockPos globalMin, BlockPos start, BlockPos end) {
            this.start = new BlockPos(Math.min(start.getX(), end.getX()), Math.min(start.getY(), end.getY()), Math.min(start.getZ(), end.getZ()));
            this.end = new BlockPos(Math.max(start.getX(), end.getX()), Math.max(start.getY(), end.getY()), Math.max(start.getZ(), end.getZ()));
            this.width = this.end.getX() - this.start.getX() + 1;
            this.length = this.end.getZ() - this.start.getZ() + 1;
            this.offsetX = this.start.getX() - globalMin.getX();
            this.offsetZ = this.start.getZ() - globalMin.getZ();
        }

        public void placeBorder(byte[][] data, int height, int maxHeight) {
            if(height > Math.floor((width/2f) - 0.4F) || height > Math.floor((length/2f) - 0.4F)) return;
            boolean cut = maxHeight < height;
            int trueHeight = Math.min(height, maxHeight + 1);

            for(int x = height; x < width - height; x++){
                data[offsetX + x][offsetZ + height] = (byte) Math.max((byte) trueHeight, data[offsetX + x][offsetZ + height]);
                data[offsetX + x][offsetZ + length - height - 1] = (byte) Math.max((byte) trueHeight, data[offsetX + x][offsetZ + length - height - 1]);
            }
            for(int z = height; z < length - height; z++){
                data[offsetX + height][offsetZ + z] = (byte) Math.max((byte) trueHeight, data[offsetX + height][offsetZ + z]);
                data[offsetX + width - height - 1][offsetZ + z] = (byte) Math.max((byte) trueHeight, data[offsetX + width - height - 1][offsetZ + z]);
            }
            placeBorder(data, height + 1, maxHeight);
        }
    }
}