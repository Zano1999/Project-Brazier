package net.dark_roleplay.projectbrazier.feature.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3i;

public class FlowerContainerData {

	protected ItemStack flower;
	protected Vector3i placement;

	public FlowerContainerData(){
		this(ItemStack.EMPTY, new Vector3i(0, 0, 0));
	}

	public FlowerContainerData(ItemStack flower, Vector3i placement){
		this.flower = flower;
		this.placement = placement;
	}

	public ItemStack getFlower() {
		return flower;
	}

	public void setFlower(ItemStack flower){
		this.flower = flower;
	}

	public void setPlacement(Vector3i placement){
		this.placement = placement;
	}

	public Vector3i getPlacement() {
		return placement;
	}

	public CompoundNBT serialize(){
		CompoundNBT nbt = new CompoundNBT();
		if(!flower.isEmpty()){
			nbt.put("stack", flower.write(new CompoundNBT()));
			nbt.putLong("pos", ((placement.getX() & 0xFFF) << 24) | ((placement.getY() & 0xFFF) << 12) | ((placement.getZ()) & 0xFFF));
		}
		return nbt;
	}

	public void deserialize(CompoundNBT nbt){
		if(!nbt.contains("stack")) return;
		this.flower = ItemStack.read(nbt.getCompound("stack"));
		long pos = nbt.getLong("pos");
		this.placement = new Vector3i(pos >> 24 & 0xFFF, pos >> 12 & 0xFFF, pos & 0xFFF);
	}
}
