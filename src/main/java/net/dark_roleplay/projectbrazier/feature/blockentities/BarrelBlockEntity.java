package net.dark_roleplay.projectbrazier.feature.blockentities;

import net.dark_roleplay.projectbrazier.feature.blocks.BarrelStorageType;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature.containers.GeneralContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BarrelBlockEntity extends BlockEntity implements MenuProvider {

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
	public void load(BlockState state, CompoundTag compound) {
		super.load(state, compound);
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
	public CompoundTag save(CompoundTag compound) {
		super.save(compound);
		switch(this.storageType) {
			case FLUID:
				if(this.fluidHandler != null)
				compound.put("fluids", this.fluidHandler.writeToNBT(new CompoundTag()));
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
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return new ClientboundBlockEntityDataPacket(this.getBlockPos(), 1, this.save(new CompoundTag()));
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}


	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt){
		this.load(null, pkt.getTag());
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
				BarrelBlockEntity.this.setChanged();
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
				BarrelBlockEntity.this.setChanged();
				BlockState state = BarrelBlockEntity.this.level.getBlockState(BarrelBlockEntity.this.getBlockPos());
				BarrelBlockEntity.this.level.sendBlockUpdated(BarrelBlockEntity.this.getBlockPos(), state, state, 3);
			}
		};
	}

	//Invalidate Capabilities
	@Override
	public void setRemoved() {
		super.setRemoved();
		lazyItemHandler.invalidate();
		lazyFluidHandler.invalidate();
	}

	@Override
	public TextComponent getDisplayName() {
		return new TranslatableComponent("container.projectbrazier.gui.container.barrel");
	}

	@Nullable
	@Override
	public Container createMenu(int id, Inventory playerInventory, Player player) {
		return new GeneralContainer(id, playerInventory, this.level, this.worldPosition);
	}
	//endregion


	public ItemStackHandler getItemHandler() {
		return itemHandler;
	}
}
