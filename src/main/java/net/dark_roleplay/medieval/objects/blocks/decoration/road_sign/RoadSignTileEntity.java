package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import javax.annotation.Nullable;

import net.dark_roleplay.medieval.holders.MedievalTileEntities;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class RoadSignTileEntity extends TileEntity {

	private VoxelShape signVoxelShape = null;

	private SignInfo[] signs = new SignInfo[] {};

	public RoadSignTileEntity() {
		super(MedievalTileEntities.ROAD_SIGN);
		//this.signs = new SignInfo[] {
		//		new SignInfo("Castle", 45, 4, true, "acacia"),
		//		new SignInfo("Village", 0, 12, false, "birch") };
	}

	public VoxelShape getSignsShape() {

		if (signVoxelShape == null) {
			VoxelShape shape = VoxelShapes.empty();
			for (SignInfo sign : this.signs) {
				float x1 = 0, x2 = 0, z1 = 0, z2 = 0;
				double rad = Math.toRadians(-sign.getDirection());

				// x= x cos rad - y sin rad
				// y= y cos rad + x sin rad
				float cos = (float) Math.cos(rad), sin = (float) Math.sin(rad);

				x1 = (float) (10.5 * cos + 2 * sin);
				z1 = (float) (-2 * cos + 10.5 * sin);
				x2 = (float) (-10.5 * cos + 4 * sin);
				z2 = (float) (-4 * cos - 10.5 * sin);

				x1 += 8;
				x2 += 8;
				z1 += 8;
				z2 += 8;

				if (shape == null)
					shape = Block.makeCuboidShape(x1, sign.getHeight() - 2.5, z1, x2, sign.getHeight() + 2.5, z2);
				else
					shape = VoxelShapes.combineAndSimplify(shape, shape = Block.makeCuboidShape(x1,
							sign.getHeight() - 2.5, z1, x2, sign.getHeight() + 2.5, z2), IBooleanFunction.OR);
			}
			this.signVoxelShape = shape;

		}
		return signVoxelShape;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
		}

		return super.getCapability(capability, side);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		ListNBT signs = compound.getList("signs", NBT.TAG_COMPOUND);
		this.signs = new SignInfo[signs.size()];
		for (int i = 0; i < signs.size(); i++) {
			this.signs[i] = new SignInfo(signs.getCompound(i));
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		ListNBT signs = new ListNBT();
		for (int i = 0; i < this.signs.length; i++)
			if (this.signs[i] != null)
				signs.add(this.signs[i].toNBT());
		compound.put("signs", signs);
		return super.write(compound);
	}

	public SignInfo[] getSigns() {
		return signs;
	}

	public void addSign(int height, int direction, String material, boolean pointsRight){
		SignInfo[] newSigns = new SignInfo[this.signs.length + 1];
		for(int i = 0; i < this.signs.length; i++){
			newSigns[i] = this.signs[i];
		}

		newSigns[this.signs.length] = new SignInfo("", direction, height, pointsRight, material);
		this.signs = newSigns;
		this.markDirty();
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTag = new CompoundNBT();
		//TODO Optimize, so only changes are sent
		ListNBT signs = new ListNBT();
		for (int i = 0; i < this.signs.length; i++)
			if (this.signs[i] != null)
				signs.add(this.signs[i].toNBT());
		nbtTag.put("signs", signs);
		return new SUpdateTileEntityPacket(getPos(), 1, nbtTag);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT tag = pkt.getNbtCompound();
		//TODO Optimize, so only changes are sent
		ListNBT signs = tag.getList("signs", NBT.TAG_COMPOUND);
		this.signs = new SignInfo[signs.size()];
		for (int i = 0; i < signs.size(); i++) {
			this.signs[i] = new SignInfo(signs.getCompound(i));
		}
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		this.read(tag);
	}
}
