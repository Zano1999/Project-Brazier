package net.dark_roleplay.projectbrazier.experimental_features.decorator.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class DecorContainerStorage implements Capability.IStorage<DecorContainer>{
	@Nullable
	@Override
	public INBT writeNBT(Capability<DecorContainer> capability, DecorContainer instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<DecorContainer> capability, DecorContainer instance, Direction side, INBT nbt) {
		instance.deserializeNBT((ListTag) nbt);
	}
}
