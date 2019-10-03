package net.dark_roleplay.tertiary_interactor.network;

import net.dark_roleplay.library.networking.SimplePacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TertiaryInteractionStopped extends SimplePacket<TertiaryInteractionStopped> {

    public TertiaryInteractionStopped(){}

    @Override
    public void encode(TertiaryInteractionStopped roadSignEditSignPacket, PacketBuffer packetBuffer) { }

    @Override
    public TertiaryInteractionStopped decode(PacketBuffer packetBuffer) {
        return new TertiaryInteractionStopped();
    }

    @Override
    public void onMessage(TertiaryInteractionStopped obj, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
//        if(obj.tePos.distanceSq(context.getSender().getPositionVec(), true) < 49){
//            TileEntity te = context.getSender().getEntityWorld().getTileEntity(obj.tePos);
//            if(te == null || !(te instanceof RoadSignTileEntity)) return;
//
//            RoadSignTileEntity rte = (RoadSignTileEntity) te;
//            rte.getSigns().get(obj.signID).setText(obj.newText);
//            rte.markDirty();
//            BlockState state = context.getSender().getEntityWorld().getBlockState(obj.tePos);
//            context.getSender().getEntityWorld().notifyBlockUpdate(obj.tePos, state, state, 2);
//        }
    }
}
