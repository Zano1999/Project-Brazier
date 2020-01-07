package net.dark_roleplay.medieval.networking.sign_post;

import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SignPostEditPacketHandler {

    public static void encode(SignPostEditPacket packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.getTileEntityPosition());
        buffer.writeInt(packet.getSignID());
        buffer.writeString(packet.getNewText());
    }

    public static SignPostEditPacket decode(PacketBuffer packetBuffer) {
        SignPostEditPacket packet = new SignPostEditPacket();

        return packet.setTileEntityPosition(packetBuffer.readBlockPos()).setSignID(packetBuffer.readInt()).setNewText(packetBuffer.readString());
    }

    public static void onMessage(SignPostEditPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        if(packet.getTileEntityPosition().distanceSq(context.getSender().getPositionVec(), true) < 49){
            TileEntity te = context.getSender().getEntityWorld().getTileEntity(packet.getTileEntityPosition());
            if(te == null || !(te instanceof RoadSignTileEntity)) return;

            RoadSignTileEntity rte = (RoadSignTileEntity) te;
            rte.getSigns().get(packet.getSignID()).setText(packet.getNewText());
            rte.markDirty();
            BlockState state = context.getSender().getEntityWorld().getBlockState(packet.getTileEntityPosition());
            context.getSender().getEntityWorld().notifyBlockUpdate(packet.getTileEntityPosition(), state, state, 2);
        }
    }

}
