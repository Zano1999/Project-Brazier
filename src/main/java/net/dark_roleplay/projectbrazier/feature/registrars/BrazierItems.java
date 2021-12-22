package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.feature.blocks.HangingItemBlock;
import net.dark_roleplay.projectbrazier.feature.blocks.NailBlock;
import net.dark_roleplay.projectbrazier.feature.items.PlantSeedsItem;
import net.dark_roleplay.projectbrazier.feature.items.PlatformBlockItem;
import net.dark_roleplay.projectbrazier.feature.items.SpyglassItem;
import net.dark_roleplay.projectbrazier.feature.items.WarHornItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BrazierItems {

	public static final RegistryObject<Item>
//			BARLEY = Registrar.registerItem("barley"),
//			DOUGH = Registrar.registerItem("dough"),
//			FLOUR = Registrar.registerItem("flour"),
//			BAT_EAR = Registrar.registerItem("bat_ear"),
//			BAT_WING = Registrar.registerItem("bat_wing"),
//			WOLF_FUR = Registrar.registerItem("wolf_fur"),
//			CUT_GRASS = Registrar.registerItem("cut_grass"),
//			HAY = Registrar.registerItem("hay"),
			BRONZE_COIN = Registrar.registerItem("bronze_coin"),
			GOLD_COIN = Registrar.registerItem("gold_coin"),
			SILVER_COIN = Registrar.registerItem("silver_coin"),
			COPPER_COIN = Registrar.registerItem("copper_coin"),
			LUMP_OF_DRY_CLAY = Registrar.registerItem("lump_of_dry_clay"),
//			BELL_PEPPER = Registrar.registerItem("bell_pepper"),
//			BLUEBERRIES = Registrar.registerItem("blueberries"),
//			BUTTER = Registrar.registerItem("butter"),
			CAULIFLOWER = Registrar.registerItem("cauliflower"),
			WHITE_CABBAGE = Registrar.registerItem("white_cabbage"),
//			EGGPLANT = Registrar.registerItem("eggplant"),
//			GARLIC = Registrar.registerItem("garlic"),
//			GREEN_APPLE = Registrar.registerItem("green_apple"),
//			GREEN_CARAMELIZED_APPLE = Registrar.registerItem("green_caramelized_apple"),
//			GREEN_PEAR = Registrar.registerItem("green_pear"),
//			GRILLED_CATFISH = Registrar.registerItem("grilled_catfish"),
//			GRILLED_WOLF = Registrar.registerItem("grilled_wolf"),
//			HOPS = Registrar.registerItem("hops"),
//			ONION = Registrar.registerItem("onion"),
//			RAW_CATFISH = Registrar.registerItem("raw_catfish"),
//			RAW_WOLF = Registrar.registerItem("raw_wolf"),
//			RED_CARAMELIZED_APPLE = Registrar.registerItem("red_caramelized_apple"),
//			RED_GRAPES = Registrar.registerItem("red_grapes"),
//			TURNIP = Registrar.registerItem("turnip"),
//			YELLOW_APPLE = Registrar.registerItem("yellow_apple"),
//			YELLOW_CARAMELIZED_APPLE = Registrar.registerItem("yellow_caramelized_apple"),
//			YELLOW_PEAR = Registrar.registerItem("yellow_pear"),
			BONE_WAR_HORN = Registrar.registerItem("bone_war_horn", WarHornItem::new),
			GOLD_SPYGLASS = Registrar.registerItem("gold_spyglass", SpyglassItem::new),
			SILVER_SPYGLASS = Registrar.registerItem("silver_spyglass", SpyglassItem::new),
			CAULIFLOWER_SEEDS = Registrar.registerItem("cauliflower_seeds", prop -> new PlantSeedsItem(BrazierBlocks.CAULIFLOWER.get(), prop)),
			WHITE_CABBAGE_SEEDS = Registrar.registerItem("white_cabbage_seeds", prop -> new PlantSeedsItem(BrazierBlocks.WHITE_CABBAGE.get(), prop)),
			STONE_ARROW_SLIT = Registrar.registerItem("stone_arrow_slit", prop -> new SelectiveBlockItem(new Block[]{
					BrazierBlocks.V_STONE_BRICK_ARROW_SLIT.get(),
					BrazierBlocks.H_STONE_BRICK_ARROW_SLIT.get(),
					BrazierBlocks.C_STONE_BRICK_ARROW_SLIT.get()
			}, prop));

	//Experimental Items
//	public static final RegistryObject<Item>
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
