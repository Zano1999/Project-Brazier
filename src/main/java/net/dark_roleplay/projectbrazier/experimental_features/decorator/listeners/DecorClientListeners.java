package net.dark_roleplay.projectbrazier.experimental_features.decorator.listeners;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.packets.DecorInitSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT)
public class DecorClientListeners {
	public static final Map<ChunkPos, DecorInitSyncPacket> SCHEDULED_PACKETS = new HashMap<>();

	@SubscribeEvent
	public static void chunkLoad(ChunkEvent.Load event){
		DecorInitSyncPacket packet = SCHEDULED_PACKETS.remove(event.getChunk().getPos());
		if(packet != null){
			Chunk chunk = Minecraft.getInstance().level.getChunk(packet.getChunkPos().getX() >> 4, packet.getChunkPos().getZ() >> 4);
			LazyOptional<DecorContainer> capability = chunk.getCapability(DecorRegistrar.DECOR);
			capability.ifPresent(decorCon -> {
				decorCon.setDecorChunk(packet.getChunkPos().getY() >> 4, packet.getDecor());
			});
		}
	}
}
