package net.dark_roleplay.medieval.objects.blocks.building.roofs;

import net.dark_roleplay.medieval.handler_2.MedievalTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RoofTileEntity extends TileEntity {

    private BlockState containedState = null;
    private RoofModelHelper.RoofModel bakedModel = null;
    private boolean hasModelFailed = false;

    public RoofTileEntity() {
        super(MedievalTileEntities.SHINGLE_ROOF.get());
    }

    public void setContainedState(BlockState newState){
        this.containedState = newState;
        this.markDirty();
    }

    public BlockState getContainedState(){
        return this.containedState;
    }

    @OnlyIn(Dist.CLIENT)
    public RoofModelHelper.RoofModel getModel(){
        if(bakedModel != null) return bakedModel;
        if(hasModelFailed || containedState == null) return null;
        IBakedModel newModel = Minecraft.getInstance().getModelManager().getModel(BlockModelShapes.getModelLocation(containedState));
        if(newModel == null) {
            hasModelFailed = true;
            return null;
        }else{
            RoofModelHelper.RoofModel model = RoofModelHelper.create(this.getBlockState(), containedState, newModel);
            this.bakedModel = model;
            return this.bakedModel;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void invalidatedModel(){
        this.bakedModel = null;
        this.hasModelFailed = false;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        if(this.containedState != null)
            compound.put("state", NBTUtil.writeBlockState(this.containedState));
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if(compound.contains("state"))
            this.containedState = NBTUtil.readBlockState(compound.getCompound("state"));
    }


    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        super.handleUpdateTag(tag);
    }
}
