package net.dark_roleplay.medieval.objects.blocks.decoration.chairs;

import net.dark_roleplay.medieval.holders.MedievalGuis;
import net.dark_roleplay.medieval.holders.MedievalTileEntities;
import net.dark_roleplay.medieval.objects.guis.generic_container.GenericContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class SolidChairTileEntity extends TileEntity implements IInteractionObject{

	TileEntityLockableLoot t;
	
	protected ItemStackHandler hidden_inventory = null;

	public SolidChairTileEntity() {
		super(MedievalTileEntities.SOLID_CHAIR_ARMREST);
		this.hidden_inventory = new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				SolidChairTileEntity.this.markDirty();
			}
		};
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> {
				return (T) hidden_inventory;
			});
		}

		return super.getCapability(capability, side);
	}

	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);

		if (compound.hasKey("hidden_inventory"))
			this.hidden_inventory.deserializeNBT((NBTTagCompound) compound.getTag("hidden_inventory"));
	}

	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		compound = super.write(compound);
		NBTTagCompound inventory = this.hidden_inventory.serializeNBT();
		compound.setTag("hidden_inventory", inventory);
		return compound;
	}

	@Override
	public ITextComponent getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getCustomName() {
		return null;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new GenericContainer(this, playerInventory);
	}

	@Override
	public String getGuiID() {
		return MedievalGuis.GUI_GENERIC_STORAGE.toString();
	}
}
