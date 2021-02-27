package net.dark_roleplay.projectbrazier.handler;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.features.blocks.barrel.BarrelTileEntity;
import net.dark_roleplay.projectbrazier.features.blocks.drawbridge.DrawbridgeAnchorTileEntity;
import net.dark_roleplay.projectbrazier.features.blocks.flowerpot.FlowerContainerTileEntity;
import net.dark_roleplay.projectbrazier.features.tileentities.SingleItemTileEntity;
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
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ProjectBrazier.MODID);

	public static final RegistryObject<TileEntityType<SingleItemTileEntity>>
			SINGLE_ITEM_STORAGE = TILE_ENTITIES.register("single_item_storage", () -> createType(SingleItemTileEntity::new,
			MedievalBlocks.HANGING_HORN
	));

	public static final RegistryObject<TileEntityType<DrawbridgeAnchorTileEntity>>
			DRAWBRODGE_ANCHOR = TILE_ENTITIES.register("drawbridge_anchor", () -> createType(DrawbridgeAnchorTileEntity::new, MedievalBlocks.DRAWBRIDGE_ANCHOR));


	public static final RegistryObject<TileEntityType<BarrelTileEntity>>
			BARREL = TILE_ENTITIES.register("barrel", () -> createType(BarrelTileEntity::new, MedievalBlocks.OPEN_BARREL.values(), MedievalBlocks.CLOSED_BARREL.values()));

	public static final RegistryObject<TileEntityType<FlowerContainerTileEntity>>
			FLOWER_CONTAINER = TILE_ENTITIES.register("flower_container", () -> createType(FlowerContainerTileEntity::new, MedievalBlocks.FLOWER_BUCKET.values(), MedievalBlocks.FLOWER_BARREL.values()));

	protected static <T extends TileEntity> TileEntityType<T> createType(Supplier<T> supplier, RegistryObject<Block>... blocks) {
		return TileEntityType.Builder.create(supplier, Arrays.stream(blocks).map(ro -> ro.get()).collect(Collectors.toList()).toArray(new Block[blocks.length])).build(null);
	}

	protected static <T extends TileEntity> TileEntityType<T> createType(Supplier<T> supplier, Collection<RegistryObject<Block>>... blocks) {
		return TileEntityType.Builder.create(supplier, Arrays.stream(blocks).flatMap(col -> col.stream()).map(RegistryObject::get).toArray(i -> new Block[i])).build(null);
	}
}
