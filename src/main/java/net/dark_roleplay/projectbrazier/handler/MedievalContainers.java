package net.dark_roleplay.projectbrazier.handler;

import net.dark_roleplay.projectbrazier.features.screens.general_container.GeneralContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;

public class MedievalContainers {
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = MedievalRegistries.CONTAINERS;
	public static final RegistryObject<ContainerType<GeneralContainer>> GENERAL_CONTAINER = CONTAINERS.register("general_container", () -> new ContainerType((IContainerFactory) GeneralContainer::new));
}
