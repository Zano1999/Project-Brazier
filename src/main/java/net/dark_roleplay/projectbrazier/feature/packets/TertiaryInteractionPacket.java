package net.dark_roleplay.projectbrazier.feature.packets;

import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TertiaryInteractionPacket {

	private BlockPos pos;

	public TertiaryInteractionPacket(){}

	public TertiaryInteractionPacket(BlockPos pos){
		this.pos = pos;
	}

	public static void encode(TertiaryInteractionPacket pkt, PacketBuffer buffer){
		buffer.writeBlockPos(pkt.pos);
	}

	public static TertiaryInteractionPacket decode(PacketBuffer buffer){
		TertiaryInteractionPacket pkt = new TertiaryInteractionPacket();
		pkt.pos = buffer.readBlockPos();

		return pkt;
	}

	public static void handle(TertiaryInteractionPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctxSupplier.get().getSender();
			World world = player.getLevel();
			BlockState state = world.getBlockState(pkt.pos);
			if(state.getBlock() instanceof ITertiaryInteractor){
				ITertiaryInteractor block = (ITertiaryInteractor) state.getBlock();
				if(block.hasInteraction(world, pkt.pos, state, player)){
					block.executeInteraction(world, pkt.pos, state, player);
				}
			}

			ctxSupplier.get().setPacketHandled(true);
		});
	}
}
