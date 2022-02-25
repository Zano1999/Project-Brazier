package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.items.ItemFixedDataRegistry;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.feature.blocks.HangingItemBlock;
import net.dark_roleplay.projectbrazier.feature.blocks.NailBlock;
import net.dark_roleplay.projectbrazier.feature.items.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Map;

public class BrazierItems {
	private static final ItemFixedDataRegistry ITEMS = BrazierRegistries.ITEMS_FD;

	public static final RegistryObject<Item>
			BRONZE_COIN = ITEMS.get("bronze_coin"),
			GOLD_COIN = ITEMS.get("gold_coin"),
			SILVER_COIN = ITEMS.get("silver_coin"),
			COPPER_COIN = ITEMS.get("copper_coin"),
			CAULIFLOWER = ITEMS.get("cauliflower"),
			WHITE_CABBAGE = ITEMS.get("white_cabbage"),
			ROPE = ITEMS.get("rope"),
			LUMP_OF_DRY_CLAY = ITEMS.get("lump_of_dry_clay"),
			BONE_WAR_HORN = ITEMS.get("bone_war_horn"),
			GOLD_SPYGLASS = ITEMS.get("gold_spyglass"),
			SILVER_SPYGLASS = ITEMS.get("silver_spyglass"),
			CAULIFLOWER_SEEDS = ITEMS.get("cauliflower_seeds"),
			WHITE_CABBAGE_SEEDS = ITEMS.get("white_cabbage_seeds"),
			STONE_ARROW_SLIT = ITEMS.get("stone_arrow_slit"),
			DEEPSLATE_ARROW_SLIT = ITEMS.get("deepslate_arrow_slit");



	//Experimental Items
//	public static final RegistryObject<Item>
//			CRYSTALIZED_POTION = Registrar.registerItem("crystalized_potion", CrystalizedPotionItem::new);
//			DECOR_TEST	= Registrar.registerItem("decor_test", DecorItem::new);

	public static void registerItemBlocks(RegistryEvent.Register<Item> event){
		IForgeRegistry<Item> reg = event.getRegistry();
		for(RegistryObject<Block> regObj : BrazierRegistries.BLOCKS.getEntries()){
			BlockItem blockItem = new BlockItem(regObj.get(), new Item.Properties().tab(BrazierCreativeTabs.decor()));
			reg.register(blockItem.setRegistryName(regObj.getId()));
		}

		for(MargMaterial material : BrazierBlocks.WOOD_PLATFORM_CON){
			PlatformBlockItem blockItem = new PlatformBlockItem(new Block[]{
					BrazierBlocks.TOP_WOOD_PLATFORM.get(material),
					BrazierBlocks.TOP_WOOD_PLATFORM_STAIRS.get(material)
			},new Block[]{
					BrazierBlocks.BOTTOM_WOOD_PLATFORM.get(material),
					BrazierBlocks.BOTTOM_WOOD_PLATFORM_STAIRS.get(material)
			}, new Item.Properties().tab(BrazierCreativeTabs.decor()));
			reg.register(blockItem.setRegistryName(ProjectBrazier.MODID, material.getTextProvider().apply("${material}_platform")));

			Map<Block, Item> BY_BLOCK = Item.BY_BLOCK;

			Item.BY_BLOCK.put(BrazierBlocks.TOP_WOOD_PLATFORM.get(material), blockItem);
			Item.BY_BLOCK.put(BrazierBlocks.BOTTOM_WOOD_PLATFORM.get(material), blockItem);
			Item.BY_BLOCK.put(BrazierBlocks.TOP_WOOD_PLATFORM_STAIRS.get(material), blockItem);
			Item.BY_BLOCK.put(BrazierBlocks.BOTTOM_WOOD_PLATFORM_STAIRS.get(material), blockItem);
		}
	}

	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {
		NailBlock.HANGABLE_ITEMS.put(BONE_WAR_HORN.get(), (HangingItemBlock) BrazierBlocks.HANGING_HORN.get());
		NailBlock.HANGABLE_ITEMS.put(SILVER_SPYGLASS.get(), (HangingItemBlock) BrazierBlocks.HANGING_SILVER_SPYGLASS.get());
		NailBlock.HANGABLE_ITEMS.put(GOLD_SPYGLASS.get(), (HangingItemBlock) BrazierBlocks.HANGING_GOLD_SPYGLASS.get());

		Map<Block, Item> BY_BLOCK = Item.BY_BLOCK;

		Item.BY_BLOCK.put(BrazierBlocks.HANGING_GOLD_SPYGLASS.get(), BrazierBlocks.NAIL.get().asItem());
		Item.BY_BLOCK.put(BrazierBlocks.HANGING_SILVER_SPYGLASS.get(), BrazierBlocks.NAIL.get().asItem());
		Item.BY_BLOCK.put(BrazierBlocks.HANGING_HORN.get(), BrazierBlocks.NAIL.get().asItem());
	}
}
