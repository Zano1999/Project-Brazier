package net.dark_roleplay.medieval.networking.sign_post;

import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SignPostPlacementPacketHandler {

    public static void encode(SignPostPlacementPacket packet, PacketBuffer buffer) {
        buffer.writeBoolean(RoadSignHelper.INSTANCE.isRight());

    }

    public static SignPostPlacementPacket decode(PacketBuffer buffer) {
        SignPostPlacementPacket packet = new SignPostPlacementPacket();

        return packet.setRight(buffer.readBoolean());
    }

    public static void onMessage(SignPostPlacementPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        packet.setPlayer(context.getSender(), packet.isRight());
    }
}
