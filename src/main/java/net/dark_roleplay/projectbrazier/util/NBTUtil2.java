package net.dark_roleplay.projectbrazier.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3f;

public class NBTUtil2 {

	public static CompoundNBT writeVector3f(Vector3f vector){
		CompoundNBT comp = new CompoundNBT();
		comp.putFloat("x", vector.getX());
		comp.putFloat("y", vector.getY());
		comp.putFloat("z", vector.getZ());
		return comp;
	}

	public static Vector3f readVector3f(CompoundNBT compound){
		return new Vector3f(compound.getFloat("x"), compound.getFloat("y"), compound.getFloat("z"));
	}
}
