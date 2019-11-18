package net.dark_roleplay.medieval.handler_2;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.api.materials.MaterialRequirement;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.building.roofs.RoofItem;
import net.dark_roleplay.medieval.objects.items.equipment.misc.RoadSignItem;
import net.dark_roleplay.medieval.objects.items.equipment.tools.ItemTelescope;
import net.dark_roleplay.medieval.objects.items.equipment.tools.RoofersNotes;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.dark_roleplay.medieval.handler_2.MedievalCreativeTabs.*;

public class MedievalItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DarkRoleplayMedieval.MODID);

    private static final Item.Properties foodProperties = new Item.Properties().group(FOOD);
    private static final Item.Properties decoProperties = new Item.Properties().group(DECORATION);
    private static final Item.Properties miscProperties = new Item.Properties().group(MISCELLANEOUS);
    private static final Item.Properties equipProperties = new Item.Properties().group(EQUIPMENT).maxStackSize(1);
    private static final Item.Properties utilityProperties = new Item.Properties().group(UTILITY);
    private static final Item.Properties buildProperties = new Item.Properties().group(BUILDING);

    private static final MaterialRequirement logMat = new MaterialRequirement("wood", "log", "log_top");
    private static final MaterialRequirement plankMat = new MaterialRequirement("wood", "planks");

    public static final RegistryObject<Item>
        BELL_PEPPER                 = ITEMS.register("bell_pepper", () -> new Item(foodProperties.food(MedievalFoods.BELL_PEPPER))),
        BLUEBERRIES                 = ITEMS.register("blueberries", () -> new Item(foodProperties.food(MedievalFoods.BLUEBERRIES))),
        BUTTER                      = ITEMS.register("butter", () -> new Item(foodProperties.food(MedievalFoods.BUTTER))),
        CAULIFLOWER                 = ITEMS.register("cauliflower", () -> new Item(foodProperties.food(MedievalFoods.CAULIFLOWER))),
        EGGPLANT                    = ITEMS.register("eggplant", () -> new Item(foodProperties.food(MedievalFoods.EGGPLANT))),
        GARLIC                      = ITEMS.register("garlic", () -> new Item(foodProperties.food(MedievalFoods.GARLIC))),
        GREEN_APPLE                 = ITEMS.register("green_apple", () -> new Item(foodProperties.food(Foods.APPLE))),
        GREEN_CARAMELIZED_APPLE     = ITEMS.register("green_caramelized_apple", () -> new Item(foodProperties.food(MedievalFoods.CARAMELIZED_APPLE))),
        GREEN_PEAR                  = ITEMS.register("green_pear", () -> new Item(foodProperties.food(MedievalFoods.PEAR))),
        GRILLED_CATFISH             = ITEMS.register("grilled_catfish", () -> new Item(foodProperties.food(MedievalFoods.GRILLED_CATFISH))),
        GRILLED_WOLF                = ITEMS.register("grilled_wolf", () -> new Item(foodProperties.food(MedievalFoods.GRILLED_WOLF))),
        HONEY_COMB                  = ITEMS.register("honey_comb", () -> new Item(foodProperties.food(MedievalFoods.HONEY_COMB))),
        HOPS                        = ITEMS.register("hops", () -> new Item(foodProperties.food(MedievalFoods.HOPS))),
        ONION                       = ITEMS.register("onion", () -> new Item(foodProperties.food(MedievalFoods.ONION))),
        RED_CARAMELIZED_APPLE       = ITEMS.register("red_caramelized_apple", () -> new Item(foodProperties.food(MedievalFoods.CARAMELIZED_APPLE))),
        RAW_CATFISH                 = ITEMS.register("raw_catfish", () -> new Item(foodProperties.food(MedievalFoods.RAW_CATFISH))),
        RAW_WOLF                    = ITEMS.register("raw_wolf", () -> new Item(foodProperties.food(MedievalFoods.RAW_WOLF))),
        RED_GRAPES                  = ITEMS.register("red_grapes", () -> new Item(foodProperties.food(MedievalFoods.GRAPES))),
        TURNIP                      = ITEMS.register("turnip", () -> new Item(foodProperties.food(MedievalFoods.TURNIP))),
        YELLOW_APPLE                = ITEMS.register("yellow_apple", () -> new Item(foodProperties.food(Foods.APPLE))),
        YELLOW_CARAMELIZED_APPLE    = ITEMS.register("yellow_caramelized_apple", () -> new Item(foodProperties.food(MedievalFoods.CARAMELIZED_APPLE))),
        YELLOW_PEAR                 = ITEMS.register("yellow_pear", () -> new Item(foodProperties.food(MedievalFoods.PEAR))),
        SPRUCE_TEA                  = ITEMS.register("spruce_tea", () -> new Item(foodProperties.food(MedievalFoods.SPRUCE_TEA))),//TODO Custom Item

        TORCH_HOLDER                = ITEMS.register("torch_holder", () -> new BlockItem(MedievalBlocks.TORCH_HOLDER.get(), decoProperties)),
        ADVENT_WREATH               = ITEMS.register("advent_wreath", () -> new BlockItem(MedievalBlocks.ADVENT_WREATH.get(), decoProperties)),
        WALL_BRAZIER                = ITEMS.register("wall_brazier", () -> new BlockItem(MedievalBlocks.WALL_BRAZIER.get(), decoProperties)),
        JAIL_LATTICE                = ITEMS.register("jail_lattice", () -> new BlockItem(MedievalBlocks.JAIL_LATTICE.get(), decoProperties)),
        RIVERSTONE                  = ITEMS.register("riverstone", () -> new BlockItem(MedievalBlocks.RIVERSTONE.get(), buildProperties)),
        LARGE_RIVERSTONE            = ITEMS.register("large_riverstone", () -> new BlockItem(MedievalBlocks.LARGE_RIVERSTONE.get(), buildProperties)),
        LARGE_DARK_RIFVERSTONE      = ITEMS.register("dark_large_riverstone", () -> new BlockItem(MedievalBlocks.DARK_LARGE_RIVERSTONE.get(), buildProperties)),
        COLORFUL_COBBLESTONE        = ITEMS.register("colorful_cobblestone", () -> new BlockItem(MedievalBlocks.COLORFUL_COBBLESTONE.get(), buildProperties)),
        PALE_COLORFUL_COBBLESTONE   = ITEMS.register("pale_colorful_cobblestone", () -> new BlockItem(MedievalBlocks.PALE_COLORFUL_COBBLESTONE.get(), buildProperties)),
        TIMBERED_CLAY               = ITEMS.register("clean_timbered_clay", () -> new BlockItem(MedievalBlocks.TIMBERED_CLAY.get(), buildProperties)),
        ROPE_ANCHOR                 = ITEMS.register("rope_anchor", () -> new BlockItem(MedievalBlocks.ROPE_ANCHOR.get(), decoProperties)),
        OAK_ROPE_LADDER_ANCHOR      = ITEMS.register("oak_rope_ladder_anchor", () -> new BlockItem(MedievalBlocks.OAK_ROPE_LADDER_ANCHOR.get(), decoProperties)),
        OAK_ROPE_LADDER             = ITEMS.register("oak_rope_ladder", () -> new BlockItem(MedievalBlocks.OAK_ROPE_LADDER.get(), decoProperties)),

        BARLEY                      = ITEMS.register("barley", () -> new Item(miscProperties)),
        BARLEY_DOUGH                = ITEMS.register("barley_dough", () -> new Item(miscProperties)),
        BARLEY_FLOUR                = ITEMS.register("barley_flour", () -> new Item(miscProperties)),
        BAT_EAR                     = ITEMS.register("bat_ear", () -> new Item(miscProperties)),
        BAT_WING                    = ITEMS.register("bat_wing", () -> new Item(miscProperties)),
        BRONZE_COIN                 = ITEMS.register("bronze_coin", () -> new Item(miscProperties)),
        CHARCOAL_POWDER             = ITEMS.register("charcoal_powder", () -> new Item(miscProperties)),
        COPPER_COIN                 = ITEMS.register("copper_coin", () -> new Item(miscProperties)),
        COPPER_ORE_CHUNK            = ITEMS.register("copper_ore_chunk", () -> new Item(miscProperties)),
        CUT_GRASS                   = ITEMS.register("cut_grass", () -> new Item(miscProperties)),
        DRY_CLAY_CHUNK              = ITEMS.register("dry_clay_chunk", () -> new Item(miscProperties)),
        GOLD_COIN                   = ITEMS.register("gold_coin", () -> new Item(miscProperties)),
        HARDENED_LEATHER            = ITEMS.register("hardened_leather", () -> new Item(miscProperties)),
        HARDENED_LEATHER_STRIP      = ITEMS.register("hardened_leather_strip", () -> new Item(miscProperties)),
        HAY                         = ITEMS.register("hay", () -> new Item(miscProperties)),
        LEATHER_BOOK_COVER          = ITEMS.register("leather_book_cover", () -> new Item(miscProperties)),
        LEATHER_STRIP               = ITEMS.register("leather_strip", () -> new Item(miscProperties)),
        SALPETER_ORE_CHUNK          = ITEMS.register("salpeter_ore_chunk", () -> new Item(miscProperties)),
        SILVER_COIN                 = ITEMS.register("silver_coin", () -> new Item(miscProperties)),
        SILVER_ORE_CHUNK            = ITEMS.register("silver_ore_chunk", () -> new Item(miscProperties)),
        SULFUR_ORE_CHUNK            = ITEMS.register("sulfur_ore_chunk", () -> new Item(miscProperties)),
        THICK_LEATHER_BOOK_COVER    = ITEMS.register("thick_leather_book_cover", () -> new Item(miscProperties)),
        THIN_LEATHER_BOOK_COVER     = ITEMS.register("thin_leather_book_cover", () -> new Item(miscProperties)),
        TIN_ORE_CHUNK               = ITEMS.register("tin_ore_chunk", () -> new Item(miscProperties)),
        TRIGGER_TRAP                = ITEMS.register("trigger_trap", () -> new Item(miscProperties)),
        WHEAT_DOUGH                 = ITEMS.register("wheat_dough", () -> new Item(miscProperties)),
        WHEAT_FLOUR                 = ITEMS.register("wheat_flour", () -> new Item(miscProperties)),
        WOLF_FUR                    = ITEMS.register("wolf_fur", () -> new Item(miscProperties)),
        WOODEN_CUP                  = ITEMS.register("wooden_cup", () -> new Item(miscProperties)),

        BONE_WAR_HORN               = ITEMS.register("bone_war_horn", () -> new Item(equipProperties)),
        CLEAN_PAINTBRUSH            = ITEMS.register("clean_paintbrush", () -> new Item(equipProperties)),
        DIRTY_PAINTBRUSH            = ITEMS.register("dirty_paintbrush", () -> new Item(equipProperties)),
        GOLDEN_TELESCOPE            = ITEMS.register("golden_telescope", () -> new ItemTelescope(equipProperties)),
        SILVER_TELESCOPE            = ITEMS.register("silver_telescope", () -> new ItemTelescope(equipProperties)),
        STONE_STREET_STOMPER        = ITEMS.register("stone_street_stomper", () -> new Item(equipProperties)),
        WOODEN_KEY                  = ITEMS.register("wooden_key", () -> new Item(equipProperties)),
        WOODEN_LOCK                 = ITEMS.register("wooden_lock", () -> new Item(equipProperties)),
        WOODEN_STREET_STOMPER       = ITEMS.register("wooden_street_stomper", () -> new Item(equipProperties)),
        WOODEN_WRENCH               = ITEMS.register("wooden_wrench", () -> new Item(equipProperties)),
        ROOFERS_NOTES               = ITEMS.register("roofers_notes", () -> new RoofersNotes(equipProperties));



    public static final Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Item>>
        WOOD_BEAMS                  = materialRegister(plankMat, "${material}_wood_beam", () -> new Item(miscProperties)),
        PLANKS                      = materialRegister(plankMat, "${material}_plank", () -> new Item(miscProperties)),
        FIREWOOD                    = materialRegister(logMat, "${material}_firewood", () -> new Item(miscProperties)),
        SIMPLE_ROAD_SIGN            = materialRegister(plankMat, "simple_${material}_road_sign", material -> () -> new RoadSignItem(miscProperties, material,"drpmedieval:other/simple_${material}_road_sign_%s"));

    public static final Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Item>>
        SHINGLE_ROOF                = materialRegisterBlocks(MedievalBlocks.SHINGLE_ROOF, block -> new RoofItem(block.get(), buildProperties)),
        CHOPPING_BLOCK              = materialRegisterBlocks(MedievalBlocks.CHOPPING_BLOCK, block -> new BlockItem(block.get(), utilityProperties)),
        SIMPLE_WOOD_STAIRS          = materialRegisterBlocks(MedievalBlocks.SIMPLE_WOOD_STAIRS, block -> new BlockItem(block.get(), buildProperties)),
        SOLID_WOOD_TABLE            = materialRegisterBlocks(MedievalBlocks.SOLID_WOOD_TABLE, block -> new BlockItem(block.get(), decoProperties)),
        WOOD_PLANK_CHAIR            = materialRegisterBlocks(MedievalBlocks.WOOD_PLANK_CHAIR, block -> new BlockItem(block.get(), decoProperties)),
        WOOD_SOLID_CHAIR            = materialRegisterBlocks(MedievalBlocks.WOOD_SOLID_CHAIR, block -> new BlockItem(block.get(), decoProperties)),
        WOOD_SOLID_ARMREST_CHAIR    = materialRegisterBlocks(MedievalBlocks.WOOD_SOLID_ARMREST_CHAIR, block -> new BlockItem(block.get(), decoProperties)),
        WOOD_SOLID_BENCH            = materialRegisterBlocks(MedievalBlocks.WOOD_SOLID_BENCH, block -> new BlockItem(block.get(), decoProperties)),
        WOOD_PLATFORM               = materialRegisterBlocks(MedievalBlocks.WOOD_PLATFORM, block -> new BlockItem(block.get(), buildProperties)),
        WOOD_PLATFORM_STAIRS        = materialRegisterBlocks(MedievalBlocks.WOOD_PLATFORM_STAIRS, block -> new BlockItem(block.get(), buildProperties)),
        WOOD_ROAD_SIGN              = materialRegisterBlocks(MedievalBlocks.WOOD_ROAD_SIGN, block -> new BlockItem(block.get(), decoProperties)),
        VERTICAL_WOOD_WINDOW        = materialRegisterBlocks(MedievalBlocks.VERTICAL_WOOD_WINDOW, block -> new BlockItem(block.get(), buildProperties)),
        DENSE_VERTICAL_WOOD_WINDOW  = materialRegisterBlocks(MedievalBlocks.DENSE_VERTICAL_WOOD_WINDOW, block -> new BlockItem(block.get(), buildProperties)),
        CROSS_WOOD_WINDOW           = materialRegisterBlocks(MedievalBlocks.CROSS_WOOD_WINDOW, block -> new BlockItem(block.get(), buildProperties)),
        GRID_WOOD_WINDOW            = materialRegisterBlocks(MedievalBlocks.GRID_WOOD_WINDOW, block -> new BlockItem(block.get(), buildProperties)),
        DIAMOND_WOOD_WINDOW         = materialRegisterBlocks(MedievalBlocks.DIAMOND_WOOD_WINDOW, block -> new BlockItem(block.get(), buildProperties));

    private static Map<Material, RegistryObject<Item>> materialRegister(MaterialRequirement matGetter, String name, Supplier<Item> suplier){
        Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Item>> items = new HashMap<>();

        matGetter.execute(material -> {
            items.put(material, ITEMS.register(material.getTextProv().searchAndReplace(name), suplier));
        });
        return items;
    }

    private static Map<Material, RegistryObject<Item>> materialRegister(MaterialRequirement matGetter, String name, Function<Material, Supplier<Item>> suplier){
        Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Item>> items = new HashMap<>();

        matGetter.execute(material -> {
            items.put(material, ITEMS.register(material.getTextProv().searchAndReplace(name), suplier.apply(material)));
        });
        return items;
    }

    private static Map<Material, RegistryObject<Item>> materialRegisterBlocks(Map<Material, RegistryObject<Block>> blocks, Function<RegistryObject<Block>, Item> func){
        Map<net.dark_roleplay.marg.api.materials.Material, RegistryObject<Item>> items = new HashMap<>();

        for(Map.Entry<Material, RegistryObject<Block>> entry : blocks.entrySet()){
            items.put(entry.getKey(), ITEMS.register(entry.getValue().getId().getPath(), () -> func.apply(entry.getValue())));
        }

        return items;
    }
}


//      TODO ADD those back in
//		reg(new SoupItem(6, new Properties().maxStackSize(1).group(FOOD)), "vegetable_stew");
//		reg(new SoupItem(6, new Properties().maxStackSize(1).group(FOOD)), "pumpkin_stew");
//		reg(new SoupItem(7, new Properties().maxStackSize(1).group(FOOD)), "chicken_stew");
//		reg(new SoupItem(6, new Properties().maxStackSize(1).group(FOOD)), "cod_stew");
//      reg(new Item(new Properties().group(MISCELLANEOUS)), "rope");

// TODO Add those
// new DRPItem("asparagus", "food/vegetables", 64),
// new DRPItem("asparagus_cut", "food/vegetables/cut", 64),
// new DRPItem("asparagus_peeled", "food/vegetables/peeled", 64),
// new DRPItem("aubergine", "food/vegetables", 64),
// new DRPItem("aubergine_cut", "food/vegetables/cut", 64),
// new DRPItem("aubergine_peeled", "food/vegetables/peeled", 64),
// new DRPItem("bean_pod", "food/vegetables", 64),
// new DRPItem("beans", "food/vegetables/peeled", 64),
// new DRPItem("bell_pepper", "food/vegetables", 64),
// new DRPItem("bell_pepper_cut", "food/vegetables/cut", 64),
// new DRPItem("broccoli", "food/vegetables", 64),
// new DRPItem("broccoli_cut", "food/vegetables/cut", 64),
// new DRPItem("brussel_sprouts", "food/vegetables", 64),
// new DRPItem("brussel_sprouts_cut", "food/vegetables/cut", 64),
// new DRPItem("cantaloupe", "food/vegetables", 64),
// new DRPItem("cantaloupe_cut", "food/vegetables/cut", 64),
// new DRPItem("cantaloupe_peeled", "food/vegetables/peeled", 64),
// //new DRPItem("carrot", "food/vegetables", 64),
// new DRPItem("carrot_cut", "food/vegetables/cut", 64),
// new DRPItem("carrot_peeled", "food/vegetables/peeled", 64),
// new DRPItem("cauliflower_cut", "food/vegetables/cut", 64),
// new DRPItem("celery", "food/vegetables", 64),
// new DRPItem("celery_cut", "food/vegetables/cut", 64),
// new DRPItem("cucumber", "food/vegetables", 64),
// new DRPItem("cucumber_cut", "food/vegetables/cut", 64),
// new DRPItem("cucumber_peeled", "food/vegetables/peeled", 64),
// new DRPItem("garlic_cut", "food/vegetables/cut", 64),
// new DRPItem("garlic_peeled", "food/vegetables/peeled", 64),
// new DRPItem("horseradish", "food/vegetables", 64),
// new DRPItem("horseradish_cut", "food/vegetables/cut", 64),
// new DRPItem("jute", "food/other", 64),
// new DRPItem("kohlrabi", "food/vegetables", 64),
// new DRPItem("kohlrabi_cut", "food/vegetables/cut", 64),
// new DRPItem("kohlrabi_peeled", "food/vegetables/peeled", 64),
// new DRPItem("leek", "food/vegetables", 64),
// new DRPItem("leek_cut", "food/vegetables/cut", 64),
// new DRPItem("lentil", "food/vegetables", 64),
// new DRPItem("lettuce", "food/vegetables", 64),
// new DRPItem("lettuce_cut", "food/vegetables/cut", 64),
// new DRPItem("onion_cut", "food/vegetables/cut", 64),
// new DRPItem("onion_peeled", "food/vegetables/peeled", 64),
// new DRPItem("parsley", "food/herbs", 64),
// new DRPItem("parsley_cut", "food/herbs/cut", 64),
// new DRPItem("parsley_root", "food/vegetables", 64),
// new DRPItem("parsley_root_cut", "food/vegetables/cut", 64),
// new DRPItem("pea_pod", "food/vegetables", 64),
// new DRPItem("pea_pod_cut", "food/vegetables/cut", 64),
// new DRPItem("peas", "food/vegetables/peeled", 64),
// //new DRPItem("potato", "food/vegetables", 64),
// new DRPItem("potato_cut", "food/vegetables/cut", 64),
// new DRPItem("potato_peeled", "food/vegetables/peeled", 64),
// //new DRPItem("red_beet", "food/vegetables", 64),
// new DRPItem("red_beet_cut", "food/vegetables/cut", 64),
// new DRPItem("red_beet_peeled", "food/vegetables/peeled", 64),
// new DRPItem("red_cabbage", "food/vegetables", 64),
// new DRPItem("red_cabbage_cut", "food/vegetables/cut", 64),
// new DRPItem("rhubarb", "food/vegetables", 64),
// new DRPItem("rhubarb_cut", "food/vegetables/cut", 64),
// new DRPItem("spinach", "food/vegetables", 64),
// new DRPItem("spinach_cut", "food/vegetables/cut", 64),
// new DRPItem("sugar_beet", "food/vegetables", 64),
// new DRPItem("sugar_beet_cut", "food/vegetables/cut", 64),
// new DRPItem("sweet_potato", "food/vegetables", 64),
// new DRPItem("sweet_potato_cut", "food/vegetables/cut", 64),
// new DRPItem("sweet_potato_peeled", "food/vegetables/peeled", 64),
// new DRPItem("white_cabbage", "food/vegetables", 64),
// new DRPItem("white_cabbage_cut", "food/vegetables/cut", 64),
// new DRPItem("zucchini", "food/vegetables", 64),
// new DRPItem("zucchini_cut", "food/vegetables/cut", 64),
// new DRPItem("zucchini_peeled", "food/vegetables/peeled", 64),

// new PoleWeapon("halberd", "equipment/weapons/pole_weapons", 20),
// new DRPEquip("quiver", "quivers", DRPEquip.TYPE.TYPE_AMMO_STORAGE),
// new DRPEquip("leather_purse", "purses", DRPEquip.TYPE.TYPE_MONEY_STORAGE),
// new DRPEquip("ring_bronze", "rings", DRPEquip.TYPE.TYPE_RING),
// new DRPEquip("ring_silver", "rings", DRPEquip.TYPE.TYPE_RING),
// new DRPEquip("ring_golden", "rings", DRPEquip.TYPE.TYPE_RING),