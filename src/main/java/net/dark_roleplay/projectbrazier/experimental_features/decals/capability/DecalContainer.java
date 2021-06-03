package net.dark_roleplay.projectbrazier.experimental_features.decals.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class DecalContainer implements INBTSerializable<ListNBT> {
	private Map<Integer, DecalChunk> DECOR_CHUNKS = new TreeMap<>();

	public DecalChunk getDecalChunk(int chunkY, boolean create){
		return create ? DECOR_CHUNKS.computeIfAbsent(chunkY, y -> new DecalChunk(y)) : DECOR_CHUNKS.get(chunkY);
	}

	public void setDecalChunk2(int y, DecalChunk chunk){
		DECOR_CHUNKS.put(y, chunk);
	}

	public Collection<DecalChunk> getSubChunks(){
		return DECOR_CHUNKS.values();
	}

	@Override
	public ListNBT serializeNBT() {
		ListNBT chunks = new ListNBT();

		for(DecalChunk chunk : DECOR_CHUNKS.values())
			chunks.add(chunk.serializeNBT());

		return chunks;
	}

	@Override
	public void deserializeNBT(ListNBT nbt) {
		for(INBT compound : nbt){
			DecalChunk chunk = new DecalChunk(0);
			chunk.deserializeNBT((CompoundNBT) compound);
			DECOR_CHUNKS.put(chunk.getVertical(), chunk);
		}
	}
}
