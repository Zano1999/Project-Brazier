package net.dark_roleplay.medieval.objects.packets;

import net.dark_roleplay.library.networking.SimplePacket;
import net.dark_roleplay.medieval.holders.MedievalConfigs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DodgePacket extends SimplePacket<DodgePacket> {

    private float radianDirection = 0f;

    public DodgePacket(){
        this(0F);
    }

    public DodgePacket(float radianDirection){
        this.radianDirection = radianDirection;
    }


    @Override
    public void encode(DodgePacket dodgePacket, PacketBuffer packetBuffer) {
        packetBuffer.writeFloat(this.radianDirection);
    }

    @Override
    public DodgePacket decode(PacketBuffer packetBuffer) {
        return new DodgePacket(packetBuffer.readFloat());
    }

    @Override
    public void onMessage(DodgePacket dodgePacket, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayerEntity player = context.getSender();

        //TODO Validate if possible

        if(!MedievalConfigs.SKILL_CONFIG.ALLOOW_AIRBORNE.get() && player.onGround){
            dash(player, player.getEntityWorld());
        }else{
            dash(player, player.getEntityWorld());
        }
    }

    private void dash(PlayerEntity player, World world){
        player.fallDistance = 0;
        world.playSound(null, player.getPosition(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.1F, 2);
    }
}
