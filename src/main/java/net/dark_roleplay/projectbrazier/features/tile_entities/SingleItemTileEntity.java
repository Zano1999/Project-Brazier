package net.dark_roleplay.projectbrazier.features.tile_entities;

import net.dark_roleplay.projectbrazier.handler.MedievalTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SingleItemTileEntity extends TileEntity {

	private final ItemStackHandler inventory = new ItemStackHandler(1){
		@Override
		public void onContentsChanged(int slot){
			SingleItemTileEntity.this.markDirty();
		}
	};
	private final LazyOptional<ItemStackHandler> lazyInventory = LazyOptional.of(() -> inventory);

	public SingleItemTileEntity() {
		super(MedievalTileEntities.SINGLE_ITEM_STORAGE.get());
	}


	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound = super.write(compound);
		compound.put("inventory", this.inventory.serializeNBT());
		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		if(compound.contains("inventory"))
			this.inventory.deserializeNBT( compound.getCompound("inventory"));
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side){
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (LazyOptional<T>) lazyInventory;

		return super.getCapability(cap, side);
	}
}
