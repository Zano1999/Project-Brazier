package net.dark_roleplay.projectbrazier.experimental_features.selective_item_block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectiveBlockItemPacket {

	private int index;

	public SelectiveBlockItemPacket(){}

	public SelectiveBlockItemPacket(int index){
		this.index = index;
	}

	public static void encode(SelectiveBlockItemPacket pkt, PacketBuffer buffer){
		buffer.writeInt(pkt.index);
	}

	public static SelectiveBlockItemPacket decode(PacketBuffer buffer){
		SelectiveBlockItemPacket pkt = new SelectiveBlockItemPacket();
		pkt.index = buffer.readInt();

		return pkt;
	}

	public static void handle(SelectiveBlockItemPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctxSupplier.get().getSender();

			SelectiveBlockItem item = SelectiveBlockItem.getHeldSelectiveBlockItem(player);
			if(item == null) return;

			item.setPlayerSelected(player.getGameProfile(), pkt.index);

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}
