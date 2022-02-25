package net.dark_roleplay.projectbrazier.experimental_features.chopping_block;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ChunkWatchListener {

	@SubscribeEvent
	public static void listenToChunk(ChunkWatchEvent.Watch event){
		LevelChunk chunk = event.getWorld().getChunk(event.getPos().x, event.getPos().z);
		chunk.getBlockEntities().forEach((pos, be) -> {
			if(be instanceof ChoppingBlockBlockEntity cbe){
				BrazierPackets.BLOCK_ENTITY.sendTo(new ChoppingBlockBlockEntitySync(cbe), event.getPlayer().connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
			}
		});
	}

}
