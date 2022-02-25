package net.dark_roleplay.projectbrazier.feature.blockentities;

import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature.helpers.ZiplineHelper;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.util.math.bezier.BezierCurve;
import net.dark_roleplay.projectbrazier.util.data.NBTUtil2;
import net.dark_roleplay.projectbrazier.util.math.VectorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

public class ZiplineBlockEntity extends BlockEntity {

	private BezierCurve curve;
	private Vector3f start, mid, end;

	//Only used clientside
	private Vector3f[] vertices = null;

	public ZiplineBlockEntity(BlockPos pos, BlockState state) {
		super(BrazierBlockEntities.ZIPLINE_ANCHOR.get(), pos, state);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		if(this.start == null || this.end == null) return;

		compound.put("start", NBTUtil2.writeVector3f(this.start));
		compound.put("end", NBTUtil2.writeVector3f(this.end));
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		if(!compound.contains("start")) return;
		this.setPositions(NBTUtil2.readVector3f(compound.getCompound("start")),NBTUtil2.readVector3f(compound.getCompound("end")), true);
	}

	@Override
	public AABB getRenderBoundingBox(){
		return INFINITE_EXTENT_AABB;
	}

	@Override
	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag);
		return tag;
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt){
		this.load(pkt.getTag());
	}

	public void setPositions(Vector3f start, Vector3f end, boolean isLoading){
		this.start = start.copy();
		this.end = end.copy();
		this.mid = VectorUtils.lerpVector(start, end, 0.6);
		float yDist = this.start.y() - this.end.y(),
				xDist = this.start.x() - this.end.x(),
				zDist = this.start.z() - this.end.z();
		float distance = (float) Math.sqrt(yDist * yDist + xDist * xDist + zDist * zDist);
		this.mid.setY(this.mid.y() - Math.min(yDist*0.4F,7F));

		this.curve = BezierCurve.createGlobal(this.start, this.mid, this.end, (int) distance);

		if(!isLoading)
			this.setChanged();
	}

	public void setPositionInitial(Vector3f start, Vector3f mid, Vector3f end, BezierCurve curve){
		this.start = start.copy();
		this.mid = mid.copy();
		this.end = end.copy();
		this.curve = curve;
		this.setChanged();
	}

	public void startPlayerZipline(Player player){
		ZiplineHelper.startZipline(player, this.getLevel(), this.start, this.end, this.mid);
	}

	public boolean isInitialized(){
		return this.start != null;
	}

	public Vector3f[] getVertices(){
		if(this.vertices != null)
			return this.vertices;

		if(this.isInitialized()){
			BezierCurve curves[] = {
					curve.offset(-0.0625F, -0.0625F),
					curve.offset(-0.0625F, 0.0625F),
					curve.offset(0.0625F, 0.0625F),
					curve.offset(0.0625F, -0.0625F)
			};

			Vector3f[] bezierPoints = curve.getBezierPoints();
			Vector3f[][] bezierPoints2 = {
					curves[0].getBezierPoints(),
					curves[1].getBezierPoints(),
					curves[2].getBezierPoints(),
					curves[3].getBezierPoints()
			};

			this.vertices = new Vector3f[bezierPoints.length * 4];

			for(int i = 0; i < bezierPoints.length; i++){
				vertices[i*4] = bezierPoints2[0][i];
				vertices[i*4 + 1] = bezierPoints2[1][i];
				vertices[i*4 + 2] = bezierPoints2[2][i];
				vertices[i*4 + 3] = bezierPoints2[3][i];
			}
			return this.vertices;
		}

		return null;
	}
}
