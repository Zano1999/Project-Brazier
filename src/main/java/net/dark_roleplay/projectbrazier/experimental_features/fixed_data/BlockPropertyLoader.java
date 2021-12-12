package net.dark_roleplay.projectbrazier.experimental_features.fixed_data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BlockPropertyLoader {

    private static final Map<String, BlockBehaviour.Properties> PROPERTIES = new HashMap<>();

    public static BlockBehaviour.Properties getProp(String name){
        if(PROPERTIES.isEmpty())
            loadBlockProperties();
        return PROPERTIES.get(name);
    }

    public static void loadBlockProperties(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(BlockPropertyLoader.class.getClassLoader().getResourceAsStream("fixed_data/projectbrazier/item_props.json")))) {
            JsonParser parser = new JsonParser();
            JsonObject properties = parser.parse(reader).getAsJsonObject();

            for(Map.Entry<String, JsonElement> entry : properties.entrySet())
                PROPERTIES.put(entry.getKey(), loadProp((JsonObject) entry.getValue()));
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private static Properties loadProp(JsonObject propObj){
        Properties prop = Properties.of(getMaterialFromName(GsonHelper.getAsString(propObj, "material", "AIR")));

        if(GsonHelper.getAsBoolean(propObj, "hasCollision", true))
            prop.noCollission();

        if(GsonHelper.getAsBoolean(propObj, "hasOcclusion", true))
            prop.noOcclusion();

        prop.friction(GsonHelper.getAsFloat(propObj, "friction", 0.6F));
        prop.speedFactor(GsonHelper.getAsFloat(propObj, "speedFactor", 1F));
        prop.jumpFactor(GsonHelper.getAsFloat(propObj, "jumpFactor", 1F));


        return prop;
    }

    private static FoodProperties loadFood(JsonObject foodObj){
        FoodProperties.Builder builder = new FoodProperties.Builder();
        builder.nutrition(GsonHelper.getAsInt(foodObj, "Hunger"));
        builder.saturationMod(GsonHelper.getAsFloat(foodObj, "Saturation"));
        if(GsonHelper.getAsBoolean(foodObj, "IsMeat", false)) builder.meat();
        if(GsonHelper.getAsBoolean(foodObj, "FastEat", false)) builder.fast();
        if(GsonHelper.getAsBoolean(foodObj, "AlwaysEdible", false)) builder.alwaysEat();
        return builder.build();
    }

    private static Material getMaterialFromName(String name){
        switch(name.toUpperCase()){
            case "AIR": return Material.AIR;
            case "STRUCTURE_VOID": return Material.STRUCTURAL_AIR;
            case "PORTAL": return Material.PORTAL;
            case "CARPET": return Material.CLOTH_DECORATION;
            case "PLANTS": return Material.PLANT;
            case "OCEAN_PLANT": return Material.WATER_PLANT;
            case "TALL_PLANTS": return Material.REPLACEABLE_PLANT;
            case "REPLACEABLE_FIREPROOF_PLANT": return Material.REPLACEABLE_FIREPROOF_PLANT;
            case "SEA_GRASS": return Material.REPLACEABLE_WATER_PLANT;
            case "WATER": return Material.WATER;
            case "BUBBLE_COLUMN": return Material.BUBBLE_COLUMN;
            case "LAVA": return Material.LAVA;
            case "SNOW": return Material.TOP_SNOW;
            case "FIRE": return Material.FIRE;
            case "WEB": return Material.WEB;
            case "SCULK": return Material.SCULK;
            case "REDSTONE_LIGHT": return Material.BUILDABLE_GLASS;
            case "CLAY": return Material.CLAY;
            case "EARTH": return Material.DIRT;
            case "ORGANIC": return Material.GRASS;
            case "PACKED_ICE": return Material.ICE_SOLID;
            case "SAND": return Material.SAND;
            case "SPONGE": return Material.SPONGE;
            case "SHULKER": return Material.SHULKER_SHELL;
            case "WOOD": return Material.WOOD;
            case "NETHER_WOOD": return Material.NETHER_WOOD;
            case "BAMBOO_SAPLING": return Material.BAMBOO_SAPLING;
            case "BAMBOO": return Material.BAMBOO;
            case "WOOL": return Material.WOOL;
            case "TNT": return Material.EXPLOSIVE;
            case "LEAVES": return Material.LEAVES;
            case "GLASS": return Material.GLASS;
            case "ICE": return Material.ICE;
            case "CACTUS": return Material.CACTUS;
            case "ROCK": return Material.STONE;
            case "IRON": return Material.METAL;
            case "SNOW_BLOCK": return Material.SNOW;
            case "ANVIL": return Material.HEAVY_METAL;
            case "BARRIER": return Material.BARRIER;
            case "PISTON": return Material.PISTON;
            case "MOSS": return Material.MOSS;
            case "GOURD": return Material.VEGETABLE;
            case "DRAGON_EGG": return Material.EGG;
            case "CAKE": return Material.CAKE;
            case "AMETHYST": return Material.AMETHYST;
            case "POWDER_SNOW": return Material.POWDER_SNOW;
            case "MISCELLANEOUS":
            default: return Material.DECORATION;
        }
    }
}
