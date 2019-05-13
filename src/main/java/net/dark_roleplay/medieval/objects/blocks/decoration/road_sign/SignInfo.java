package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.minecraft.nbt.NBTTagCompound;

public class SignInfo {

	private int direction = 0;
	private String text = "";
	
	public SignInfo(String text, int direction) {
		this.direction = direction;
		this.text = text;
	}
	
	public SignInfo(NBTTagCompound tag) {
		this.text = tag.getString("text");
		this.direction = tag.getInt("direction");
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
	
	public NBTTagCompound toNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("text", this.text);
		tag.setInt("direction", direction);
		return tag;
	}
}
