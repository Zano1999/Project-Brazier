package net.dark_roleplay.projectbrazier.experimental_features.decorator.packets;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public abstract class DecorPacket {
	private ResourceLocation dimensionName;
	private BlockPos chunkPos;

	public DecorPacket(){}

	public DecorPacket(ResourceLocation dimensionName, DecorChunk decor, BlockPos chunkPos) {
		this.dimensionName = dimensionName;
		this.chunkPos = chunkPos;
	}

	public ResourceLocation getDimensionName() {
		return dimensionName;
	}

	public BlockPos getChunkPos() {
		return chunkPos;
	}

	public void setDimensionName(ResourceLocation dimensionName) {
		this.dimensionName = dimensionName;
	}

	public void setChunkPos(BlockPos chunkPos) {
		this.chunkPos = chunkPos;
	}
}
