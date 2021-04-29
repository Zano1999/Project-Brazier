package net.dark_roleplay.projectbrazier.experimental_features.decorator.packets;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

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
			Chunk chunk = Minecraft.getInstance().world.getChunk(pkt.chunkPos.getX() >> 4, pkt.chunkPos.getZ() >> 4);
			LazyOptional<DecorContainer> capability = chunk.getCapability(DecorRegistrar.DECOR);
			capability.ifPresent(decorCon -> {
				decorCon.setDecorChunk(pkt.chunkPos.getY() >> 4, pkt.decor);
			});

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}