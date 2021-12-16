package net.dark_roleplay.projectbrazier.experimental_features.immersive_screen;

import com.mojang.math.Vector3f;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class CameraEntity extends Entity {
	public CameraEntity(EntityType<CameraEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public void setup(Vec3 pos, Vector3f rotation){
		this.absMoveTo(pos.x(), pos.y(), pos.z(), rotation.x(), rotation.y());
	}

	@Override
	protected void defineSynchedData() {}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}