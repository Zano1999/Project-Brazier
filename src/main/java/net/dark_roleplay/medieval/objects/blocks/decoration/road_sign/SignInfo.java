package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class SignInfo {

	private int direction = 0;
	private String text = "";
	private boolean pointsLeft = true;
	private int height = 0;
	private ItemStack signItem = null;
	
	public SignInfo(String text, int direction, int height, boolean pointsLeft, ItemStack signItem) {
		this.direction = direction;
		this.pointsLeft = pointsLeft;
		this.text = text;
		this.height = height;
		this.signItem = signItem;
	}
	
	public SignInfo(CompoundNBT tag) {
		this.text = tag.getString("text");
		this.direction = tag.getInt("direction");
		this.height = tag.getInt("height");
		this.pointsLeft = tag.getBoolean("isLeft");
		this.signItem = ItemStack.read(tag.getCompound("signItem"));
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isPointsLeft() {
		return pointsLeft;
	}

	public void setPointsLeft(boolean pointsLeft) {
		this.pointsLeft = pointsLeft;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ItemStack getSignItem() {
		return signItem;
	}

	public void setSignItem(ItemStack signItem) {
		this.signItem = signItem;
	}
	
	public CompoundNBT toNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putString("text", this.text);
		tag.putInt("direction", this.direction);
		tag.putInt("height", this.height);
		tag.putBoolean("isLeft", this.pointsLeft);
		tag.put("signItem", this.signItem.write(new CompoundNBT()));
		return tag;
	}
}
