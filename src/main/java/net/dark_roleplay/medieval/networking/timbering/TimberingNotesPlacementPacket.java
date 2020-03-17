package net.dark_roleplay.medieval.networking.timbering;

import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberedClayState;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberingData;
import net.minecraft.util.math.BlockPos;

public class TimberingNotesPlacementPacket {

    private TimberingData data;

    public TimberingNotesPlacementPacket(TimberingData data){
        this.data = data;
    }

    public TimberingData getData() {
        return data;
    }
}
