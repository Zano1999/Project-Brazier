package net.dark_roleplay.projectbrazier.experimental_features.decorator.capability;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorState;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashSet;
import java.util.Set;

public class DecorContainer {
	private Set<DecorState> STATES = new HashSet<>();

	public DecorState getDecor(int yPos){
		return new DecorState(DecorRegistrar.TEST, new Vector3d(0, 0, 0), Vector3d.ZERO);
	}
}
