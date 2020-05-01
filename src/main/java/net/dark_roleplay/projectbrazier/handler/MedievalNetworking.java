package net.dark_roleplay.projectbrazier.handler;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MedievalNetworking {

    public static SimpleChannel CHANNEL;

    public static void initNetworking(){
        CHANNEL = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ProjectBrazier.MODID, "main_channel"))
                .clientAcceptedVersions("1.0"::equals)
                .serverAcceptedVersions("1.0"::equals)
                .networkProtocolVersion(() -> "1.0")
                .simpleChannel();

        registerPackets();
    }

    private static void registerPackets(){
    }
}
