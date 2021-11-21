package net.dark_roleplay.projectbrazier.feature.blockentities;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HangingItemBlockEntity extends TileEntity {

	private final ItemStackHandler inventory = new ItemStackHandler(1){
		@Override
		public void onContentsChanged(int slot){
			HangingItemBlockEntity.this.setChanged();
		}
	};
	private final LazyOptional<ItemStackHandler> lazyInventory = LazyOptional.of(() -> inventory);

	public HangingItemBlockEntity() {
		super(BrazierBlockEntities.SINGLE_ITEM_STORAGE.get());
	}


	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound = super.save(compound);
		compound.put("inventory", this.inventory.serializeNBT());
		return compound;
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
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
