package net.dark_roleplay.projectbrazier.experimental_features.zipline;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.lwjgl.system.MathUtil;

public class ZiplineEntity extends Entity {


	Vector3d bezierStart, bezierEnd, bezierMid;
	double[] LUT;

	float accel = 0.01F;
	float speed = 0.005F;
	double dist = 0F;

	public ZiplineEntity(EntityType<?> type, World world) {
		this(type, world, new Vector3d(350, 75, 0), new Vector3d(400, 60, 10), new Vector3d(375, 50, 5));
	}

	public ZiplineEntity(EntityType<?> type, World world, Vector3d start, Vector3d end, Vector3d mid) {
		super(type, world);

		long millisStart = System.currentTimeMillis();

		this.bezierStart = start.add(0, -3, 0);
		this.bezierEnd = end.add(0, -3, 0);
		this.bezierMid = mid.add(0, -3, 0);

		this.rotationYaw = (float) (Math.atan2(bezierEnd.z - bezierStart.z, bezierEnd.x - bezierStart.x) * 180/Math.PI) - 90;
		this.prevRotationYaw = rotationYaw;

		this.setPosition(bezierStart.x, bezierStart.y, bezierStart.z);

		int steps = 100;
		LUT = new double[steps + 1];
		double currentDistance = 0;
		Vector3d currentPos = bezierStart;
		LUT[0] = 0;
		for(int i = 1; i <= steps; i++){
			Vector3d bezierPos = getBezierPos(bezierStart, bezierEnd, bezierMid, ((float)i)/steps);
			currentDistance += currentPos.distanceTo(bezierPos);
			currentPos = bezierPos;
			LUT[i] = currentDistance;
		}

		System.out.println("Calculations took: " + (System.currentTimeMillis() - millisStart));
	}

	@Override
	protected void registerData() {

	}

	@Override
	protected void readAdditional(CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {

	}

	@Override
	public boolean shouldRiderSit()
	{
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void applyOrientationToEntity(Entity entityToUpdate) {
		entityToUpdate.setRenderYawOffset(this.rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return true;
	}

	@Override
	public void tick(){
		super.tick();

		this.dist += speed;
		this.speed = (float) Math.min(1, speed + accel);

		double progress = distToT(LUT, dist);

		if(progress >= 1F){
			this.remove();
		}

		Vector3d newPos = getBezierPos(bezierStart, bezierEnd, bezierMid, progress);
		//Handle actual movement, client & server side
		if (!this.world.isRemote) {
			this.setPosition(newPos.x, newPos.y, newPos.z);
		} else {
			this.setRawPosition(newPos.x, newPos.y, newPos.z);
		}

		if (!this.getEntityWorld().isRemote && !this.isBeingRidden()) this.remove();
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);

		double progress = distToT(LUT, this.dist + this.speed);
		passenger.setMotion(getBezierPos(bezierStart, bezierEnd, bezierMid, progress).subtract(this.getPositionVec()).mul(50, 1, 50));
	}

	private static Vector3d lerpVector(Vector3d a, Vector3d b, double pct){
		return new Vector3d(
				MathHelper.lerp(pct, a.x, b.x),
				MathHelper.lerp(pct, a.y, b.y),
				MathHelper.lerp(pct, a.z, b.z)
		);
	}

	public static double distToT(double[] lut, double distance){
		double arcLength = lut[lut.length -1];
		int n = lut.length;

		if(distance > 0 && distance < arcLength)
			for(int i = 0; i < n-1; i++)
				if(distance > lut[i] && distance < lut[i+1])
					return MathHelper.lerp((distance - lut[i]) / (lut[i+1] - lut[i]), i/(n-1F), (i+1)/(n-1F));

		return distance/arcLength; //distance is outside bezier curve
	}

	public static Vector3d getBezierPos(Vector3d bezierStart, Vector3d bezierEnd, Vector3d bezierMid, double progress){
		Vector3d a = lerpVector(bezierStart, bezierMid, progress);
		Vector3d b = lerpVector(bezierMid, bezierEnd, progress);
		return lerpVector(a, b, progress);
	}
}
