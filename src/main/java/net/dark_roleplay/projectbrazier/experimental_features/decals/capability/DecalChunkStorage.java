package net.dark_roleplay.projectbrazier.experimental_features.decals.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class DecalChunkStorage implements Capability.IStorage<DecalChunk>{
	@Nullable
	@Override
	public INBT writeNBT(Capability<DecalChunk> capability, DecalChunk instance, Direction side) {
		return null;
	}

	@Override
	public void readNBT(Capability<DecalChunk> capability, DecalChunk instance, Direction side, INBT nbt) {

	}
}
