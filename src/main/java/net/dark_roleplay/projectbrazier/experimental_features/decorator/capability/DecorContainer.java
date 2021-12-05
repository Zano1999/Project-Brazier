package net.dark_roleplay.projectbrazier.experimental_features.decorator.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class DecorContainer implements INBTSerializable<ListTag> {
	private Map<Integer, DecorChunk> DECOR_CHUNKS = new TreeMap<>();

	public DecorChunk getDecorChunk(int chunkY, boolean create){
		return create ? DECOR_CHUNKS.computeIfAbsent(chunkY, y -> new DecorChunk(y)) : DECOR_CHUNKS.get(chunkY);
	}

	public void setDecorChunk(int y, DecorChunk chunk){
		DECOR_CHUNKS.put(y, chunk);
	}

	public Collection<DecorChunk> getSubChunks(){
		return DECOR_CHUNKS.values();
	}

	@Override
	public ListTag serializeNBT() {
		ListTag chunks = new ListTag();

		for(DecorChunk chunk : DECOR_CHUNKS.values())
			chunks.add(chunk.serializeNBT());

		return chunks;
	}

	@Override
	public void deserializeNBT(ListTag nbt) {
		for(INBT compound : nbt){
			DecorChunk chunk = new DecorChunk(0);
			chunk.deserializeNBT((CompoundTag) compound);
			DECOR_CHUNKS.put(chunk.getVertical(), chunk);
		}
	}
}
