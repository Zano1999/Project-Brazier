package net.dark_roleplay.projectbrazier.feature.packets;

import net.dark_roleplay.projectbrazier.experimental_features.drawbridges.DrawbridgeAnchorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncDrawbridgeState {

	private DrawbridgeAnchorTileEntity.State state;
	private float angle;
	private BlockPos pos;

	public SyncDrawbridgeState(){}

	public SyncDrawbridgeState(DrawbridgeAnchorTileEntity te){
		this.state = te.getMovementState();
		this.angle = te.getAngle();
		this.pos = te.getBlockPos();
	}

	public static void encode(SyncDrawbridgeState pkt, FriendlyByteBuf buffer){
		buffer.writeBlockPos(pkt.pos);
		buffer.writeFloat(pkt.angle);
		buffer.writeInt(pkt.state.getID());
	}

	public static SyncDrawbridgeState decode(FriendlyByteBuf buffer){
		SyncDrawbridgeState pkt = new SyncDrawbridgeState();
		pkt.pos = buffer.readBlockPos();
		pkt.angle = buffer.readFloat();
		pkt.state = DrawbridgeAnchorTileEntity.State.getFromID(buffer.readInt());

		return pkt;
	}

	public static void handle(SyncDrawbridgeState pkt, Supplier<NetworkEvent.Context> ctxSupplier){
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> {
			BlockEntity te = Minecraft.getInstance().level.getBlockEntity(pkt.pos);
			if(te != null && te instanceof DrawbridgeAnchorTileEntity){
				DrawbridgeAnchorTileEntity anchorTe = (DrawbridgeAnchorTileEntity) te;
				anchorTe.setAngle(pkt.angle);
				anchorTe.setMovementState(pkt.state);
			}
		});
		ctx.setPacketHandled(true);
	}

}
