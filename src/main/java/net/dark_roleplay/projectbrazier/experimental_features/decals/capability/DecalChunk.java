package net.dark_roleplay.projectbrazier.experimental_features.decals.capability;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.dark_roleplay.projectbrazier.experimental_features.decals.decal.DecalState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public class DecalChunk implements INBTSerializable<CompoundNBT> {

	private int yCoordinate;
	private Int2ObjectMap<DecalState> STATES = new Int2ObjectOpenHashMap<>();

	public DecalChunk(int y){
		this.yCoordinate = y;
	}

	public void setDecal(BlockPos pos, Direction dir, DecalState decal){
		STATES.put(packPos(pos, dir), decal);
	}

	@Override
	public CompoundNBT serializeNBT() {
		return new CompoundNBT();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {}

	public Int2ObjectMap<DecalState> getDecals() {
		return STATES;
	}

	public int getVertical() {
		return yCoordinate;
	}

	private int packPos(BlockPos position, Direction direction){
		return (position.getX() & 0xFF << 24) |
				(position.getY() & 0xFF << 16) |
				(position.getZ() & 0xFF << 8) |
				(direction.getIndex());
	}
}
