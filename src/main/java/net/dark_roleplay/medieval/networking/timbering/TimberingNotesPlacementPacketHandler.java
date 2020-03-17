package net.dark_roleplay.medieval.networking.timbering;

import net.dark_roleplay.medieval.handler.MedievalBlocks;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberedClayState;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberingData;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.dark_roleplay.medieval.util.block_pos.BlockPosUtil;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TimberingNotesPlacementPacketHandler {

    public static void encode(TimberingNotesPlacementPacket packet, PacketBuffer buffer) {
        TimberingData data = packet.getData();

        buffer.writeBlockPos(data.getPosA());
        buffer.writeBlockPos(data.getPosB());
        TimberedClayState[][] states = data.getArea();


        for(int x = 0; x < states.length; x++){
            for(int y = 0; y < states[x].length; y++){
                if(states[x][y] == TimberedClayState.EMPTY) {
                    buffer.writeByte(0);
                    buffer.writeByte(0);
                }else {
                    TimberedClayState state = states[x][y];

                    buffer.writeByte(state.getPrimary().getID() + 1);
                    buffer.writeByte(state.getSecondary().getID() + 1);
                }
            }
        }
    }

    public static TimberingNotesPlacementPacket decode(PacketBuffer buffer) {
        BlockPos posA = buffer.readBlockPos();
        BlockPos posB = buffer.readBlockPos();

        TimberingData data = new TimberingData(posA, posB);

        data.calculateAxis();

        int horizontalSize = data.getAxis() == Direction.Axis.X ? data.getSelection().getWidth() : data.getSelection().getLength();

        TimberedClayState[][] states = new TimberedClayState[horizontalSize][data.getSelection().getHeight()];

        for(int x = 0; x < horizontalSize; x++){
            for(int y = 0; y < data.getSelection().getHeight(); y++){
                byte primary = buffer.readByte();
                byte secondary = buffer.readByte();

                if(primary == 0 || secondary == 0){
                    states[x][y] = TimberedClayState.EMPTY;
                }else{
                    states[x][y] = TimberedClayState.of(TimberedClayVariant.INSTANCES.get(primary-1), TimberedClayVariant.INSTANCES.get(secondary-1));
                }
            }
        }
        data.setArea(states);

        return new TimberingNotesPlacementPacket(data);
    }

    public static void onMessage(TimberingNotesPlacementPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        World world = context.getSender().getServerWorld();

        TimberingData data = packet.getData();

        TimberedClayState[][] area = data.getArea();

        BlockPosUtil.walkRegion(data.getPosA(), data.getPosB(), (pos, offset) -> {
            TimberedClayState state = area[data.getAxis() == Direction.Axis.X ? offset.getX() : offset.getZ()][data.getSelection().getHeight() - 1 - offset.getY()];
            if(state != TimberedClayState.EMPTY){
                BlockState blockState = state.toBlockState("oak");
                if(blockState.getBlock() != MedievalBlocks.TIMBERED_CLAY.get())
                    blockState = blockState.with(BlockStateProperties.HORIZONTAL_AXIS, data.getAxis());
                world.setBlockState(pos, blockState);
            }
        });
    }
}
