package net.dark_roleplay.projectbrazier.util.data;

import com.mojang.math.Vector3f;
import net.minecraft.nbt.CompoundTag;

public class NBTUtil2 {
	public static CompoundTag writeVector3f(Vector3f vector){
		CompoundTag comp = new CompoundTag();
		comp.putFloat("x", vector.x());
		comp.putFloat("y", vector.y());
		comp.putFloat("z", vector.z());
		return comp;
	}

	public static Vector3f readVector3f(CompoundTag compound){
		return new Vector3f(compound.getFloat("x"), compound.getFloat("y"), compound.getFloat("z"));
	}
}
