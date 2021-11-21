package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;

public class DecorState{

	private Decor decor;
	private Vector3d position;
	private Vector3d rotation;

	public DecorState(Decor decor){
		this.decor = decor;
		this.position = new Vector3d(0, 0, 0);
		this.rotation = new Vector3d(0, 0, 0);
	}

	public DecorState(Decor decor, Vector3d position, Vector3d rotation) {
		this(decor);
		this.position = position;
		this.rotation = rotation;
	}

	public Decor getDecor() {
		return decor;
	}

	public Vector3d getPosition() {
		return position;
	}

	public Vector3d getRotation() {
		return rotation;
	}

	public void setDecor(Decor decor) {
		this.decor = decor;
	}

	public void setPosition(Vector3d position) {
		this.position = position;
	}

	public void setRotation(Vector3d rotation) {
		this.rotation = rotation;
	}

	public CompoundNBT serialize() {
		CompoundNBT tag = new CompoundNBT();

		tag.putDouble("posX", position.x());
		tag.putDouble("posY", position.y());
		tag.putDouble("posZ", position.z());

		tag.putDouble("rotX", rotation.x());
		tag.putDouble("rotY", rotation.y());
		tag.putDouble("rotZ", rotation.z());
		return tag;
	}

	public void deserialize(CompoundNBT tag) {
		this.setPosition(new Vector3d(tag.getDouble("posX"), tag.getDouble("posY"), tag.getDouble("posZ")));
		this.setRotation(new Vector3d(tag.getDouble("rotX"), tag.getDouble("rotY"), tag.getDouble("rotZ")));
	}
}