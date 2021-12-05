package net.dark_roleplay.projectbrazier.util.block_pos;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraftforge.common.util.INBTSerializable;

public class SelectionRegion implements INBTSerializable<CompoundTag> {

    private BlockPos start;
    private BlockPos target;

    private int width;
    private int height;
    private int length;

    public SelectionRegion(){}


    public SelectionRegion(BlockPos start, BlockPos target){
        this.setRegion(start, target);
    }

    public void calcSize(){
        if(start == null || target == null) return;

        this.width = target.getX() - start.getX() + 1;
        this.height = target.getY() - start.getY() + 1;
        this.length = target.getZ() - start.getZ() + 1;
    }

    public BlockPos getStart() {
        return start;
    }

    public void setStart(BlockPos start) {
        this.start = start;
        this.calcSize();
    }

    public BlockPos getTarget() {
        return target;
    }

    public void setTarget(BlockPos target) {
        this.target = target;
        this.calcSize();
    }

    public void setRegion(BlockPos posA, BlockPos posB){
        if(posA != null && posB != null){
            this.start = BlockPosUtil.getMin(posA, posB);
            this.target = BlockPosUtil.getMax(posA, posB);
            this.calcSize();
        }else if(posA != null){
            this.start = posA;
        }else if(posB != null){
            this.target = posB;
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        if(this.start != null)
            tag.put("S", NbtUtils.writeBlockPos(this.start));
        if(this.target != null)
            tag.put("T", NbtUtils.writeBlockPos(this.target));

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if(nbt.contains("S"))
            this.start = NbtUtils.readBlockPos(nbt.getCompound("S"));
        if(nbt.contains("T"))
            this.target = NbtUtils.readBlockPos(nbt.getCompound("T"));

        this.calcSize();
    }
}
