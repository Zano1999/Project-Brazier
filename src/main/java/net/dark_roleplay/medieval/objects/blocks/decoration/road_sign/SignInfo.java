package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.minecraft.nbt.CompoundNBT;

public class SignInfo {

	private int direction = 0;
	private String text = "";
	private boolean pointsLeft = true;
	private int height = 0;
	private String material = "";
	
	public SignInfo(String text, int direction, int height, boolean pointsLeft, String material) {
		this.direction = direction;
		this.pointsLeft = pointsLeft;
		this.text = text;
		this.height = height;
		this.material = material;
	}
	
	public SignInfo(CompoundNBT tag) {
		this.text = tag.getString("text");
		this.direction = tag.getInt("direction");
		this.height = tag.getInt("height");
		this.pointsLeft = tag.getBoolean("isLeft");
		this.material = tag.getString("material");
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

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public CompoundNBT toNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putString("text", this.text);
		tag.putInt("direction", this.direction);
		tag.putInt("height", this.height);
		tag.putBoolean("isLeft", this.pointsLeft);
		tag.putString("material", this.material);
		return tag;
	}
}
