package net.dark_roleplay.medieval.objects.packets;

import net.dark_roleplay.library.networking.SimplePacket;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RoadSignPlacementPacket extends SimplePacket<RoadSignPlacementPacket> {

    public static Map<PlayerEntity, Boolean> players = new HashMap<>();

    private boolean isRight = false;

    public RoadSignPlacementPacket(){}

    public RoadSignPlacementPacket(boolean isRight){
        this.isRight = isRight;
    }

    @Override
    public void encode(RoadSignPlacementPacket obj, PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(RoadSignHelper.INSTANCE.isRight());

    }

    @Override
    public RoadSignPlacementPacket decode(PacketBuffer packetBuffer) {
        return new RoadSignPlacementPacket(packetBuffer.readBoolean());
    }

    @Override
    public void onMessage(RoadSignPlacementPacket o, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        players.put(context.getSender(), o.isRight);
    }
}
