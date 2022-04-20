package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.experimental_features.chopping_block.ChoppingBlockBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blockentities.BarrelBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blockentities.FlowerContainerBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blockentities.HangingItemBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blockentities.ZiplineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BrazierBlockEntities{

	public static final RegistryObject<BlockEntityType<HangingItemBlockEntity>>
			SINGLE_ITEM_STORAGE = Registrar.registerBlockEntity("hanging_item", HangingItemBlockEntity::new, BrazierBlocks.HANGING_HORN, BrazierBlocks.HANGING_SILVER_SPYGLASS , BrazierBlocks.HANGING_GOLD_SPYGLASS);

	public static final RegistryObject<BlockEntityType<BarrelBlockEntity>>
			BARREL_BLOCK_ENTITY = Registrar.registerBlockEntity("barrel", BarrelBlockEntity::new, BrazierBlocks.OPEN_BARRELS.values(), BrazierBlocks.CLOSED_BARRELS.values());

//	public static final RegistryObject<BlockEntityType<DrawbridgeAnchorTileEntity>>
//			DRAWBRODGE_ANCHOR = null;// = registerBlockEntity("drawbridge_anchor", DrawbridgeAnchorTileEntity::new, BrazierBlocks.DRAWBRIDGE_ANCHOR);

	public static final RegistryObject<BlockEntityType<FlowerContainerBlockEntity>>
			FLOWER_CONTAINER = Registrar.registerBlockEntity("flower_container", FlowerContainerBlockEntity::new, BrazierBlocks.FLOWER_BUCKET.values(), BrazierBlocks.FLOWER_BARRELS.values());

	public static final RegistryObject<BlockEntityType<ZiplineBlockEntity>>
			ZIPLINE_ANCHOR = Registrar.registerBlockEntity("zipline_anchor", ZiplineBlockEntity::new, BrazierBlocks.ZIPLINE_ANCHOR.values());


//	public static final RegistryObject<BlockEntityType<ChoppingBlockBlockEntity>>
//			CHOPPING_BLOCK = Registrar.registerBlockEntity("chopping_block", ChoppingBlockBlockEntity::new, BrazierBlocks.CHOPPING_BLOCK.values());


	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event){}
}
