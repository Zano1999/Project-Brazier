package net.dark_roleplay.projectbrazier.experimental_features.decorator.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class DecorContainerStorage implements Capability.IStorage<DecorContainer>{
	@Nullable
	@Override
	public INBT writeNBT(Capability<DecorContainer> capability, DecorContainer instance, Direction side) {
		return null;
	}

	@Override
	public void readNBT(Capability<DecorContainer> capability, DecorContainer instance, Direction side, INBT nbt) {

	}
}
