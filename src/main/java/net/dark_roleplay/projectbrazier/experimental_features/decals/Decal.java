package net.dark_roleplay.projectbrazier.experimental_features.decals;

import net.minecraft.state.StateContainer;
import net.minecraft.util.Util;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Decal extends ForgeRegistryEntry<Decal> {

	private DecalState defaultState;

	public Decal(){
		StateContainer.Builder<Decal, DecalState> builder = new StateContainer.Builder<>(this);
//		this.fillStateContainer(builder);
	}

	public String getTranslationKey() {
		return Util.makeTranslationKey("decal", this.getRegistryName());
	}

	public final DecalState getDefaultState() {
		return this.defaultState;
	}
}
