package net.dark_roleplay.projectbrazier.experimental_features.fixed_data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierCreativeTabs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ItemPropertyLoader {

    private static final Map<String, Item.Properties> PROPERTIES = new HashMap<>();

    public static Item.Properties getProp(String name){
        if(PROPERTIES.isEmpty())
            loadItemProperties();
        return PROPERTIES.get(name);
    }

    public static void loadItemProperties(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ItemPropertyLoader.class.getClassLoader().getResourceAsStream("fixed_data/projectbrazier/item_props.json")))) {
            JsonParser parser = new JsonParser();
            JsonObject properties = parser.parse(reader).getAsJsonObject();

            for(Map.Entry<String, JsonElement> entry : properties.entrySet())
                PROPERTIES.put(entry.getKey(), loadProp((JsonObject) entry.getValue()));
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private static Item.Properties loadProp(JsonObject propObj){
        Item.Properties prop = new Item.Properties();

        prop.stacksTo(GsonHelper.getAsInt(propObj, "MaxCount", 64));

        if(propObj.has("MaxDamage"))
            prop.durability(GsonHelper.getAsInt(propObj, "MaxDamage"));

        if(propObj.has("ItemGroup"))
            prop.tab(BrazierCreativeTabs.getGroupFromName(GsonHelper.getAsString(propObj, "ItemGroup")));

        if(propObj.has("Food"))
            prop.food(loadFood(GsonHelper.getAsJsonObject(propObj, "Food")));

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
}