package net.dark_roleplay.projectbrazier.experimental_features.decorator.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;
import java.util.TreeMap;

public class DecorContainer implements INBTSerializable<CompoundNBT> {
	private Map<Integer, DecorChunk> DECOR_CHUNKS = new TreeMap<>();

	public DecorChunk getDecorChunk(int chunkY){
		return DECOR_CHUNKS.computeIfAbsent(chunkY, y -> new DecorChunk(y));
	}

	@Override
	public CompoundNBT serializeNBT() {
		return null;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {

	}
}
