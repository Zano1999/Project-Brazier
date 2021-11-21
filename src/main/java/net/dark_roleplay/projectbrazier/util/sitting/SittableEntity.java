package net.dark_roleplay.projectbrazier.util.sitting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SittableEntity extends Entity {

	private BlockState requiredBlock;

	public SittableEntity(EntityType type, World world) {
		super(type, world);
		this.noPhysics = true;
		this.requiredBlock = Blocks.AIR.defaultBlockState();
	}

	public SittableEntity(EntityType<SittableEntity> type, World world, double x, double y, double z, double yOffset, boolean requireBlock) {
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
		float f = MathHelper.wrapDegrees(entityToUpdate.yRot - this.yRot);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entityToUpdate.yRotO += f1 - f;
		entityToUpdate.yRot += f1 - f;
		entityToUpdate.setYHeadRot(entityToUpdate.yRot);
	}

	@Override
	protected Vector3d limitPistonMovement(Vector3d pos) {
		Vector3d val = limitPistonMovement(pos);
		if(val != Vector3d.ZERO)
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
	protected void readAdditionalSaveData(CompoundNBT compound) {
		NBTUtil.readBlockState(compound.getCompound("requiredBlock"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT compound) {
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
	public Vector3d getDismountLocationForPassenger(LivingEntity rider) {
		World world = this.getCommandSenderWorld();
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
			return new Vector3d(testPos.getX() + 0.5, testPos.getY() + 0.5, testPos.getZ() + 0.5);
		}

		return new Vector3d(this.getX(), this.getBoundingBox().maxY + 1, this.getZ());
	}

	private boolean isValidDismountPosition(World world, BlockPos pos, Entity entity){
		return world.getBlockState(pos).isAir(world, pos) && world.getBlockState(pos.below()).entityCanStandOnFace(world, pos, entity, Direction.UP);
	}
}