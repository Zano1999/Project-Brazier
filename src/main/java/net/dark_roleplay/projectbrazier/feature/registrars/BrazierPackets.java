package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.packets.DecorInitSyncPacket;
import net.dark_roleplay.projectbrazier.feature.packets.TertiaryInteractionPacket;
import net.dark_roleplay.projectbrazier.feature.packets.SyncDrawbridgeState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class BrazierPackets {

	public static SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(ProjectBrazier.MODID, "main_channel"))
			.clientAcceptedVersions("1.0"::equals)
			.serverAcceptedVersions("1.0"::equals)
			.networkProtocolVersion(() -> "1.0")
			.simpleChannel();

	public static void registerPackets() {
		CHANNEL.<SyncDrawbridgeState>registerMessage(0, SyncDrawbridgeState.class, SyncDrawbridgeState::encode, SyncDrawbridgeState::decode, SyncDrawbridgeState::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(1, DecorInitSyncPacket.class, DecorInitSyncPacket::encode, DecorInitSyncPacket::decode, DecorInitSyncPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(2, TertiaryInteractionPacket.class, TertiaryInteractionPacket::encode, TertiaryInteractionPacket::decode, TertiaryInteractionPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
	}
}
