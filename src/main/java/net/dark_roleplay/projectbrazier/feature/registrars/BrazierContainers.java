package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.feature.containers.GeneralContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.IContainerFactory;

public class BrazierContainers{

	public static final RegistryObject<MenuType<GeneralContainer>> GENERAL_CONTAINER =
			Registrar.registerContainer("general_container", (IContainerFactory) GeneralContainer::new);

	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {}
}
