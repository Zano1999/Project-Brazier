package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;

public class ChunkRenderUtils {

	public static void rerenderChunk(ClientLevel world, BlockPos pos){
		world.setSectionDirtyWithNeighbors(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
	}
}
