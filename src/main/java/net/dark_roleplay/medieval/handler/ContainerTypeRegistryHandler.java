package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.guis.generic_container.GenericContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Bus.MOD)
public class ContainerTypeRegistryHandler {


	private static IForgeRegistry<ContainerType<?>> registry = null;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<ContainerType<?>> registryEvent) {
		registry = registryEvent.getRegistry();

		reg(new ContainerType<GenericContainer>(GenericContainer::new), "generic_container");
	}

	protected static void reg(ContainerType<? extends Container> container, String registryName) {
		container.setRegistryName(new ResourceLocation(DarkRoleplayMedieval.MODID, registryName));
		registry.register(container);
	}
	
}
