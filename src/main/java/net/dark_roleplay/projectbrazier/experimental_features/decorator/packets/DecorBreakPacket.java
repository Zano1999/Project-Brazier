package net.dark_roleplay.projectbrazier.experimental_features.decorator.packets;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorListener;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorState;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.IDecorator;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.RayTraceHelper;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.listeners.DecorClientListeners;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Supplier;

public class DecorBreakPacket extends DecorPacket{

	public DecorBreakPacket(){}

	public DecorBreakPacket(ResourceLocation dimensionName, DecorChunk decor, BlockPos chunkPos) {
		super(dimensionName, decor, chunkPos);
	}

	public static void encode(DecorBreakPacket pkt, PacketBuffer buffer){
		buffer.writeResourceLocation(pkt.getDimensionName());
		buffer.writeBlockPos(pkt.getChunkPos());
	}

	public static DecorBreakPacket decode(PacketBuffer buffer){
		DecorBreakPacket pkt = new DecorBreakPacket();

		pkt.setDimensionName(buffer.readResourceLocation());
		pkt.setChunkPos(buffer.readBlockPos());
		return pkt;
	}

	public static void handle(DecorBreakPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			//DecorClientListeners.SCHEDULED_PACKETS.put(new ChunkPos(pkt.chunkPos.getX() >> 4, pkt.chunkPos.getZ() >> 4), pkt);

			World world = ctxSupplier.get().getSender().getServerWorld();
			PlayerEntity player = ctxSupplier.get().getSender();
			if(!world.getDimensionKey().getRegistryName().equals(pkt.getDimensionName())) return;

			if(!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof IDecorator) {

				Triple<DecorChunk, DecorState, Vector3d> hitState = RayTraceHelper.rayTraceForDecor(world, player);

				if (hitState != null) {
					hitState.getLeft().removeDecor(hitState.getMiddle());
					world.playSound(player, new BlockPos(hitState.getRight()), SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 1, 1);
				}
			}

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}
