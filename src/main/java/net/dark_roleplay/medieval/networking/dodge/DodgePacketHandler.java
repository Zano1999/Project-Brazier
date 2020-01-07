package net.dark_roleplay.medieval.networking.dodge;

import net.dark_roleplay.medieval.holders.MedievalConfigs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DodgePacketHandler {

    public static void encode(DodgePacket packet, PacketBuffer packetBuffer) {
        packetBuffer.writeFloat(packet.getRadianDirection());
    }

    public static DodgePacket decode(PacketBuffer buffer) {
        DodgePacket packet = new DodgePacket();
        return packet.setRadianDirection(buffer.readFloat());
    }

    public static void onMessage(DodgePacket dodgePacket, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayerEntity player = context.getSender();

        //TODO Validate if possible

        if(!MedievalConfigs.SKILL_CONFIG.ALLOW_AIRBORNE.get() && player.onGround){
            dash(player, player.getEntityWorld());
        }else{
            dash(player, player.getEntityWorld());
        }
    }

    private static void dash(PlayerEntity player, World world){
        player.fallDistance = 0;
        world.playSound(null, player.getPosition(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.1F, 2);
    }
}
