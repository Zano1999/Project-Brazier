package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorItem;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.feature.blocks.HangingItemBlock;
import net.dark_roleplay.projectbrazier.feature.blocks.NailBlock;
import net.dark_roleplay.projectbrazier.feature.items.PlantSeedsItem;
import net.dark_roleplay.projectbrazier.feature.items.PlatformBlockItem;
import net.dark_roleplay.projectbrazier.feature.items.SpyglassItem;
import net.dark_roleplay.projectbrazier.feature.items.WarHornItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BrazierItems extends Registrar {

	public static final RegistryObject<Item>
//			BARLEY = registerItem("barley"),
//			DOUGH = registerItem("dough"),
//			FLOUR = registerItem("flour"),
//			BAT_EAR = registerItem("bat_ear"),
//			BAT_WING = registerItem("bat_wing"),
//			WOLF_FUR = registerItem("wolf_fur"),
//			CUT_GRASS = registerItem("cut_grass"),
//			HAY = registerItem("hay"),
			BRONZE_COIN = registerItem("bronze_coin"),
			GOLD_COIN = registerItem("gold_coin"),
			SILVER_COIN = registerItem("silver_coin"),
			COPPER_COIN = registerItem("copper_coin"),
//			BELL_PEPPER = registerItem("bell_pepper"),
//			BLUEBERRIES = registerItem("blueberries"),
//			BUTTER = registerItem("butter"),
			CAULIFLOWER = registerItem("cauliflower"),
			WHITE_CABBAGE = registerItem("white_cabbage"),
//			EGGPLANT = registerItem("eggplant"),
//			GARLIC = registerItem("garlic"),
//			GREEN_APPLE = registerItem("green_apple"),
//			GREEN_CARAMELIZED_APPLE = registerItem("green_caramelized_apple"),
//			GREEN_PEAR = registerItem("green_pear"),
//			GRILLED_CATFISH = registerItem("grilled_catfish"),
//			GRILLED_WOLF = registerItem("grilled_wolf"),
//			HOPS = registerItem("hops"),
//			ONION = registerItem("onion"),
//			RAW_CATFISH = registerItem("raw_catfish"),
//			RAW_WOLF = registerItem("raw_wolf"),
//			RED_CARAMELIZED_APPLE = registerItem("red_caramelized_apple"),
//			RED_GRAPES = registerItem("red_grapes"),
//			TURNIP = registerItem("turnip"),
//			YELLOW_APPLE = registerItem("yellow_apple"),
//			YELLOW_CARAMELIZED_APPLE = registerItem("yellow_caramelized_apple"),
//			YELLOW_PEAR = registerItem("yellow_pear"),
			BONE_WAR_HORN = registerItem("bone_war_horn", WarHornItem::new),
			GOLD_SPYGLASS = registerItem("gold_spyglass", SpyglassItem::new),
			SILVER_SPYGLASS = registerItem("silver_spyglass", SpyglassItem::new),
			CAULIFLOWER_SEEDS = registerItem("cauliflower_seeds", prop -> new PlantSeedsItem(BrazierBlocks.CAULIFLOWER.get(), prop)),
			WHITE_CABBAGE_SEEDS = registerItem("white_cabbage_seeds", prop -> new PlantSeedsItem(BrazierBlocks.WHITE_CABBAGE.get(), prop)),
			STONE_ARROW_SLIT = registerItem("stone_arrow_slit", prop -> new SelectiveBlockItem(new Block[]{
					BrazierBlocks.V_STONE_BRICK_ARROW_SLIT.get(),
					BrazierBlocks.H_STONE_BRICK_ARROW_SLIT.get(),
					BrazierBlocks.C_STONE_BRICK_ARROW_SLIT.get()
			}, prop));

	//Experimental Items
//	public static final RegistryObject<Item>
//			DECOR_TEST	= registerItem("decor_test", DecorItem::new);

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

		Item.BY_BLOCK.put(BrazierBlocks.C_STONE_BRICK_ARROW_SLIT.get(), BrazierItems.STONE_ARROW_SLIT.get());
		Item.BY_BLOCK.put(BrazierBlocks.H_STONE_BRICK_ARROW_SLIT.get(), BrazierItems.STONE_ARROW_SLIT.get());
		Item.BY_BLOCK.put(BrazierBlocks.V_STONE_BRICK_ARROW_SLIT.get(), BrazierItems.STONE_ARROW_SLIT.get());

		Item.BY_BLOCK.put(BrazierBlocks.HANGING_GOLD_SPYGLASS.get(), BrazierBlocks.NAIL.get().asItem());
		Item.BY_BLOCK.put(BrazierBlocks.HANGING_SILVER_SPYGLASS.get(), BrazierBlocks.NAIL.get().asItem());
		Item.BY_BLOCK.put(BrazierBlocks.HANGING_HORN.get(), BrazierBlocks.NAIL.get().asItem());
	}
}
