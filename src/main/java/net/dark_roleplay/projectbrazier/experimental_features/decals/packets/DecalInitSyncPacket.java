package net.dark_roleplay.projectbrazier.experimental_features.decals.packets;

import net.dark_roleplay.projectbrazier.experimental_features.decals.capability.DecalChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.listeners.DecorClientListeners;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

//TODO Make Server Friendly
public class DecalInitSyncPacket {

	private ResourceLocation dimensionName;
	private DecalChunk decal;
	private BlockPos chunkPos;

	public DecalInitSyncPacket(){}

	public DecalInitSyncPacket(ResourceLocation dimensionName, DecalChunk decal, BlockPos chunkPos) {
		this.dimensionName = dimensionName;
		this.decal = decal;
		this.chunkPos = chunkPos;
	}

	public ResourceLocation getDimensionName() {
		return dimensionName;
	}

	public DecalChunk getDecal() {
		return decal;
	}

	public BlockPos getChunkPos() {
		return chunkPos;
	}

	public static void encode(DecalInitSyncPacket pkt, PacketBuffer buffer){
		buffer.writeResourceLocation(pkt.dimensionName);
		buffer.writeBlockPos(pkt.chunkPos);
		buffer.writeCompoundTag(pkt.decal.serializeNBT());
	}

	public static DecalInitSyncPacket decode(PacketBuffer buffer){
		DecalInitSyncPacket pkt = new DecalInitSyncPacket();

		pkt.dimensionName = buffer.readResourceLocation();
		pkt.chunkPos = buffer.readBlockPos();
		pkt.decal = new DecalChunk(pkt.chunkPos.getY() >> 4);
		pkt.decal.deserializeNBT(buffer.readCompoundTag());

		return pkt;
	}

	public static void handle(DecalInitSyncPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			//TODO Add rendering
			//DecorClientListeners.SCHEDULED_PACKETS.put(new ChunkPos(pkt.chunkPos.getX() >> 4, pkt.chunkPos.getZ() >> 4), pkt);

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}