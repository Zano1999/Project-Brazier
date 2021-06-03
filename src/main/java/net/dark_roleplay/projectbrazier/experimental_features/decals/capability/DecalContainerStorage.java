package net.dark_roleplay.projectbrazier.experimental_features.decals.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class DecalContainerStorage implements Capability.IStorage<DecalContainer>{
	@Nullable
	@Override
	public INBT writeNBT(Capability<DecalContainer> capability, DecalContainer instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<DecalContainer> capability, DecalContainer instance, Direction side, INBT nbt) {
		instance.deserializeNBT((ListNBT) nbt);
	}
}

