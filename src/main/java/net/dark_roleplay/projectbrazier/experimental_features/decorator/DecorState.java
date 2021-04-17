package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.minecraft.util.math.vector.Vector3d;

public class DecorState {

	private Decor decor;
	private Vector3d position;
	private Vector3d rotation;

	public DecorState(Decor decor, Vector3d position, Vector3d rotation) {
		this.decor = decor;
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
}