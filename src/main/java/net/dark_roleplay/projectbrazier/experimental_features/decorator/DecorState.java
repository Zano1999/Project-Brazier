package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;

public class DecorState{

	private Decor decor;
	private Vector3i chunkPos;
	private Vector3d chunkRelativePos;
	private Vector3d rotation;

	public DecorState(Decor decor){
		this(decor, new Vector3i(0, 0, 0), new Vector3d(0, 0, 0), new Vector3d(0, 0, 0));
	}

	public DecorState(Decor decor, Vector3i chunkPos, Vector3d chunkRelativePos, Vector3d rotation) {
		this.decor = decor;
		this.chunkPos = chunkPos;
		this.chunkRelativePos = chunkRelativePos;
		this.rotation = rotation;
	}

	public Decor getDecor() {
		return decor;
	}

	public Vector3i getChunkPos() {
		return chunkPos;
	}

	public Vector3d getChunkRelativePos() {
		return chunkRelativePos;
	}

	public Vector3d getRotation() {
		return rotation;
	}

	public void setDecor(Decor decor) {
		this.decor = decor;
	}

	protected void setPosition(Vector3i position, Vector3d chunkPosition) {
		this.chunkPos = new Vector3i(position.getX(), position.getY(), position.getZ());
		this.chunkRelativePos = chunkPosition;
	}

	public void setPosition(BlockPos position, Vector3d chunkPosition) {
		this.chunkPos = new Vector3i(position.getX() >> 4, position.getY() >> 4, position.getZ() >> 4);
		this.chunkRelativePos = chunkPosition;
	}

	public void setRotation(Vector3d rotation) {
		this.rotation = rotation;
	}

	public AxisAlignedBB getBoundingBox(){
		return new AxisAlignedBB(this.chunkRelativePos, this.chunkRelativePos.add(1, 1, 1))
				.offset(this.chunkPos.getX() << 4, this.chunkPos.getY() << 4, this.chunkPos.getZ() << 4);
	}

	public CompoundNBT serialize() {
		CompoundNBT tag = new CompoundNBT();

		tag.putDouble("pX", chunkPos.getX());
		tag.putDouble("pY", chunkPos.getY());
		tag.putDouble("pZ", chunkPos.getZ());

		tag.putDouble("cpX", chunkRelativePos.getX());
		tag.putDouble("cpY", chunkRelativePos.getY());
		tag.putDouble("cpZ", chunkRelativePos.getZ());

		tag.putDouble("rotX", rotation.getX());
		tag.putDouble("rotY", rotation.getY());
		tag.putDouble("rotZ", rotation.getZ());
		return tag;
	}

	public void deserialize(CompoundNBT tag) {
		this.setPosition(
				new Vector3i(tag.getInt("pX"), tag.getDouble("pY"), tag.getDouble("pZ")),
				new Vector3d(tag.getDouble("cpX"), tag.getDouble("cpY"), tag.getDouble("cpZ"))
		);
		this.setRotation(new Vector3d(tag.getDouble("rotX"), tag.getDouble("rotY"), tag.getDouble("rotZ")));
	}
}