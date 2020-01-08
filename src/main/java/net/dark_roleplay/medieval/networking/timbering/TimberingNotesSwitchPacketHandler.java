package net.dark_roleplay.medieval.networking.timbering;

import net.dark_roleplay.medieval.handler.MedievalItems;
import net.dark_roleplay.medieval.networking.sign_post.SignPostPlacementPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TimberingNotesSwitchPacketHandler {

    public static void encode(TimberingNotesSwitchPacket packet, PacketBuffer buffer) {}

    public static TimberingNotesSwitchPacket decode(PacketBuffer buffer) {
        return new TimberingNotesSwitchPacket();
    }

    public static void onMessage(TimberingNotesSwitchPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ItemStack heldItem = context.getSender().getHeldItemMainhand();
        if(heldItem.getItem() == MedievalItems.TIMBERING_NOTES.get()){
            CompoundNBT tag = heldItem.getOrCreateTag();
            tag.putBoolean("EditPosB", !tag.getBoolean("EditPosB"));
        }
    }
}
