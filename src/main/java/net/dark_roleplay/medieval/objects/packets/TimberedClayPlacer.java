package net.dark_roleplay.medieval.objects.packets;

import net.dark_roleplay.library.networking.SimplePacket;
import net.dark_roleplay.medieval.objects.guis.fourteen.TimberedArea;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TimberedClayPlacer extends SimplePacket<TimberedClayPlacer> {

    public TimberedClayPlacer(){}

    public TimberedClayPlacer(TimberedArea area){

    }

    @Override
    public void encode(TimberedClayPlacer timberedClayPlacer, PacketBuffer packetBuffer) {

    }

    @Override
    public TimberedClayPlacer decode(PacketBuffer packetBuffer) {
        return null;
    }

    @Override
    public void onMessage(TimberedClayPlacer timberedClayPlacer, Supplier<NetworkEvent.Context> supplier) {

    }
}
