package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.experimental_features.crafting.containers.CraftingScreenPlayerContainer;
import net.dark_roleplay.projectbrazier.feature.containers.GeneralContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.IContainerFactory;

public class BrazierContainers extends Registrar {

	public static final RegistryObject<MenuType<GeneralContainer>> GENERAL_CONTAINER =
			registerContainer("general_container", (IContainerFactory) GeneralContainer::new);

	public static final RegistryObject<MenuType<CraftingScreenPlayerContainer>> CRAFTING_PLAYER_CONTAINER =
			registerContainer("crafting_player", (IContainerFactory) CraftingScreenPlayerContainer::new);

	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {}
}
