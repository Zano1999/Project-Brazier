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
		this.absMoveTo(pos.x(), pos.y(), pos.z(), rotation.x(), rotation.y());
	}

	@Override
	protected void defineSynchedData() {}

	@Override
	protected void readAdditionalSaveData(CompoundNBT compound) {}

	@Override
	protected void addAdditionalSaveData(CompoundNBT compound) {}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
