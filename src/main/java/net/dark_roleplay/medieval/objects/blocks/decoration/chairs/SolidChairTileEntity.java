package net.dark_roleplay.medieval.objects.blocks.decoration.chairs;

import net.dark_roleplay.medieval.handler_2.MedievalTileEntities;
import net.dark_roleplay.medieval.objects.guis.generic_container.GenericContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class SolidChairTileEntity extends TileEntity implements INamedContainerProvider{

	LockableLootTileEntity t;
	
	protected ItemStackHandler hidden_inventory = null;

	public SolidChairTileEntity() {
		super(MedievalTileEntities.SOLID_CHAIR.get());
		this.hidden_inventory = new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				SolidChairTileEntity.this.markDirty();
			}
		};
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> {
				return (T) hidden_inventory;
			});
		}

		return super.getCapability(capability, side);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		if (compound.contains("hidden_inventory"))
			this.hidden_inventory.deserializeNBT((CompoundNBT) compound.get("hidden_inventory"));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound = super.write(compound);
		CompoundNBT inventory = this.hidden_inventory.serializeNBT();
		compound.put("hidden_inventory", inventory);
		return compound;
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
		return new GenericContainer(id, playerInv, IWorldPosCallable.of(this.world, this.pos));
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("drpmedieval.gui.container.chair_hidden_compartment"); 
	}
}
