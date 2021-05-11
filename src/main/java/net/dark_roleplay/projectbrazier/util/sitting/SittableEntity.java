package net.dark_roleplay.projectbrazier.util.sitting;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Arrays;

public class SittableEntity extends Entity {

	private BlockState requiredBlock;

	public SittableEntity(EntityType type, World world) {
		super(type, world);
		this.noClip = true;
		this.requiredBlock = Blocks.AIR.getDefaultState();
	}

	public SittableEntity(EntityType type, World world, double x, double y, double z, double yOffset, boolean requireBlock) {
		this(type, world);
		setPosition(x, y + yOffset, z);
		if (requireBlock)
			this.requiredBlock = this.getEntityWorld().getBlockState(this.getPosition());
	}

	public void setRotation(Direction facing) {
		setRotation(facing.getHorizontalAngle(), 90);
	}

	public void setRequiredBlock(BlockState state) {
		this.requiredBlock = state;
	}

	@Override
	public double getMountedYOffset() {
		return this.getSize(Pose.STANDING).height * 0.0D;
	}

	@Override
	public void tick() {
		if (!this.getEntityWorld().isRemote) {
			if (!this.isBeingRidden() || !this.requiredBlock.isAir() && this.requiredBlock != this.getEntityWorld().getBlockState(this.getPosition())) {
				this.remove();
			}
		}
	}

	protected void applyYawToEntity(Entity entityToUpdate) {
		if (this.rotationPitch < 89 || this.rotationPitch > 91) return;
		entityToUpdate.setRenderYawOffset(this.rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	}

	@Override
	protected Vector3d handlePistonMovement(Vector3d pos) {
		Vector3d val = handlePistonMovement(pos);
		if(val != Vector3d.ZERO)
			this.remove();
		return val;
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
	protected void readAdditional(CompoundNBT compound) {
		NBTUtil.readBlockState(compound.getCompound("requiredBlock"));
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.put("requiredBlock", NBTUtil.writeBlockState(this.requiredBlock));
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);
//		if(!passenger.getEntityWorld().isRemote())
//			passenger.setPositionAndUpdate(this.getPosX(), this.getPosY() + 5, this.getPosZ());
	}
}