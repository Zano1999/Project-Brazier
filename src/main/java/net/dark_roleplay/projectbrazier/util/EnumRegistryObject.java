package net.dark_roleplay.projectbrazier.util;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.EnumMap;
import java.util.Map;

public class EnumRegistryObject<E extends Enum<E>, T extends IForgeRegistryEntry<? super T>> {

	private Map<E, RegistryObject<T>> objects;

	public EnumRegistryObject(Class<E> clazz){
		this.objects = new EnumMap<>(clazz);
	}

	public void register(E key, RegistryObject<T> obj){
		objects.put(key, obj);
	}

	public RegistryObject<T> getRegistryObject(E key){
		return objects.get(key);
	}

	public T get(E key){
		RegistryObject<T> val = objects.get(key);
		return val == null ? null : val.get();
	}
}
