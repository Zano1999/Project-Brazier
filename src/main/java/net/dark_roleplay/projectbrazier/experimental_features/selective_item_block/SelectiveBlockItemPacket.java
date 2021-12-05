package net.dark_roleplay.projectbrazier.experimental_features.selective_item_block;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectiveBlockItemPacket {

	private int index;

	public SelectiveBlockItemPacket(){}

	public SelectiveBlockItemPacket(int index){
		this.index = index;
	}

	public static void encode(SelectiveBlockItemPacket pkt, FriendlyByteBuf buffer){
		buffer.writeInt(pkt.index);
	}

	public static SelectiveBlockItemPacket decode(FriendlyByteBuf buffer){
		SelectiveBlockItemPacket pkt = new SelectiveBlockItemPacket();
		pkt.index = buffer.readInt();

		return pkt;
	}

	public static void handle(SelectiveBlockItemPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			ServerPlayer player = ctxSupplier.get().getSender();

			SelectiveBlockItem item = SelectiveBlockItem.getHeldSelectiveBlockItem(player);
			if(item == null) return;

			item.setPlayerSelected(player.getGameProfile(), pkt.index);

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}
