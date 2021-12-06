package net.dark_roleplay.projectbrazier.experimental_features.decals;

import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.util.Util;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Decal extends ForgeRegistryEntry<Decal> {

	protected final StateDefinition<Decal, DecalState> stateContainer;
	private DecalState defaultState;

	public Decal(){
		StateDefinition.Builder<Decal, DecalState> builder = new StateDefinition.Builder<>(this);
		this.fillStateContainer(builder);
		this.stateContainer = builder.create(Decal::getDefaultState, DecalState::new);
	}

	protected void fillStateContainer(StateDefinition.Builder<Decal, DecalState> builder) {}

	public final DecalState getDefaultState() {
		return this.defaultState;
	}


	public String getTranslationKey() {
		return Util.makeDescriptionId("decal", this.getRegistryName());
	}

}
