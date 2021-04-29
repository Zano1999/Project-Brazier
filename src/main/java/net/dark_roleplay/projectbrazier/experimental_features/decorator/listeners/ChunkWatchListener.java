package net.dark_roleplay.projectbrazier.experimental_features.decorator.listeners;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.packets.DecorInitSyncPacket;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID)
public class ChunkWatchListener {
	//ChunkWatchEvent.Watch

	@SubscribeEvent
	public static void mouseScroll(ChunkWatchEvent.Watch event) {
		Chunk chunk = event.getWorld().getChunk(event.getPos().x, event.getPos().z);
		LazyOptional<DecorContainer> capability = chunk.getCapability(DecorRegistrar.DECOR);
		capability.ifPresent(decorCon -> {
			Collection<DecorChunk> subchunks = decorCon.getSubChunks();
			for (DecorChunk decorChunk : subchunks)
				BrazierPackets.CHANNEL.sendTo(
						new DecorInitSyncPacket(
								event.getWorld().getDimensionKey().getRegistryName(),
								decorChunk,
								new BlockPos(chunk.getPos().x << 4, decorChunk.getVertical() << 4, chunk.getPos().z << 4)
						),
						event.getPlayer().connection.getNetworkManager(),
						NetworkDirection.PLAY_TO_CLIENT);
		});
	}
}
