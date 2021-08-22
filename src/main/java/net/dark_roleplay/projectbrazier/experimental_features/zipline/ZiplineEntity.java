package net.dark_roleplay.projectbrazier.experimental_features.zipline;

import net.dark_roleplay.projectbrazier.util.NBTUtil2;
import net.dark_roleplay.projectbrazier.util.VectorUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.lwjgl.system.MathUtil;

public class ZiplineEntity extends Entity {

	public static final IDataSerializer<Vector3f> VECTOR = new IDataSerializer<Vector3f>() {
		public void write(PacketBuffer buf, Vector3f value) {
			buf.writeFloat(value.getX());
			buf.writeFloat(value.getY());
			buf.writeFloat(value.getZ());
		}

		public Vector3f read(PacketBuffer buf) {
			return new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
		}

		public Vector3f copyValue(Vector3f value) {
			return new Vector3f(value.getX(), value.getY(), value.getZ());
		}
	};

	static{
		DataSerializers.registerSerializer(VECTOR);
	}

	private static final DataParameter<Vector3f> START = EntityDataManager.createKey(ZiplineEntity.class, VECTOR);
	private static final DataParameter<Vector3f> END = EntityDataManager.createKey(ZiplineEntity.class, VECTOR);
	private static final DataParameter<Vector3f> MID = EntityDataManager.createKey(ZiplineEntity.class, VECTOR);

	double[] LUT;

	float accel = 0.01F;
	float speed = 0.005F;
	double dist = 0F;

	public ZiplineEntity(EntityType<?> type, World world) {
		this(type, world, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
	}

	public ZiplineEntity(EntityType<?> type, World world, Vector3f start, Vector3f end, Vector3f mid) {
		super(type, world);

		start.add(0, -3, 0);
		end.add(0, -3, 0);
		mid.add(0, -3, 0);

		this.setBezierStart(start);
		this.setBezierEnd(end);
		this.setBezierMid(mid);

		long millisStart = System.currentTimeMillis();


		this.rotationYaw = (float) (Math.atan2(end.getZ() - start.getZ(), end.getX() - start.getX()) * 180/Math.PI) - 90;
		this.prevRotationYaw = rotationYaw;

		this.setPosition(start.getX(), start.getY(), start.getZ());

		int steps = 100;
		LUT = new double[steps + 1];
		double currentDistance = 0;
		Vector3f currentPos = start;
		LUT[0] = 0;
		for(int i = 1; i <= steps; i++){
			Vector3f bezierPos = getBezierPos(start, end, mid, ((float)i)/steps);
			currentDistance += VectorUtils.getDistance(currentPos, bezierPos);
			currentPos = bezierPos;
			LUT[i] = currentDistance;
		}

		System.out.println("Calculations took: " + (System.currentTimeMillis() - millisStart));
	}

	@Override
	protected void registerData() {
		this.dataManager.register(START, new Vector3f(0, 0, 0));
		this.dataManager.register(END, new Vector3f(0, 0, 0));
		this.dataManager.register(MID, new Vector3f(0, 0, 0));
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		compound.put("start", NBTUtil2.writeVector3f(this.getBezierStart()));
		compound.put("end", NBTUtil2.writeVector3f(this.getBezierEnd()));
		compound.put("mid", NBTUtil2.writeVector3f(this.getBezierMid()));
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		this.setBezierMid(NBTUtil2.readVector3f(compound.getCompound("start")));
		this.setBezierEnd(NBTUtil2.readVector3f(compound.getCompound("end")));
		this.setBezierMid(NBTUtil2.readVector3f(compound.getCompound("mid")));
	}

	@Override
	public boolean shouldRiderSit() {
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

		if(progress >= 1F && !this.getEntityWorld().isRemote){
			this.remove();
		}

		Vector3f newPos = getBezierPos(this.getBezierStart(), this.getBezierEnd(), this.getBezierMid(), progress);
		//Handle actual movement, client & server side
		if (!this.world.isRemote) {
			this.setPosition(newPos.getX(), newPos.getY(), newPos.getZ());
		} else {
			this.setRawPosition(newPos.getX(), newPos.getY(), newPos.getZ());
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
//		passenger.setMotion(VectorUtils.subtract(getBezierPos(bezierStart, bezierEnd, bezierMid, progress), this.getPositionVec()).m(50, 1, 50));
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

	public static Vector3f getBezierPos(Vector3f bezierStart, Vector3f bezierEnd, Vector3f bezierMid, double progress){
		Vector3f a = VectorUtils.lerpVector(bezierStart, bezierMid, progress);
		Vector3f b = VectorUtils.lerpVector(bezierMid, bezierEnd, progress);
		return VectorUtils.lerpVector(a, b, progress);
	}

	public Vector3f getBezierStart() {
		return this.dataManager.get(START);
	}

	public Vector3f getBezierEnd() {
		return this.dataManager.get(END);
	}

	public Vector3f getBezierMid() {
		return this.dataManager.get(MID);
	}

	public void setBezierStart(Vector3f bezierStart) {
		this.dataManager.set(START, bezierStart);
	}

	public void setBezierEnd(Vector3f bezierEnd) {
		this.dataManager.set(END, bezierEnd);
	}

	public void setBezierMid(Vector3f bezierMid) {
		this.dataManager.set(MID, bezierMid);
	}
}
