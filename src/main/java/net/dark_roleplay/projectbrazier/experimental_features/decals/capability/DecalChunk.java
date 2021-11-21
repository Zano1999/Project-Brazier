package net.dark_roleplay.projectbrazier.experimental_features.decals.capability;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.dark_roleplay.projectbrazier.experimental_features.decals.DecalState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class DecalChunk {
	private final DecalSubChunk[] subChunks;

	private final int verticalChunkOffset;
	private final int verticalSubChunkCount;
	public DecalChunk(int minHeight, int maxHeight){
		this.verticalChunkOffset = minHeight/16;
		this.verticalSubChunkCount = (maxHeight - minHeight)/16;
		this.subChunks = new DecalSubChunk[this.verticalSubChunkCount];
	}

	@Nullable
	public DecalState getDecalState(BlockPos position, Direction direction){
		DecalSubChunk subChunk = getSubChunk(position);
		return subChunk == null ? null : subChunk.getDecalState(position, direction);
	}

	public void setDecalState(DecalState state, BlockPos position, Direction direction){
		DecalSubChunk subChunk = getSubChunk(position);

	}

	@Nullable
	private DecalSubChunk getSubChunk(BlockPos pos){
		int subChunkID = pos.getY()/16 - verticalChunkOffset;
		if(subChunkID < 0 || subChunkID >= verticalSubChunkCount) return null;
		return subChunks[subChunkID];
	}

	private static class DecalSubChunk{
		private Int2ObjectMap<DecalState> decals;

		public DecalSubChunk(){
			this.decals = new Int2ObjectOpenHashMap<>();
		}

		@Nullable
		public DecalState getDecalState(BlockPos position, Direction direction){
			return decals.get(packPos(position, direction));
		}

		public void setDecalState(DecalState state, BlockPos position, Direction direction){
			decals.put(packPos(position, direction), state);
		}

		private int packPos(BlockPos position, Direction direction){
			return (position.getX() & 0xFF << 24) |
					(position.getY() & 0xFF << 16) |
					(position.getZ() & 0xFF << 8) |
					(direction.get3DDataValue());
		}
	}
}
