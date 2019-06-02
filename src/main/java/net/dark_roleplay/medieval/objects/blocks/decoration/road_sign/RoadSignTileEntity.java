package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import javax.annotation.Nullable;

import net.dark_roleplay.drpmarg.api.Constants;
import net.dark_roleplay.drpmarg.api.Materials;
import net.dark_roleplay.drpmarg.api.materials.Material;
import net.dark_roleplay.medieval.holders.MedievalTileEntities;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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
		this.signs = new SignInfo[] {
				new SignInfo("Castle", 45, 4, true, "acacia"),
				new SignInfo("Village", 0, 12, false, "birch") };
	}

	public VoxelShape getSignsShape() {

		if (signVoxelShape == null) {
			VoxelShape shape = null;
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
	public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
		}

		return super.getCapability(capability, side);
	}

	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);

		NBTTagList signs = compound.getList("signs", NBT.TAG_COMPOUND);
		this.signs = new SignInfo[signs.size()];
		for (int i = 0; i < signs.size(); i++) {
			this.signs[i] = new SignInfo(signs.getCompound(i));
		}
	}

	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		NBTTagList signs = new NBTTagList();
		for (int i = 0; i < this.signs.length; i++)
			if (this.signs[i] != null)
				signs.add(this.signs[i].toNBT());
		compound.setTag("signs", signs);
		return super.write(compound);
	}

	public SignInfo[] getSigns() {
		return signs;
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		//TODO Optimize, so only changes are sent
		NBTTagList signs = new NBTTagList();
		for (int i = 0; i < this.signs.length; i++)
			if (this.signs[i] != null)
				signs.add(this.signs[i].toNBT());
		nbtTag.setTag("signs", signs);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.write(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound tag = pkt.getNbtCompound();
		//TODO Optimize, so only changes are sent
		NBTTagList signs = tag.getList("signs", NBT.TAG_COMPOUND);
		this.signs = new SignInfo[signs.size()];
		for (int i = 0; i < signs.size(); i++) {
			this.signs[i] = new SignInfo(signs.getCompound(i));
		}
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.read(tag);
	}
}
