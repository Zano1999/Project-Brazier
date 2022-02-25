package net.dark_roleplay.projectbrazier.experimental_features.chopping_block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChoppingBlockBlockEntitySync {

	private BlockPos pos;
	private ItemStack item;

	public ChoppingBlockBlockEntitySync(){}

	public ChoppingBlockBlockEntitySync(ChoppingBlockBlockEntity be){
		this.pos = be.getBlockPos();
		this.item = be.getHeldItem();
	}

	public static void encode(ChoppingBlockBlockEntitySync pkt, FriendlyByteBuf buffer){
		buffer.writeBlockPos(pkt.pos);
		buffer.writeItem(pkt.item);
	}

	public static ChoppingBlockBlockEntitySync decode(FriendlyByteBuf buffer){
		ChoppingBlockBlockEntitySync pkt = new ChoppingBlockBlockEntitySync();
		pkt.pos = buffer.readBlockPos();
		pkt.item = buffer.readItem();
		return pkt;
	}

	public static void handle(ChoppingBlockBlockEntitySync pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			Level level = Minecraft.getInstance().level;
			if(!level.hasChunkAt(pkt.pos)) return;
			LevelChunk chunk = level.getChunkAt(pkt.pos);
			BlockEntity be = chunk.getBlockEntity(pkt.pos);
			if(!(level.getBlockEntity(pkt.pos) instanceof ChoppingBlockBlockEntity cbe)) return;
			cbe.forceSetItem(pkt.item);

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}
