package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.networking.dodge.DodgePacket;
import net.dark_roleplay.medieval.networking.dodge.DodgePacketHandler;
import net.dark_roleplay.medieval.networking.sign_post.SignPostEditPacket;
import net.dark_roleplay.medieval.networking.sign_post.SignPostEditPacketHandler;
import net.dark_roleplay.medieval.networking.sign_post.SignPostPlacementPacket;
import net.dark_roleplay.medieval.networking.sign_post.SignPostPlacementPacketHandler;
import net.dark_roleplay.medieval.networking.timbering.TimberingNotesPlacementPacket;
import net.dark_roleplay.medieval.networking.timbering.TimberingNotesPlacementPacketHandler;
import net.dark_roleplay.medieval.networking.timbering.TimberingNotesSwitchPacket;
import net.dark_roleplay.medieval.networking.timbering.TimberingNotesSwitchPacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MedievalNetworking {

    public static SimpleChannel CHANNEL;

    public static void initNetworking(){
        CHANNEL = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(DarkRoleplayMedieval.MODID, "main_channel"))
                .clientAcceptedVersions("1.0"::equals)
                .serverAcceptedVersions("1.0"::equals)
                .networkProtocolVersion(() -> "1.0")
                .simpleChannel();

        registerPackets();
    }

    private static void registerPackets(){
        CHANNEL.registerMessage(0, DodgePacket.class, DodgePacketHandler::encode, DodgePacketHandler::decode, DodgePacketHandler::onMessage);
        CHANNEL.registerMessage(1, SignPostEditPacket.class, SignPostEditPacketHandler::encode, SignPostEditPacketHandler::decode, SignPostEditPacketHandler::onMessage);
        CHANNEL.registerMessage(2, SignPostPlacementPacket.class, SignPostPlacementPacketHandler::encode, SignPostPlacementPacketHandler::decode, SignPostPlacementPacketHandler::onMessage);
        CHANNEL.registerMessage(3, TimberingNotesSwitchPacket.class, TimberingNotesSwitchPacketHandler::encode, TimberingNotesSwitchPacketHandler::decode, TimberingNotesSwitchPacketHandler::onMessage);
        CHANNEL.registerMessage(4, TimberingNotesPlacementPacket.class, TimberingNotesPlacementPacketHandler::encode, TimberingNotesPlacementPacketHandler::decode, TimberingNotesPlacementPacketHandler::onMessage);
    }
}
