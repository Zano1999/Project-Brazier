package net.dark_roleplay.medieval.handler_2;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.guis.generic_container.GenericContainer;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MedievalContainers {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, DarkRoleplayMedieval.MODID);

    public static final RegistryObject<ContainerType<?>> GENERIC_CONTAINER = CONTAINERS.register("generic_container", () -> new ContainerType(GenericContainer::new));

}
