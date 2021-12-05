package net.dark_roleplay.projectbrazier.feature.blockentities;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HangingItemBlockEntity extends BlockEntity {

	private final ItemStackHandler inventory = new ItemStackHandler(1){
		@Override
		public void onContentsChanged(int slot){
			HangingItemBlockEntity.this.setChanged();
		}
	};
	private final LazyOptional<ItemStackHandler> lazyInventory = LazyOptional.of(() -> inventory);

	public HangingItemBlockEntity(BlockPos pos, BlockState state) {
		super(BrazierBlockEntities.SINGLE_ITEM_STORAGE.get(), pos, state);
	}

	@Override
	public CompoundTag save(CompoundTag compound) {
		compound = super.save(compound);
		compound.put("inventory", this.inventory.serializeNBT());
		return compound;
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
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
