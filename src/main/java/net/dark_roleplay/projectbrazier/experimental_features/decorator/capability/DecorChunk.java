package net.dark_roleplay.projectbrazier.experimental_features.decorator.capability;

import net.dark_roleplay.projectbrazier.experimental_features.decorator.Decor;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringNBT;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DecorChunk implements INBTSerializable<CompoundTag> {

	private int yCoordinate;
	private Set<DecorState> STATES = new HashSet<>();

	public DecorChunk(int y){
		this.yCoordinate = y;
	}

	public void addDecor(DecorState decor){
		STATES.add(decor);
	}

	@Override
	public CompoundTag serializeNBT() {
		List<Decor> palette = new ArrayList<>();

		CompoundTag chunk = new CompoundTag();
		ListTag states = new ListTag();
		ListTag paletteTag = new ListTag();

		for(DecorState state : STATES){
			CompoundTag stateTag = state.serialize();
			if(!palette.contains(state.getDecor())) palette.add(state.getDecor());
			stateTag.putInt("decor", palette.indexOf(state.getDecor()));
			states.add(stateTag);
		}

		for(Decor decor : palette)
			paletteTag.add(StringNBT.valueOf(decor.getRegistryName().toString()));

		chunk.put("palette", paletteTag);
		chunk.put("states", states);
		chunk.putInt("verticalPos", this.yCoordinate);

		return chunk;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		List<Decor> palette = new ArrayList<>();

		ListTag paletteTag = nbt.getList("palette", Constants.NBT.TAG_STRING);
		for(int i = 0; i < paletteTag.size(); i ++)
			palette.add(DecorRegistrar.REGISTRY.getValue(new ResourceLocation(paletteTag.getString(i))));

		this.yCoordinate = nbt.getInt("verticalPos");

		ListTag statesTag = nbt.getList("states", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < statesTag.size(); i++){
			CompoundTag state = statesTag.getCompound(i);
			DecorState decorState = new DecorState(palette.get(state.getInt("decor")));
			decorState.deserialize(state);
			STATES.add(decorState);
		}
	}

	public Set<DecorState> getDecors() {
		return STATES;
	}

	public int getVertical() {
		return yCoordinate;
	}
}
