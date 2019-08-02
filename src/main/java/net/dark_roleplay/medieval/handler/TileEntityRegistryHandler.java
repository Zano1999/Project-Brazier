package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairTileEntity;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Bus.MOD)
public class TileEntityRegistryHandler {

	private static Map<Class<? extends TileEntity>, Set<Block>> teBlocks = new HashMap<Class<? extends TileEntity>, Set<Block>>();

	private static IForgeRegistry<TileEntityType<?>> registry = null;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<TileEntityType<?>> registryEvent) {
		registry = registryEvent.getRegistry();

		reg(createType(SolidChairTileEntity::new, SolidChairTileEntity.class), "solid_chair_armrest");
		reg(createType(RoadSignTileEntity::new, RoadSignTileEntity.class), "road_sign");
	}

	protected static <T extends TileEntity> TileEntityType createType(Supplier<T> supplier, Class<T> teClass){
		Set<Block> blocks = teBlocks.get(teClass);
		if(blocks != null)
			return TileEntityType.Builder.create(supplier, blocks.toArray(new Block[blocks.size()])).build(null);
		else
			return TileEntityType.Builder.create(supplier).build(null);
	}

	protected static void reg(TileEntityType<? extends TileEntity> tileEntity, String registryName) {
		tileEntity.setRegistryName(new ResourceLocation(DarkRoleplayMedieval.MODID, registryName));
		registry.register(tileEntity);
	}

	public static void addTileEntityBlock(Class<? extends TileEntity> teClass, Block b){
		Set<Block> blocks = teBlocks.get(teClass);
		if(blocks == null) {
			blocks = new HashSet<Block>();
			teBlocks.put(teClass, blocks);
		}

		blocks.add(b);
	}

	// GameRegistry.registerTileEntity(TileEntityRoof.class, new
	// ResourceLocation(References.MODID, "roof"));
	// GameRegistry.registerTileEntity(DynamicStorageTileEntity.class, new
	// ResourceLocation(References.MODID, "te_dynamic_storage"));
	// GameRegistry.registerTileEntity(TileEntityHoneyCentrifuge.class, new
	// ResourceLocation(References.MODID, "honey_centrifuge"));
	//
	//
	// //TODO Look trough this
	// //TODO Finally make new models!
	// GameRegistry.registerTileEntity(TileEntityAnvil.class, new
	// ResourceLocation(References.MODID, "TileEntityAnvil"));
	// GameRegistry.registerTileEntity(TileEntityGrindstone.class, new
	// ResourceLocation(References.MODID, "TileEntityGrindstone"));
	// GameRegistry.registerTileEntity(TileEntityHangingCauldron.class, new
	// ResourceLocation(References.MODID, "TileEntityHangingCauldron"));
	// GameRegistry.registerTileEntity(TileEntityBookOne.class, new
	// ResourceLocation(References.MODID, "TileEntityBookOne"));
	// GameRegistry.registerTileEntity(TileEntityCauldron.class, new
	// ResourceLocation(References.MODID, "TileEntityCauldron"));
	// GameRegistry.registerTileEntity(TileEntityChain.class, new
	// ResourceLocation(References.MODID, "TileEntityChain"));
	// GameRegistry.registerTileEntity(TileEntityHook.class, new
	// ResourceLocation(References.MODID, "TileEntityHook"));
	// GameRegistry.registerTileEntity(TileEntityKeyHanging.class, new
	// ResourceLocation(References.MODID, "TileEntityKeyHanging"));
	// GameRegistry.registerTileEntity(TileEntityTarget.class, new
	// ResourceLocation(References.MODID, "TileEntityTarget"));
	// GameRegistry.registerTileEntity(TileEntityRopeAnchor.class, new
	// ResourceLocation(References.MODID, "TileEntityRopeAnchor"));
	// GameRegistry.registerTileEntity(TileEntityFirepit.class, new
	// ResourceLocation(References.MODID, "TileEntityFirepit"));
	//
	// //New Storage
	// GameRegistry.registerTileEntity(TileEntityFluidBarrel.class, new
	// ResourceLocation(References.MODID, "tile_entity_fluid_barrel"));
	// GameRegistry.registerTileEntity(TileEntityShelf.class, new
	// ResourceLocation(References.MODID, "te_shelf"));
	// GameRegistry.registerTileEntity(TileEnittyUniversalShelf.class, new
	// ResourceLocation(References.MODID, "te_universal_shelf"));
	// GameRegistry.registerTileEntity(TileEntityChoppingBlock.class, new
	// ResourceLocation(References.MODID, "te_chopping_block"));
	//
	// GameRegistry.registerTileEntity(TileEntityClockCore.class, new
	// ResourceLocation(References.MODID, "te_clock_core"));
	// GameRegistry.registerTileEntity(TileEntityShopSign.class, new
	// ResourceLocation(References.MODID,"te_shop_sign"));
	//
	// //Old Storage
	// GameRegistry.registerTileEntity(TileEntityCrate.class, new
	// ResourceLocation(References.MODID, "TilEntityCrate"));
	// GameRegistry.registerTileEntity(TileEntitySimpleChest.class, new
	// ResourceLocation(References.MODID, "TileEntityDungeonChest"));
	//
	// //New Test Things
	// GameRegistry.registerTileEntity(TileEntityFlowerContainer.class, new
	// ResourceLocation(References.MODID, "flower_container"));
}