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

	public Vector3i getPlacement() {
		return placement;
	}

	public CompoundNBT serialize(){
		CompoundNBT nbt = new CompoundNBT();
		if(!flower.isEmpty()){
			nbt.put("stack", flower.write(new CompoundNBT()));
			nbt.putInt("pos", (placement.getX() & 0xFF << 16) | (placement.getY() & 0xFF << 8) | (placement.getZ() & 0xFF));
		}
		return nbt;
	}

	public void deserialize(CompoundNBT nbt){
		if(!nbt.contains("stack")) return;
		this.flower = ItemStack.read(nbt.getCompound("stack"));
		int pos = nbt.getInt("pos");
		this.placement = new Vector3i(pos >> 16 & 0xFF, pos >> 8 & 0xFF, pos & 0xFF);
	}
}
