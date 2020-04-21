package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.features.items.WarHornItem;
import net.dark_roleplay.medieval.features.items.ZoomItem;
import net.dark_roleplay.medieval.util.json.ItemPropertyLoader;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

public class MedievalItems {
	public static final DeferredRegister<Item> ITEMS = MedievalRegistries.ITEMS;

	public static final RegistryObject<Item>
			BARLEY = register("barley"),
			DOUGH = register("dough"),
			FLOUR = register("flour"),
			BAT_EAR = register("bat_ear"),
			BAT_WING = register("bat_wing"),
			WOLF_FUR = register("wolf_fur"),
			CUT_GRASS = register("cut_grass"),
			HAY = register("hay"),
			BRONZE_COIN = register("bronze_coin"),
			GOLD_COIN = register("gold_coin"),
			SILVER_COIN = register("silver_coin"),
			COPPER_COIN = register("copper_coin"),
			BELL_PEPPER = register("bell_pepper"),
			BLUEBERRIES = register("blueberries"),
			BUTTER = register("butter"),
			CAULIFLOWER = register("cauliflower"),
			EGGPLANT = register("eggplant"),
			GARLIC = register("garlic"),
			GREEN_APPLE = register("green_apple"),
			GREEN_CARAMELIZED_APPLE = register("green_caramelized_apple"),
			GREEN_PEAR = register("green_pear"),
			GRILLED_CATFISH = register("grilled_catfish"),
			GRILLED_WOLF = register("grilled_wolf"),
			HOPS = register("hops"),
			ONION = register("onion"),
			RAW_CATFISH = register("raw_catfish"),
			RAW_WOLF = register("raw_wolf"),
			RED_CARAMELIZED_APPLE = register("red_caramelized_apple"),
			RED_GRAPES = register("red_grapes"),
			TURNIP = register("turnip"),
			YELLOW_APPLE = register("yellow_apple"),
			YELLOW_CARAMELIZED_APPLE = register("yellow_caramelized_apple"),
			YELLOW_PEAR = register("yellow_pear"),
			BONE_WAR_HORN = register("bone_war_horn", WarHornItem::new),
			GOLD_SPYGLASS = register("gold_spyglass", ZoomItem::new),
			SILVER_SPYGLASS = register("silver_spyglass", ZoomItem::new);

	public static void registerItemBlocks(RegistryEvent.Register<Item> event){
		IForgeRegistry<Item> reg = event.getRegistry();
		for(RegistryObject<Block> regObj : MedievalBlocks.BLOCKS.getEntries()){
			BlockItem blockItem = new BlockItem(regObj.get(), new Item.Properties().group(MedievalCreativeTabs.decor()));
			reg.register(blockItem.setRegistryName(regObj.getId()));
		}
	}

	private static RegistryObject<Item> register(String name) {
		return register(name, Item::new);
	}

	private static RegistryObject<Item> register(String name, Function<Item.Properties, Item> suplier) {
		return ITEMS.register(name, () -> suplier.apply(ItemPropertyLoader.properties(name)));
	}
}