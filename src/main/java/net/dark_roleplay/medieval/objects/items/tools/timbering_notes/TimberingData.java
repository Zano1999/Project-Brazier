package net.dark_roleplay.medieval.objects.items.tools.timbering_notes;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public class TimberingData implements INBTSerializable<CompoundNBT> {

    private BlockPos posA;
    private BlockPos posB;

    private boolean canEditPositions = true;

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();

        if(posA != null) compound.put("PosA", NBTUtil.writeBlockPos(posA));
        if(posB != null) compound.put("PosB", NBTUtil.writeBlockPos(posB));

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(nbt.contains("PosA")) this.posA = NBTUtil.readBlockPos(nbt.getCompound("PosA"));
        if(nbt.contains("PosB")) this.posB = NBTUtil.readBlockPos(nbt.getCompound("PosB"));
    }

    public BlockPos getPosA() {
        return posA;
    }

    public boolean setPosA(BlockPos posA) {
        if(canEditPositions)
            this.posA = posA;
        return canEditPositions;
    }

    public BlockPos getPosB() {
        return posB;
    }

    public boolean setPosB(BlockPos posB) {
        if(canEditPositions)
            this.posB = posB;
        return canEditPositions;
    }
}
