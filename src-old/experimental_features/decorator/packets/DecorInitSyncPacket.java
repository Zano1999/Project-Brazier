package net.dark_roleplay.projectbrazier.experimental_features.decorator.packets;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.listeners.DecorClientListeners;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

//TODO Make Server Friendly
public class DecorInitSyncPacket {

	private ResourceLocation dimensionName;
	private DecorChunk decor;
	private BlockPos chunkPos;

	public DecorInitSyncPacket(){}

	public DecorInitSyncPacket(ResourceLocation dimensionName, DecorChunk decor, BlockPos chunkPos) {
		this.dimensionName = dimensionName;
		this.decor = decor;
		this.chunkPos = chunkPos;
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

	public static void encode(DecorInitSyncPacket pkt, FriendlyByteBuf buffer){
		buffer.writeResourceLocation(pkt.dimensionName);
		buffer.writeBlockPos(pkt.chunkPos);
		buffer.writeNbt(pkt.decor.serializeNBT());
	}

	public static DecorInitSyncPacket decode(FriendlyByteBuf buffer){
		DecorInitSyncPacket pkt = new DecorInitSyncPacket();

		pkt.dimensionName = buffer.readResourceLocation();
		pkt.chunkPos = buffer.readBlockPos();
		pkt.decor = new DecorChunk(pkt.chunkPos.getY() >> 4);
		pkt.decor.deserializeNBT(buffer.readNbt());

		return pkt;
	}

	public static void handle(DecorInitSyncPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			DecorClientListeners.SCHEDULED_PACKETS.put(new ChunkPos(pkt.chunkPos.getX() >> 4, pkt.chunkPos.getZ() >> 4), pkt);

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}