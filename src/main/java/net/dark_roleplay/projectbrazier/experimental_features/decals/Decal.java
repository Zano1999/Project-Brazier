package net.dark_roleplay.projectbrazier.experimental_features.decals;

import net.minecraft.state.StateContainer;
import net.minecraft.util.Util;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Decal extends ForgeRegistryEntry<Decal> {

	protected final StateContainer<Decal, DecalState> stateContainer;
	private DecalState defaultState;

	public Decal(){
		StateContainer.Builder<Decal, DecalState> builder = new StateContainer.Builder<>(this);
		this.fillStateContainer(builder);
		this.stateContainer = builder.func_235882_a_(Decal::getDefaultState, DecalState::new);
	}

	protected void fillStateContainer(StateContainer.Builder<Decal, DecalState> builder) {}

	public final DecalState getDefaultState() {
		return this.defaultState;
	}


	public String getTranslationKey() {
		return Util.makeTranslationKey("decal", this.getRegistryName());
	}

}
