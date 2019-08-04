package net.dark_roleplay.medieval.objects.packets;

import io.netty.buffer.ByteBufUtil;
import net.dark_roleplay.library.networking.SimplePacket;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RoadSignEditSignPacket extends SimplePacket<RoadSignEditSignPacket> {

    private int signID = 0;
    private BlockPos tePos = new BlockPos(0, 0, 0);
    private String newText = "";

    public RoadSignEditSignPacket(){}

    public RoadSignEditSignPacket(BlockPos pos,int signID, String newText){
        this.tePos = pos;
        this.signID = signID;
        this.newText = newText;
    }

    @Override
    public void encode(RoadSignEditSignPacket roadSignEditSignPacket, PacketBuffer packetBuffer) {
        packetBuffer.writeBlockPos(this.tePos);
        packetBuffer.writeInt(this.signID);
        packetBuffer.writeString(this.newText);
    }

    @Override
    public RoadSignEditSignPacket decode(PacketBuffer packetBuffer) {
        return new RoadSignEditSignPacket(packetBuffer.readBlockPos(), packetBuffer.readInt(), packetBuffer.readString());
    }

    @Override
    public void onMessage(RoadSignEditSignPacket obj, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        if(obj.tePos.distanceSq(context.getSender().getPositionVec(), true) < 49){
            TileEntity te = context.getSender().getEntityWorld().getTileEntity(obj.tePos);
            if(te == null || !(te instanceof RoadSignTileEntity)) return;

            RoadSignTileEntity rte = (RoadSignTileEntity) te;
            rte.getSigns().get(obj.signID).setText(obj.newText);
            rte.markDirty();
            BlockState state = context.getSender().getEntityWorld().getBlockState(obj.tePos);
            context.getSender().getEntityWorld().notifyBlockUpdate(obj.tePos, state, state, 2);
        }
    }
}
