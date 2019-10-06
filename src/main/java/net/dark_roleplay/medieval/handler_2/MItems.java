package net.dark_roleplay.medieval.handler_2;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.holders.MedievalFoods;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.dark_roleplay.medieval.holders.MedievalCreativeTabs.DECORATION;
import static net.dark_roleplay.medieval.holders.MedievalCreativeTabs.FOOD;

public class MItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DarkRoleplayMedieval.MODID);

    private static final Item.Properties foodProperties = new Item.Properties().group(FOOD);
    private static final Item.Properties decoProperties = new Item.Properties().group(DECORATION);

    public static final RegistryObject<Item>
        BELL_PEPPER = ITEMS.register("bell_pepepr", () -> new Item(foodProperties.food(MedievalFoods.BELL_PEPPER))),
        BLUEBERRIES = ITEMS.register("blueberries", () -> new Item(foodProperties.food(MedievalFoods.BLUEBERRIES))),
        BUTTER = ITEMS.register("butter", () -> new Item(foodProperties.food(MedievalFoods.BUTTER))),
        CAULIFLOWER = ITEMS.register("cauliflower", () -> new Item(foodProperties.food(MedievalFoods.CAULIFLOWER))),
        EGGPLANT = ITEMS.register("eggplant", () -> new Item(foodProperties.food(MedievalFoods.EGGPLANT))),
        GARLIC = ITEMS.register("garlic", () -> new Item(foodProperties.food(MedievalFoods.GARLIC))),
        GREEN_APPLE = ITEMS.register("green_apple", () -> new Item(foodProperties.food(Foods.APPLE))),
        GREEN_CARAMELIZED_APPLE = ITEMS.register("green_caramelized_apple", () -> new Item(foodProperties.food(MedievalFoods.CARAMELIZED_APPLE))),
        GREEN_PEAR = ITEMS.register("green_pear", () -> new Item(foodProperties.food(MedievalFoods.PEAR))),
        GRILLED_CATFISH = ITEMS.register("grilled_catfish", () -> new Item(foodProperties.food(MedievalFoods.GRILLED_CATFISH))),
        GRILLED_WOLF = ITEMS.register("grilled_wolf", () -> new Item(foodProperties.food(MedievalFoods.GRILLED_WOLF))),
        HONEY_COMB = ITEMS.register("honey_comb", () -> new Item(foodProperties.food(MedievalFoods.HONEY_COMB))),
        HOPS = ITEMS.register("hops", () -> new Item(foodProperties.food(MedievalFoods.HOPS))),
        ONION = ITEMS.register("onion", () -> new Item(foodProperties.food(MedievalFoods.ONION))),
        RED_CARAMELIZED_APPLE = ITEMS.register("red_caramelized_apple", () -> new Item(foodProperties.food(MedievalFoods.CARAMELIZED_APPLE))),
        RAW_CATFISH = ITEMS.register("raw_catfish", () -> new Item(foodProperties.food(MedievalFoods.RAW_CATFISH))),
        RAW_WOLF = ITEMS.register("raw_wolf", () -> new Item(foodProperties.food(MedievalFoods.RAW_WOLF))),
        RED_GRAPES = ITEMS.register("red_grapes", () -> new Item(foodProperties.food(MedievalFoods.GRAPES))),
        TURNIP = ITEMS.register("turnip", () -> new Item(foodProperties.food(MedievalFoods.TURNIP))),
        YELLOW_APPLE = ITEMS.register("yellow_apple", () -> new Item(foodProperties.food(Foods.APPLE))),
        YELLOW_CARAMELIZED_APPLE = ITEMS.register("yellow_caramelized_apple", () -> new Item(foodProperties.food(MedievalFoods.CARAMELIZED_APPLE))),
        YELLOW_PEAR = ITEMS.register("yellow_pear", () -> new Item(foodProperties.food(MedievalFoods.PEAR))),

        TORCH_HOLDER = ITEMS.register("torch_holder", () -> new BlockItem(MBlocks.TORCH_HOLDER.get(), decoProperties)),
        WALL_BRAZIER = ITEMS.register("wall_brazier", () -> new BlockItem(MBlocks.WALL_BRAZIER.get(), decoProperties)),
        JAIL_LATTICE = ITEMS.register("jail_lattice", () -> new BlockItem(MBlocks.JAIL_LATTICE.get(), decoProperties)),
        RIVERSTONE = ITEMS.register("riverstone", () -> new BlockItem(MBlocks.RIVERSTONE.get(), decoProperties)),
        LARGE_RIVERSTONE = ITEMS.register("large_riverstone", () -> new BlockItem(MBlocks.LARGE_RIVERSTONE.get(), decoProperties)),
        LARGE_DARK_RIFVERSTONE = ITEMS.register("large_dark_riverstone", () -> new BlockItem(MBlocks.LARGE_DARK_RIFVERSTONE.get(), decoProperties)),
        COLORFUL_COBBLESTONE = ITEMS.register("colorful_cobblestone", () -> new BlockItem(MBlocks.COLORFUL_COBBLESTONE.get(), decoProperties)),
        PALE_COLORFUL_COBBLESTONE = ITEMS.register("pale_colorful_cobblestone", () -> new BlockItem(MBlocks.PALE_COLORFUL_COBBLESTONE.get(), decoProperties)),
        TIMBERED_CLAY = ITEMS.register("clean_timbered_clay", () -> new BlockItem(MBlocks.TIMBERED_CLAY.get(), decoProperties)),
        ROPE_ANCHOR = ITEMS.register("rope_anchor", () -> new BlockItem(MBlocks.ROPE_ANCHOR.get(), decoProperties)),
        OAK_ROPE_LADDER_ANCHOR = ITEMS.register("oak_rope_ladder_anchor", () -> new BlockItem(MBlocks.OAK_ROPE_LADDER_ANCHOR.get(), decoProperties)),
        OAK_ROPE_LADDER = ITEMS.register("oak_rope_ladder", () -> new BlockItem(MBlocks.OAK_ROPE_LADDER.get(), decoProperties));
}
