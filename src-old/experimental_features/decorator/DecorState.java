package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class DecorState{

	private Decor decor;
	private Vec3 position;
	private Vec3 rotation;

	public DecorState(Decor decor){
		this.decor = decor;
		this.position = new Vec3(0, 0, 0);
		this.rotation = new Vec3(0, 0, 0);
	}

	public DecorState(Decor decor, Vec3 position, Vec3 rotation) {
		this(decor);
		this.position = position;
		this.rotation = rotation;
	}

	public Decor getDecor() {
		return decor;
	}

	public Vec3 getPosition() {
		return position;
	}

	public Vec3 getRotation() {
		return rotation;
	}

	public void setDecor(Decor decor) {
		this.decor = decor;
	}

	public void setPosition(Vec3 position) {
		this.position = position;
	}

	public void setRotation(Vec3 rotation) {
		this.rotation = rotation;
	}

	public CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();

		tag.putDouble("posX", position.x());
		tag.putDouble("posY", position.y());
		tag.putDouble("posZ", position.z());

		tag.putDouble("rotX", rotation.x());
		tag.putDouble("rotY", rotation.y());
		tag.putDouble("rotZ", rotation.z());
		return tag;
	}

	public void deserialize(CompoundTag tag) {
		this.setPosition(new Vec3(tag.getDouble("posX"), tag.getDouble("posY"), tag.getDouble("posZ")));
		this.setRotation(new Vec3(tag.getDouble("rotX"), tag.getDouble("rotY"), tag.getDouble("rotZ")));
	}
}