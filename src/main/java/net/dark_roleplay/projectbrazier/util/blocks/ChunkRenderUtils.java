package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public class ChunkRenderUtils {

	public static void rerenderChunk(ClientWorld world, BlockPos pos){
		world.markSurroundingsForRerender(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
	}
}
