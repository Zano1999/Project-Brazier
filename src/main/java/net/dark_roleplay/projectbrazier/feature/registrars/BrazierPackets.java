package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.chopping_block.ChoppingBlockBlockEntitySync;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItemPacket;
import net.dark_roleplay.projectbrazier.feature.packets.TertiaryInteractionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class BrazierPackets {

	public static SimpleChannel BLOCK_ENTITY = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(ProjectBrazier.MODID, "block_entities"))
			.clientAcceptedVersions("1.0"::equals)
			.serverAcceptedVersions("1.0"::equals)
			.networkProtocolVersion(() -> "1.0")
			.simpleChannel();

	public static SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(ProjectBrazier.MODID, "main_channel"))
			.clientAcceptedVersions("1.0"::equals)
			.serverAcceptedVersions("1.0"::equals)
			.networkProtocolVersion(() -> "1.0")
			.simpleChannel();

	public static void registerPackets() {
//		CHANNEL.<SyncDrawbridgeState>registerMessage(0, SyncDrawbridgeState.class, SyncDrawbridgeState::encode, SyncDrawbridgeState::decode, SyncDrawbridgeState::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(0, TertiaryInteractionPacket.class, TertiaryInteractionPacket::encode, TertiaryInteractionPacket::decode, TertiaryInteractionPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(1, SelectiveBlockItemPacket.class, SelectiveBlockItemPacket::encode, SelectiveBlockItemPacket::decode, SelectiveBlockItemPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));

		BLOCK_ENTITY.registerMessage(0, ChoppingBlockBlockEntitySync.class, ChoppingBlockBlockEntitySync::encode, ChoppingBlockBlockEntitySync::decode, ChoppingBlockBlockEntitySync::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}
}
