package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler.MedievalNetworking;
import net.dark_roleplay.medieval.networking.sign_post.SignPostEditPacket;
import net.dark_roleplay.medieval.networking.sign_post.SignPostPlacementPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RoadSignTileEntity extends TileEntity {

	private List<SignInfo> signs = new ArrayList<>();

	public RoadSignTileEntity() {
		super(null);
		//super(MedievalTileEntities.ROAD_SIGN.get());
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			//TODO add Sign MedievalItems
		}

		return super.getCapability(capability, side);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		ListNBT signs = compound.getList("signs", NBT.TAG_COMPOUND);
		for (int i = 0; i < signs.size(); i++)
			this.signs.add(new SignInfo(signs.getCompound(i)));
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		ListNBT signs = new ListNBT();
		for (int i = 0; i < this.signs.size(); i++)
			signs.add(this.signs.get(i).toNBT());
		compound.put("signs", signs);
		return super.write(compound);
	}

	public List<SignInfo> getSigns() {
		return signs;
	}

	public int addSign(int height, int direction, ItemStack signItem, boolean pointsRight){
		this.signs.add(new SignInfo("", direction, height, pointsRight, signItem));
		this.markDirty();
		return this.signs.size() - 1;
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT compound = new CompoundNBT();
		ListNBT signs = new ListNBT();
		for (int i = 0; i < this.signs.size(); i++)
			signs.add(this.signs.get(i).toNBT());
		compound.put("signs", signs);
		return new SUpdateTileEntityPacket(getPos(), 1, compound);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT compound = pkt.getNbtCompound();
		ListNBT signs = compound.getList("signs", NBT.TAG_COMPOUND);
		for (int i = 0; i < signs.size(); i++) {
			this.signs.add(new SignInfo(signs.getCompound(i)));
		}
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		this.read(tag);
	}

	public void sendChangesToServer(int signID){
		MedievalNetworking.CHANNEL.sendToServer(new SignPostEditPacket().setTileEntityPosition(this.getPos()).setSignID(signID).setNewText(this.getSigns().get(signID).getText()));
	}
}
