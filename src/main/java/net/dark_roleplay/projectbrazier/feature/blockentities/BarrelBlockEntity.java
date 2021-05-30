package net.dark_roleplay.projectbrazier.feature.blockentities;

import net.dark_roleplay.projectbrazier.feature.blocks.BarrelStorageType;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature.containers.GeneralContainer;
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

public class BarrelBlockEntity extends TileEntity implements INamedContainerProvider {

	private BarrelStorageType storageType = BarrelStorageType.NONE;

	private ItemStackHandler itemHandler;
	private FluidTank fluidHandler;

	private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler == null ? itemHandler = createInventory() : itemHandler);
	private LazyOptional<FluidTank> lazyFluidHandler =  LazyOptional.of(() ->  fluidHandler == null ? fluidHandler = createFluidTank() : fluidHandler);

	public BarrelBlockEntity() {
		super(BrazierBlockEntities.BARREL_BLOCK_ENTITY.get());
	}

	public BarrelStorageType getStorageType() {
		return this.storageType;
	}

	//region (De-)Serialization
	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		if (compound.contains("fluids")) {
			this.storageType = BarrelStorageType.FLUID;
			if(this.fluidHandler == null)
				this.fluidHandler = createFluidTank();
			this.fluidHandler.readFromNBT(compound.getCompound("fluids"));
		}else if (compound.contains("items")) {
			this.storageType = BarrelStorageType.ITEMS;
			if(this.itemHandler == null)
				this.itemHandler = createInventory();
			this.itemHandler.deserializeNBT(compound.getCompound("items"));
		}else {
			this.storageType = BarrelStorageType.NONE;
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
		if ((this.storageType == BarrelStorageType.NONE || this.storageType == BarrelStorageType.ITEMS) && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && lazyItemHandler != null){
			return (LazyOptional<T>) lazyItemHandler;
		}
		if ((this.storageType == BarrelStorageType.NONE || this.storageType == BarrelStorageType.FLUID) && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && lazyFluidHandler != null){
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
						if(BarrelBlockEntity.this.storageType != BarrelStorageType.ITEMS)
							BarrelBlockEntity.this.storageType = BarrelStorageType.ITEMS;
						break;
					}
					BarrelBlockEntity.this.storageType = BarrelStorageType.NONE;
				}
				BarrelBlockEntity.this.markDirty();
			}
		};
	}

	private FluidTank createFluidTank(){
		return new FluidTank(16000){
			@Override
			protected void onContentsChanged(){
				if(this.getFluidAmount() == 0)
					BarrelBlockEntity.this.storageType = BarrelStorageType.NONE;
				else if(BarrelBlockEntity.this.storageType != BarrelStorageType.FLUID){
					BarrelBlockEntity.this.storageType = BarrelStorageType.FLUID;
				}
				BarrelBlockEntity.this.markDirty();
				BlockState state = BarrelBlockEntity.this.world.getBlockState(BarrelBlockEntity.this.getPos());
				BarrelBlockEntity.this.world.notifyBlockUpdate(BarrelBlockEntity.this.getPos(), state, state, 3);
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


	public ItemStackHandler getItemHandler() {
		return itemHandler;
	}
}
