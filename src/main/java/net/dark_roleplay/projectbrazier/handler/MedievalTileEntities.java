package net.dark_roleplay.projectbrazier.handler;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.features.tile_entities.SingleItemTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MedievalTileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ProjectBrazier.MODID);

	public static final RegistryObject<TileEntityType<?>>
			SINGLE_ITEM_STORAGE = TILE_ENTITIES.register("single_item_storage", () -> createType(SingleItemTileEntity::new,
			MedievalBlocks.HANGING_HORN
	));


	protected static <T extends TileEntity> TileEntityType<T> createType(Supplier<T> supplier, RegistryObject<Block>... blocks) {
		return TileEntityType.Builder.create(supplier, Arrays.stream(blocks).map(ro -> ro.get()).collect(Collectors.toList()).toArray(new Block[blocks.length])).build(null);
	}

	protected static <T extends TileEntity> TileEntityType<T> createType(Supplier<T> supplier, Collection<RegistryObject<Block>> blocks) {
		return TileEntityType.Builder.create(supplier, blocks.stream().map(ro -> ro.get()).collect(Collectors.toList()).toArray(new Block[blocks.size()])).build(null);
	}
}
