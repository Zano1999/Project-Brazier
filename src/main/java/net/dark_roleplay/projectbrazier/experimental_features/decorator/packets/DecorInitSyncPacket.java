package net.dark_roleplay.projectbrazier.experimental_features.decorator.packets;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.listeners.DecorClientListeners;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

//TODO Make Server Friendly
public class DecorInitSyncPacket {

	private ResourceLocation dimensionName;
	private BlockPos chunkPos;
	private DecorChunk decor;

	public DecorInitSyncPacket(){}

	public DecorInitSyncPacket(ResourceLocation dimensionName, DecorChunk decor, BlockPos chunkPos) {
		this.dimensionName = dimensionName;
		this.chunkPos = chunkPos;
		this.decor = decor;
	}

	public ResourceLocation getDimensionName() {
		return dimensionName;
	}

	public DecorChunk getDecor() {
		return decor;
	}

	public BlockPos getChunkPos() {
		return chunkPos;
	}

	public static void encode(DecorInitSyncPacket pkt, PacketBuffer buffer){
		buffer.writeResourceLocation(pkt.dimensionName);
		buffer.writeBlockPos(pkt.chunkPos);
		buffer.writeCompoundTag(pkt.decor.serializeNBT());
	}

	public static DecorInitSyncPacket decode(PacketBuffer buffer){
		DecorInitSyncPacket pkt = new DecorInitSyncPacket();

		pkt.dimensionName = buffer.readResourceLocation();
		pkt.chunkPos = buffer.readBlockPos();
		pkt.decor = new DecorChunk(pkt.chunkPos.getY() >> 4);
		pkt.decor.deserializeNBT(buffer.readCompoundTag());

		return pkt;
	}

	public static void handle(DecorInitSyncPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			DecorClientListeners.SCHEDULED_PACKETS.put(new ChunkPos(pkt.chunkPos.getX() >> 4, pkt.chunkPos.getZ() >> 4), pkt);

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}