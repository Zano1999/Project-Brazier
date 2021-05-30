package net.dark_roleplay.projectbrazier.experimental_features.immersive_screen;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CameraEntity extends Entity {
	public CameraEntity(EntityType<CameraEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public void setup(Vector3d pos, Vector3f rotation){
		this.setPositionAndRotation(pos.getX(), pos.getY(), pos.getZ(), rotation.getX(), rotation.getY());
	}

	@Override
	protected void registerData() {}

	@Override
	protected void readAdditional(CompoundNBT compound) {}

	@Override
	protected void writeAdditional(CompoundNBT compound) {}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
