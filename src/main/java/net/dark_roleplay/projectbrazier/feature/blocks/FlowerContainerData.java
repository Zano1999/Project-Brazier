package net.dark_roleplay.projectbrazier.feature.blocks;

import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class FlowerContainerData {

	protected ItemStack flower;
	protected Vec3i placement;

	public FlowerContainerData(){
		this(ItemStack.EMPTY, new Vec3i(0, 0, 0));
	}

	public FlowerContainerData(ItemStack flower, Vec3i placement){
		this.flower = flower;
		this.placement = placement;
	}

	public ItemStack getFlower() {
		return flower;
	}

	public void setFlower(ItemStack flower){
		this.flower = flower;
	}

	public void setPlacement(Vec3i placement){
		this.placement = placement;
	}

	public Vec3i getPlacement() {
		return placement;
	}

	public CompoundTag serialize(){
		CompoundTag nbt = new CompoundTag();
		if(!flower.isEmpty()){
			nbt.put("stack", flower.save(new CompoundTag()));
			nbt.putLong("pos", ((placement.getX() & 0xFFF) << 24) | ((placement.getY() & 0xFFF) << 12) | ((placement.getZ()) & 0xFFF));
		}
		return nbt;
	}

	public void deserialize(CompoundTag nbt){
		if(!nbt.contains("stack")) return;
		this.flower = ItemStack.of(nbt.getCompound("stack"));
		long pos = nbt.getLong("pos");
		this.placement = new Vec3i(pos >> 24 & 0xFFF, pos >> 12 & 0xFFF, pos & 0xFFF);
	}
}
