package net.dark_roleplay.projectbrazier.util;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class EnumMaterialRegistryObject <E extends Enum<E>, T extends IForgeRegistryEntry<? super T>> {

	private Map<E, Map<MargMaterial, RegistryObject<T>>> objects;

	public EnumMaterialRegistryObject(Class<E> clazz){
		this.objects = new EnumMap<>(clazz);
	}

	public void register(E key, MargMaterial matKey, RegistryObject<T> obj){
		Map<MargMaterial, RegistryObject<T>> subObjects = objects.computeIfAbsent(key, key2 -> new HashMap <>());
		subObjects.put(matKey, obj);
	}

	public RegistryObject<T> getRegistryObject(E key, MargMaterial magKey){
		Map<MargMaterial, RegistryObject<T>> subObjects = objects.get(key);
		return subObjects == null ? null : subObjects.get(key);
	}

	public T get(E key, MargMaterial matKey){
		Map<MargMaterial, RegistryObject<T>> subObjects = objects.get(key);
		RegistryObject<T> val = subObjects == null ? null : subObjects.get(key);
		return val == null ? null : val.get();
	}
}
