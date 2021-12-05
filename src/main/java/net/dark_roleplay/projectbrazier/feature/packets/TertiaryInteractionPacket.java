package net.dark_roleplay.projectbrazier.feature.packets;

import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TertiaryInteractionPacket {

	private BlockPos pos;

	public TertiaryInteractionPacket(){}

	public TertiaryInteractionPacket(BlockPos pos){
		this.pos = pos;
	}

	public static void encode(TertiaryInteractionPacket pkt, FriendlyByteBuf buffer){
		buffer.writeBlockPos(pkt.pos);
	}

	public static TertiaryInteractionPacket decode(FriendlyByteBuf buffer){
		TertiaryInteractionPacket pkt = new TertiaryInteractionPacket();
		pkt.pos = buffer.readBlockPos();

		return pkt;
	}

	public static void handle(TertiaryInteractionPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			ServerPlayer player = ctxSupplier.get().getSender();
			Level world = player.getLevel();
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
