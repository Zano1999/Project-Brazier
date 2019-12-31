package net.dark_roleplay.medieval.objects.blocks.utility.barrel;

import net.dark_roleplay.medieval.handler.MedievalTileEntities;
import net.dark_roleplay.medieval.objects.guis.generic_container.GenericContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BarrelTileEntity extends TileEntity implements INamedContainerProvider {

    private StorageType storageType = StorageType.NONE;

    private ItemStackHandler itemHandler = new ItemStackHandler(18) {
        @Override
        protected void onContentsChanged(int slot){
            for(int currentSlot = 0; currentSlot < BarrelTileEntity.this.itemHandler.getSlots(); currentSlot++) {
                if(!BarrelTileEntity.this.itemHandler.getStackInSlot(currentSlot).isEmpty()) {
                    if(BarrelTileEntity.this.storageType != StorageType.ITEMS)
                        BarrelTileEntity.this.storageType = StorageType.ITEMS;
                    break;
                }
                BarrelTileEntity.this.storageType = StorageType.NONE;
            }
            BarrelTileEntity.this.markDirty();
        }
    };

    private FluidTank fluidHandler = new FluidTank(16000){
        @Override
        protected void onContentsChanged(){

            ITextComponent prefix = new StringTextComponent("[").applyTextStyle(TextFormatting.GRAY)
                                .appendSibling(new StringTextComponent("PLUGIN").applyTextStyle(TextFormatting.GREEN))
                                .appendSibling(new StringTextComponent("]").applyTextStyle(TextFormatting.GRAY));

            ITextComponent
                    lBracket        = new StringTextComponent("[").applyTextStyle(TextFormatting.GRAY),
                    rBracket        = new StringTextComponent("]").applyTextStyle(TextFormatting.GRAY),
                    plTag           = new StringTextComponent("PLUGIN").applyTextStyle(TextFormatting.GREEN),
                    pluginPrefix    = lBracket.appendSibling(plTag).appendSibling(rBracket);


            if(this.getFluidAmount() == 0)
                BarrelTileEntity.this.storageType = StorageType.NONE;
            else if(BarrelTileEntity.this.storageType != StorageType.FLUID){
                BarrelTileEntity.this.storageType = StorageType.FLUID;
            }
            BarrelTileEntity.this.markDirty();
            BlockState state = BarrelTileEntity.this.world.getBlockState(BarrelTileEntity.this.getPos());
            BarrelTileEntity.this.world.notifyBlockUpdate(BarrelTileEntity.this.getPos(), state, state, 3);
        }
    };

    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.of(() ->  itemHandler);
    private LazyOptional<FluidTank> lazyFluidHandler =  LazyOptional.of(() ->  fluidHandler);

    public BarrelTileEntity() {
        super(MedievalTileEntities.BARREL.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("fluids")) {
            this.storageType = StorageType.FLUID;
            this.fluidHandler.readFromNBT(compound.getCompound("fluids"));
        }else if (compound.contains("items")) {
            this.storageType = StorageType.ITEMS;
            this.itemHandler.deserializeNBT(compound.getCompound("items"));
        }else {
            this.storageType = StorageType.NONE;
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        switch(this.storageType) {
            case FLUID:
                compound.put("fluids", this.fluidHandler.writeToNBT(new CompoundNBT()));
                break;
            case ITEMS:
                compound.put("items", this.itemHandler.serializeNBT());
                break;
            default:
                break;
        }
        return compound;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side){
        if ((this.storageType == StorageType.NONE || this.storageType == StorageType.ITEMS) && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && lazyItemHandler != null){
            return (LazyOptional<T>) lazyItemHandler;
        }
        if ((this.storageType == StorageType.NONE || this.storageType == StorageType.FLUID) && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && lazyFluidHandler != null){
            return (LazyOptional<T>) lazyFluidHandler;
        }
        return super.getCapability(cap, side);
    }

    public CompoundNBT saveToNbt(CompoundNBT compound){
        switch(this.storageType) {
            case FLUID:
                compound.put("fluids", this.fluidHandler.writeToNBT(new CompoundNBT()));
                break;
            case ITEMS:
                compound.put("items", this.itemHandler.serializeNBT());
                break;
            default:
                break;
        }

        return compound;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTag = new CompoundNBT();
        this.write(nbtTag);
        return new SUpdateTileEntityPacket(this.getPos(), 1, nbtTag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }


    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SUpdateTileEntityPacket pkt){
        this.read(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        this.read(tag);
    }

    public StorageType getStorageType() {
        return this.storageType;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("drpmedieval.gui.container.barrel");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new GenericContainer(id, playerInventory, this.world, this.pos);
    }

    public static enum StorageType {
        FLUID,
        ITEMS,
        DRINK,
        NONE
    }
}
