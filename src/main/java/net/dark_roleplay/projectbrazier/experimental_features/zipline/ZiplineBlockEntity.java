package net.dark_roleplay.projectbrazier.experimental_features.zipline;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.util.NBTUtil2;
import net.dark_roleplay.projectbrazier.util.VectorUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class ZiplineBlockEntity extends TileEntity {

	private Vector3f start, end;

	public ZiplineBlockEntity() {
		this(null, null);
	}

	public ZiplineBlockEntity(Vector3f start, Vector3f end) {
		super(BrazierBlockEntities.ZIPLINE.get());
		this.start = start;
		this.end = end;
	}

	@Override
	public void setWorldAndPos(World world, BlockPos pos) {
		super.setWorldAndPos(world, pos);
		if(start == null)
			this.setStart(new Vector3f(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F));
		if(end == null)
			this.setEnd(new Vector3f(pos.getX() + 0.5F, pos.getY() - 10.5F, pos.getZ() + 30.5F));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.put("start", NBTUtil2.writeVector3f(this.start));
		compound.put("end", NBTUtil2.writeVector3f(this.end));
		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		this.start = NBTUtil2.readVector3f(compound.getCompound("start"));
		this.end = NBTUtil2.readVector3f(compound.getCompound("end"));
	}

	public Vector3f getStart() {
		return start;
	}

	public void setStart(Vector3f start) {
		this.start = start;
	}

	public Vector3f getEnd() {
		return end;
	}

	public void setEnd(Vector3f end) {
		this.end = end;
	}

	public void startZiplining(PlayerEntity player){
		Vector3f midVec = VectorUtils.lerpVector(this.start, this.end, 0.8);
		midVec.add(0, -5, 0);
		ZiplineHelper.startZipline(player, world, this.start, this.end, midVec);
	}
}
