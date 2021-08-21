package net.dark_roleplay.projectbrazier.experimental_features.decorator.capability;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.Decor;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DecorChunk implements INBTSerializable<CompoundNBT> {

	private int y;
	private Set<DecorState> STATES = new HashSet<>();

	public DecorChunk(int y){
		this.y = y;
	}

	public void addDecor(DecorState decor){
		STATES.add(decor);
	}

	public void removeDecor(DecorState decor) {
		STATES.remove(decor);
	}

	@Override
	public CompoundNBT serializeNBT() {
		List<Decor> palette = new ArrayList<>();

		CompoundNBT chunk = new CompoundNBT();
		ListNBT states = new ListNBT();
		ListNBT paletteTag = new ListNBT();

		for(DecorState state : STATES){
			CompoundNBT stateTag = state.serialize();
			if(!palette.contains(state.getDecor())) palette.add(state.getDecor());
			stateTag.putInt("decor", palette.indexOf(state.getDecor()));
			states.add(stateTag);
		}

		for(Decor decor : palette)
			paletteTag.add(StringNBT.valueOf(decor.getRegistryName().toString()));

		chunk.putInt("y", this.y);

		chunk.put("palette", paletteTag);
		chunk.put("states", states);

		return chunk;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		List<Decor> palette = new ArrayList<>();

		ListNBT paletteTag = nbt.getList("palette", Constants.NBT.TAG_STRING);
		for(int i = 0; i < paletteTag.size(); i ++)
			palette.add(DecorRegistrar.REGISTRY.getValue(new ResourceLocation(paletteTag.getString(i))));

		this.y = nbt.getInt("y");

		ListNBT statesTag = nbt.getList("states", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < statesTag.size(); i++){
			CompoundNBT state = statesTag.getCompound(i);
			DecorState decorState = new DecorState(palette.get(state.getInt("decor")));
			decorState.deserialize(state);
			STATES.add(decorState);
		}
	}

	public Set<DecorState> getDecors() {
		return STATES;
	}

	public int getY() {
		return y;
	}
}
