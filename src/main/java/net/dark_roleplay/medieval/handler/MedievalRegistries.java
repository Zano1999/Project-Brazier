package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MedievalRegistries {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DarkRoleplayMedieval.MODID);
	public static final DeferredRegister<Block> BLOCKS_NO_ITEMS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DarkRoleplayMedieval.MODID);
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DarkRoleplayMedieval.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister(ForgeRegistries.ENTITIES, DarkRoleplayMedieval.MODID);
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, DarkRoleplayMedieval.MODID);
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, DarkRoleplayMedieval.MODID);
	public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, DarkRoleplayMedieval.MODID);
}
