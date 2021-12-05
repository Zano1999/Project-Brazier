package net.dark_roleplay.projectbrazier.util;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MaterialRegistryObject<T extends IForgeRegistryEntry<? super T>> {

	private Map<MargMaterial, RegistryObject<T>> objects;

	public MaterialRegistryObject(){
		this.objects = new HashMap<>();
	}

	public void register(MargMaterial key, RegistryObject<T> obj){
		objects.put(key, obj);
	}

	public RegistryObject<T> getRegistryObject(MargMaterial key){
		return objects.get(key);
	}

	public T get(MargMaterial key){
		RegistryObject<T> val = objects.get(key);
		return val == null ? null : val.get();
	}

	public Collection<RegistryObject<T>> values(){
		return objects.values();
	}

	public RegistryObject<T> getObject(MargMaterial key){
		return objects.get(key);
	}
}
