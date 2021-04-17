package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.blocks.HangingItemBlock;
import net.dark_roleplay.projectbrazier.feature.blocks.NailBlock;
import net.dark_roleplay.projectbrazier.feature.items.SpyglassItem;
import net.dark_roleplay.projectbrazier.feature.items.WarHornItem;
import net.dark_roleplay.projectbrazier.handler.MedievalBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BrazierItems extends Registrar {

	public static final RegistryObject<Item>
			BARLEY = registerItem("barley"),
			DOUGH = registerItem("dough"),
			FLOUR = registerItem("flour"),
			BAT_EAR = registerItem("bat_ear"),
			BAT_WING = registerItem("bat_wing"),
			WOLF_FUR = registerItem("wolf_fur"),
			CUT_GRASS = registerItem("cut_grass"),
			HAY = registerItem("hay"),
			BRONZE_COIN = registerItem("bronze_coin"),
			GOLD_COIN = registerItem("gold_coin"),
			SILVER_COIN = registerItem("silver_coin"),
			COPPER_COIN = registerItem("copper_coin"),
			BELL_PEPPER = registerItem("bell_pepper"),
			BLUEBERRIES = registerItem("blueberries"),
			BUTTER = registerItem("butter"),
			CAULIFLOWER = registerItem("cauliflower"),
			EGGPLANT = registerItem("eggplant"),
			GARLIC = registerItem("garlic"),
			GREEN_APPLE = registerItem("green_apple"),
			GREEN_CARAMELIZED_APPLE = registerItem("green_caramelized_apple"),
			GREEN_PEAR = registerItem("green_pear"),
			GRILLED_CATFISH = registerItem("grilled_catfish"),
			GRILLED_WOLF = registerItem("grilled_wolf"),
			HOPS = registerItem("hops"),
			ONION = registerItem("onion"),
			RAW_CATFISH = registerItem("raw_catfish"),
			RAW_WOLF = registerItem("raw_wolf"),
			RED_CARAMELIZED_APPLE = registerItem("red_caramelized_apple"),
			RED_GRAPES = registerItem("red_grapes"),
			TURNIP = registerItem("turnip"),
			YELLOW_APPLE = registerItem("yellow_apple"),
			YELLOW_CARAMELIZED_APPLE = registerItem("yellow_caramelized_apple"),
			YELLOW_PEAR = registerItem("yellow_pear"),
			BONE_WAR_HORN = registerItem("bone_war_horn", WarHornItem::new),
			GOLD_SPYGLASS = registerItem("gold_spyglass", SpyglassItem::new),
			SILVER_SPYGLASS = registerItem("silver_spyglass", SpyglassItem::new);

	public static void registerItemBlocks(RegistryEvent.Register<Item> event){
		IForgeRegistry<Item> reg = event.getRegistry();
		for(RegistryObject<Block> regObj : BrazierRegistries.BLOCKS.getEntries()){
			BlockItem blockItem = new BlockItem(regObj.get(), new Item.Properties().group(BrazierCreativeTabs.decor()));
			reg.register(blockItem.setRegistryName(regObj.getId()));
		}

		for(MargMaterial material : BrazierBlocks.WOOD_PLATFORM_CON){
			BlockItem blockItem = new BlockItem(BrazierBlocks.TOP_WOOD_PLATFORM.get(material), new Item.Properties().group(BrazierCreativeTabs.decor()));
			reg.register(blockItem.setRegistryName(ProjectBrazier.MODID, material.getTextProvider().apply("${material}_platform")));
		}
	}

	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {
		NailBlock.HANGABLE_ITEMS.put(BONE_WAR_HORN.get(), (HangingItemBlock) BrazierBlocks.HANGING_HORN.get());
		NailBlock.HANGABLE_ITEMS.put(SILVER_SPYGLASS.get(), (HangingItemBlock) BrazierBlocks.HANGING_SILVER_SPYGLASS.get());
		NailBlock.HANGABLE_ITEMS.put(GOLD_SPYGLASS.get(), (HangingItemBlock) BrazierBlocks.HANGING_GOLD_SPYGLASS.get());
	}
}
