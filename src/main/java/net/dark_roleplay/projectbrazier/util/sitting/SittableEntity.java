package net.dark_roleplay.projectbrazier.util.sitting;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SittableEntity extends Entity {
	public int blockPosX;
	public int blockPosY;
	public int blockPosZ;

	public SittableEntity(EntityType type, World world) {
		super(type, world);
		this.noClip = true;
	}

	public SittableEntity(EntityType type, World world, double x, double y, double z, double yOffset) {
		this(type, world);
		this.blockPosX = (int) x;
		this.blockPosY = (int) y;
		this.blockPosZ = (int) z;
		setPosition(x + 0.5D, y + yOffset, z + 0.5D);
	}

	public void setRotation(Direction facing) {
		setRotation(facing.getHorizontalAngle(), 90);
	}

	@Override
	public double getMountedYOffset() {
		return this.getSize(Pose.STANDING).height * 0.0D;
	}

	@Override
	public void tick() {
		if (!this.getEntityWorld().isRemote) {
			if (!this.isBeingRidden()
					|| this.getEntityWorld().isAirBlock(new BlockPos(blockPosX, blockPosY, blockPosZ))) {
				this.remove();
			}
		}
	}

	protected void applyYawToEntity(Entity entityToUpdate) {
		if(this.rotationPitch < 89 || this.rotationPitch > 91) return;
		entityToUpdate.setRenderYawOffset(this.rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void applyOrientationToEntity(Entity entityToUpdate) {
		this.applyYawToEntity(entityToUpdate);
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return true;
	}

	@Override
	protected void registerData() {
	}
	
	@Override
	protected void readAdditional(CompoundNBT compound) {}

	@Override
	protected void writeAdditional(CompoundNBT p_213281_1_) {}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
