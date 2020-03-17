package net.dark_roleplay.medieval.objects.timbered_clay.util;

import net.dark_roleplay.medieval.handler.MedievalBlocks;
import net.dark_roleplay.medieval.objects.blocks.building.timbered_clay.TimberedClay;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayEdgeVariant;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.dark_roleplay.medieval.util.block_pos.BlockPosUtil;
import net.dark_roleplay.medieval.util.block_pos.SelectionRegion;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public class TimberingData implements INBTSerializable<CompoundNBT> {

    private BlockPos posA, posB;
    private SelectionRegion selection = new SelectionRegion();
    private Direction.Axis axis;

    private TimberedClayState[][] area;

    public TimberingData(BlockPos posA, BlockPos posB){
        this.selection.setRegion(posA, posB);
        this.posA = posA;
        this.posB = posB;
    }

    public TimberingData(CompoundNBT nbt){
        this.deserializeNBT(nbt);
    }

    public void compileArea(World world){
        this.selection.calcSize();
        int horizontalSize = axis == Direction.Axis.X ? this.selection.getWidth() : this.selection.getLength();
        this.area = new TimberedClayState[horizontalSize][this.selection.getHeight()];
        BlockPosUtil.walkRegion(this.selection.getStart(), this.selection.getTarget(), (pos, offset) -> {
            BlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof TimberedClay){
                TimberedClay timberedClay = (TimberedClay) state.getBlock();
                area[axis == Direction.Axis.X ? offset.getX()  : offset.getZ()][this.selection.getHeight() - 1 - offset.getY()] = timberedClay.toTmberedClayState(state);
            }else if(state.getBlock() == MedievalBlocks.TIMBERED_CLAY.get()){
                area[axis == Direction.Axis.X ? offset.getX() : offset.getZ()][this.selection.getHeight() - 1 - offset.getY()] = TimberedClayState.of(TimberedClayVariant.CLEAN, TimberedClayEdgeVariant.edges[0]);
            }else {
                area[axis == Direction.Axis.X ? offset.getX()  : offset.getZ()][this.selection.getHeight() - 1 - offset.getY()] = TimberedClayState.EMPTY;
            }
        });
    }

    public void calculateAxis(){
        if(this.posA == null || this.posB == null) return;
        this.selection.setRegion(this.posA, this.posB);
        this.axis = this.selection.getWidth() > this.selection.getLength() ? Direction.Axis.X : Direction.Axis.Z;
    }

    public BlockPos getPosA() {
        return posA;
    }

    public BlockPos getPosB() {
        return posB;
    }

    public Direction.Axis getAxis() {
        return axis;
    }

    public SelectionRegion getSelection(){
        return this.selection;
    }

    public ITextComponent setPos(boolean editB, BlockPos pos){
        BlockPos existing = editB ? posA : posB;

        if(existing != null){
            if(existing.getX() != pos.getX() && existing.getZ() != pos.getZ())
                return new TranslationTextComponent("message.drpmedieval.timbering_notes.not2d").setStyle(new Style().setColor(TextFormatting.RED));

            if(Math.abs(existing.getX() - pos.getX()) >= 12 || Math.abs(existing.getZ() - pos.getZ()) >= 12 || Math.abs(existing.getY() - pos.getY()) >= 12)
                return new TranslationTextComponent("message.drpmedieval.timbering_notes.to_large").setStyle(new Style().setColor(TextFormatting.RED));
        }

        if(editB) this.posB = pos;
        else this.posA = pos;
        this.calculateAxis();
        return new TranslationTextComponent("message.drpmedieval.timbering_notes.set_" + (editB ? "start" : "target"));
    }


    public TimberedClayState[][] getArea() {
        return area;
    }

    public void setArea(TimberedClayState[][] area) {
        this.area = area;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();

        compound.put("Selection", this.selection.serializeNBT());
        if(this.posA != null)
            compound.put("A", NBTUtil.writeBlockPos(this.posA));
        if(this.posB != null)
            compound.put("B", NBTUtil.writeBlockPos(this.posB));

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(nbt.contains("Selection")) this.selection.deserializeNBT(nbt.getCompound("Selection"));
        if(nbt.contains("A")) this.posA = NBTUtil.readBlockPos(nbt.getCompound("A"));
        if(nbt.contains("B")) this.posB = NBTUtil.readBlockPos(nbt.getCompound("B"));

        if(this.posA != null && this.posB != null)
            calculateAxis();
    }
}
