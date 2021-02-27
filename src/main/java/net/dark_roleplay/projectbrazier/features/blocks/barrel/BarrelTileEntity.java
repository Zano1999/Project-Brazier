package net.dark_roleplay.projectbrazier.features.blocks.barrel;

import net.dark_roleplay.projectbrazier.features.screens.general_container.GeneralContainer;
import net.dark_roleplay.projectbrazier.handler.MedievalTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
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

	private ItemStackHandler itemHandler;
	private FluidTank fluidHandler;

	private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler == null ? itemHandler = createInventory() : itemHandler);
	private LazyOptional<FluidTank> lazyFluidHandler =  LazyOptional.of(() ->  fluidHandler == null ? fluidHandler = createFluidTank() : fluidHandler);

	//TODO Add TE TYPE
	public BarrelTileEntity() {
		super(MedievalTileEntities.BARREL.get());
	}

	public StorageType getStorageType() {
		return this.storageType;
	}

	//region (De-)Serialization
	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		if (compound.contains("fluids")) {
			this.storageType = StorageType.FLUID;
			if(this.fluidHandler == null)
				this.fluidHandler = createFluidTank();
			this.fluidHandler.readFromNBT(compound.getCompound("fluids"));
		}else if (compound.contains("items")) {
			this.storageType = StorageType.ITEMS;
			if(this.itemHandler == null)
				this.itemHandler = createInventory();
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
				if(this.fluidHandler != null)
				compound.put("fluids", this.fluidHandler.writeToNBT(new CompoundNBT()));
				break;
			case ITEMS:
				if(this.itemHandler != null)
				compound.put("items", this.itemHandler.serializeNBT());
				break;
			default:
				break;
		}
		return compound;
	}
	//endregion

	//region Server -> Client Sync
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.getPos(), 1, this.write(new CompoundNBT()));
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}


	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
		this.read(null, pkt.getNbtCompound());
	}

	//endregion

	//region Capability
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

	private ItemStackHandler createInventory(){
		return new ItemStackHandler(18) {
			@Override
			protected void onContentsChanged(int slot){
				for(int currentSlot = 0; currentSlot < this.getSlots(); currentSlot++) {
					if(!this.getStackInSlot(currentSlot).isEmpty()) {
						if(BarrelTileEntity.this.storageType != StorageType.ITEMS)
							BarrelTileEntity.this.storageType = StorageType.ITEMS;
						break;
					}
					BarrelTileEntity.this.storageType = StorageType.NONE;
				}
				BarrelTileEntity.this.markDirty();
			}
		};
	}

	private FluidTank createFluidTank(){
		return new FluidTank(16000){
			@Override
			protected void onContentsChanged(){
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
	}

	//Invalidate Capabilities
	@Override
	public void remove() {
		super.remove();
		lazyItemHandler.invalidate();
		lazyFluidHandler.invalidate();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.projectbrazier.gui.container.barrel");
	}

	@Nullable
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new GeneralContainer(id, playerInventory, this.world, this.pos);
	}
	//endregion

	public static enum StorageType{
		FLUID,
		ITEMS,
		DRINK,
		NONE
	}
}
