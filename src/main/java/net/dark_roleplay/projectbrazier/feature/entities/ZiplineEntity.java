package net.dark_roleplay.projectbrazier.feature.entities;

import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierSounds;
import net.dark_roleplay.projectbrazier.util.data.NBTUtil2;
import net.dark_roleplay.projectbrazier.util.math.VectorUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class ZiplineEntity extends Entity implements IEntityAdditionalSpawnData {

	private static int STEPS = 100;

	double[] LUT;

	float accel = 0.02F;
	float speed = 0.05F;
	double dist = 0F;

	Vector3f start, end, mid;

	public ZiplineEntity(EntityType<?> type, Level world) {
		this(type, world, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
	}

	public ZiplineEntity(EntityType<?> type, Level world, Vector3f start, Vector3f end, Vector3f mid) {
		super(type, world);

		this.start = start.copy();
		this.end = end.copy();
		this.mid = mid.copy();
		this.start.setY(this.start.y() - 2.1875F);
		this.end.setY(this.end.y() - 2.1875F);
		this.mid.setY(this.mid.y() - 2.1875F);

		this.setYRot((float) (Math.atan2(end.z() - start.z(), end.x() - start.x()) * 180/Math.PI) - 90);
		this.yRotO = this.getYRot();

		this.setPos(start.x(), start.y(), start.z());

		calculateLUT(start, end, mid);
	}

	@Override
	public double getPassengersRidingOffset() {
		return 0.5D;
	}

	private void calculateLUT(Vector3f start, Vector3f end, Vector3f mid){
		LUT = new double[STEPS + 1];
		double currentDistance = 0;
		Vector3f currentPos = start;
		LUT[0] = 0;
		for(int i = 1; i <= STEPS; i++){
			Vector3f bezierPos = getBezierPos(start, end, mid, ((float)i)/STEPS);
			currentDistance += VectorUtils.getDistance(currentPos, bezierPos);
			currentPos = bezierPos;
			LUT[i] = currentDistance;
		}
	}

	@Override
	protected void defineSynchedData() {}

	@Override
	public boolean shouldRiderSit() {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void onPassengerTurned(Entity entityToUpdate) {
		entityToUpdate.setYBodyRot(this.getYRot());
		float f = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
		float f1 = Mth.clamp(f, -95, 95);
		entityToUpdate.yRotO += f1 - f;
		entityToUpdate.setYRot(entityToUpdate.getYRot() + f1 - f);
		entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
	}

//	@Override
//	protected boolean canBeRidden(Entity entityIn) {
//		return true;
//	}

	@Override
	public void tick(){
		super.tick();

		if(this.tickCount % 3 == 0)
			this.playSound(BrazierSounds.ZIPLINE.get(), 1, 1);

		//if(this.getLevel().isClientSide) return;

		this.dist += speed;
		this.speed = (float) Math.min(1, speed + accel);

		double progress = distToT(LUT, dist);

		if(dist + speed + 0.25F > LUT[LUT.length - 1] && !this.getLevel().isClientSide){
			this.getPassengers().forEach(passenger -> {
				passenger.stopRiding();
				passenger.setDeltaMovement(new Vec3(0, 5, 5));
			});
		}

		if(progress >= 1F && !this.getLevel().isClientSide){
			this.remove(RemovalReason.DISCARDED);
		}

		Vector3f newPos = getBezierPos(this.start, this.end, this.mid, progress);
		//Handle actual movement, client & server side
		this.setPos(newPos.x(), newPos.y(), newPos.z());

		if (!this.getLevel().isClientSide && !this.isVehicle()) this.remove(RemovalReason.DISCARDED);
	}



	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		this.start = NBTUtil2.readVector3f(compound.getCompound("start"));
		this.end = NBTUtil2.readVector3f(compound.getCompound("end"));
		this.mid = NBTUtil2.readVector3f(compound.getCompound("mid"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.put("start", NBTUtil2.writeVector3f(this.start));
		compound.put("end", NBTUtil2.writeVector3f(this.end));
		compound.put("mid", NBTUtil2.writeVector3f(this.mid));
	}

	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);

		double progress = distToT(LUT, this.dist + this.speed);
		Vec3 speed = new Vec3(VectorUtils.subtract(getBezierPos(this.start, this.end, this.mid, progress), this.position()))
				.multiply(5, 5, 5);
		System.out.println(speed);
		//passenger.setDeltaMovement(speed);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}


	public static double distToT(double[] lut, double distance){
		double arcLength = lut[lut.length -1];
		int n = lut.length;

		if(distance > 0 && distance < arcLength)
			for(int i = 0; i < n-1; i++)
				if(distance > lut[i] && distance < lut[i+1])
					return Mth.lerp((distance - lut[i]) / (lut[i+1] - lut[i]), i/(n-1F), (i+1)/(n-1F));

		return distance/arcLength; //distance is outside bezier curve
	}

	public static Vector3f getBezierPos(Vector3f bezierStart, Vector3f bezierEnd, Vector3f bezierMid, double progress){
		Vector3f a = VectorUtils.lerpVector(bezierStart, bezierMid, progress);
		Vector3f b = VectorUtils.lerpVector(bezierMid, bezierEnd, progress);
		return VectorUtils.lerpVector(a, b, progress);
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		CompoundTag tag = new CompoundTag();
		this.addAdditionalSaveData(tag);
		buffer.writeNbt(tag);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		this.readAdditionalSaveData(additionalData.readNbt());
		this.calculateLUT(this.start, this.end, this.mid);
	}
}