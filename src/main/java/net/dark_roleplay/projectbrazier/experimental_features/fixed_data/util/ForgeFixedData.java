package net.dark_roleplay.projectbrazier.experimental_features.fixed_data.util;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.*;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class ForgeFixedData<T extends IForgeRegistryEntry<T>> extends FixedData<T>{

	private IForgeRegistry<T> registry;
	private String namespace;

	public ForgeFixedData(IForgeRegistry<T> registry, String namespace){
		this.registry = registry;
		this.namespace = namespace;
	}

	protected abstract Set<T> loadObjects();

	public void registryListener(RegistryEvent.Register<T> event){
		IForgeRegistry<T> reg = event.getRegistry();
		for(T obj : loadObjects()){
			event.getRegistry().register(obj);
		}

		for (RegistryObject<T> obj : registryObjects)
			obj.updateReference(reg);
	}

	private final Set<RegistryObject<T>> registryObjects = new LinkedHashSet<>();

	public RegistryObject<T> get(String name){
		RegistryObject<T> robj = RegistryObject.of(new ResourceLocation(this.namespace, name), this.registry);
		registryObjects.add(robj);
		return robj;
	}
}
