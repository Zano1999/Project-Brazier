package net.dark_roleplay.projectbrazier.util.sitting;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.entity.*;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class SittableEntity extends Entity {

	private BlockState requiredBlock;

	public SittableEntity(EntityType type, Level world) {
		super(type, world);
		this.noPhysics = true;
		this.requiredBlock = Blocks.AIR.defaultBlockState();
	}

	public SittableEntity(EntityType<SittableEntity> type, Level world, double x, double y, double z, double yOffset, boolean requireBlock) {
		this(type, world);
		setPos(x, y + yOffset, z);
		if (requireBlock)
			this.requiredBlock = this.getCommandSenderWorld().getBlockState(this.blockPosition());
	}

	public void setRotation(Direction facing) {
		setRot(facing.toYRot(), 90);
	}

	public void setRequiredBlock(BlockState state) {
		this.requiredBlock = state;
	}

	@Override
	public double getPassengersRidingOffset() {
		return this.getDimensions(Pose.STANDING).height * 0.0D;
	}

	@Override
	public void tick() {
		if (!this.getCommandSenderWorld().isClientSide) {
			if (!this.isVehicle() || !this.requiredBlock.isAir() && this.requiredBlock != this.getCommandSenderWorld().getBlockState(this.blockPosition())) {
				this.remove();
			}
		}
	}

	protected void applyYawToEntity(Entity entityToUpdate) {
		if (this.xRot < 89 || this.xRot > 91) return;
		entityToUpdate.setYBodyRot(this.yRot);
		float f = Mth.wrapDegrees(entityToUpdate.yRot - this.yRot);
		float f1 = Mth.clamp(f, -105.0F, 105.0F);
		entityToUpdate.yRotO += f1 - f;
		entityToUpdate.yRot += f1 - f;
		entityToUpdate.setYHeadRot(entityToUpdate.yRot);
	}

	@Override
	protected Vec3 limitPistonMovement(Vec3 pos) {
		Vec3 val = limitPistonMovement(pos);
		if(val != Vec3.ZERO)
			this.remove();
		return val;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void onPassengerTurned(Entity entityToUpdate) {
		this.applyYawToEntity(entityToUpdate);
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return true;
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		NBTUtil.readBlockState(compound.getCompound("requiredBlock"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.put("requiredBlock", NBTUtil.writeBlockState(this.requiredBlock));
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);
//		if(!passenger.getEntityWorld().isRemote())
//			passenger.setPositionAndUpdate(this.getPosX(), this.getPosY() + 5, this.getPosZ());
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity rider) {
		Level world = this.getCommandSenderWorld();
		BlockPos pos = this.blockPosition();
		BlockState stateAtPos = world.getBlockState(pos);

		Direction dir = Direction.UP;
		if(stateAtPos.hasProperty(BlockStateProperties.HORIZONTAL_FACING)){
			dir = stateAtPos.getValue(BlockStateProperties.HORIZONTAL_FACING);
		}else if(stateAtPos.hasProperty(BlockStateProperties.FACING)){
			dir = stateAtPos.getValue(BlockStateProperties.FACING);
		}else if(stateAtPos.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)){
			Direction.Axis axis = stateAtPos.getValue(BlockStateProperties.HORIZONTAL_AXIS);
			if(axis == Direction.Axis.X)
				dir = Direction.NORTH;
			else if(axis == Direction.Axis.Z)
				dir = Direction.EAST;
		}

		BlockPos.Mutable testPos = pos.mutable();

		if(dir != Direction.UP && (isValidDismountPosition(world, testPos.move(dir), rider) ||
				isValidDismountPosition(world, testPos.setWithOffset(pos, dir.getClockWise()), rider) ||
				isValidDismountPosition(world, testPos.setWithOffset(pos, dir.getCounterClockWise()), rider) ||
				isValidDismountPosition(world, testPos.setWithOffset(pos, dir.getOpposite()), rider)
		)){
			return new Vec3(testPos.getX() + 0.5, testPos.getY() + 0.5, testPos.getZ() + 0.5);
		}

		return new Vec3(this.getX(), this.getBoundingBox().maxY + 1, this.getZ());
	}

	private boolean isValidDismountPosition(Level world, BlockPos pos, Entity entity){
		return world.getBlockState(pos).isAir(world, pos) && world.getBlockState(pos.below()).entityCanStandOnFace(world, pos, entity, Direction.UP);
	}
}