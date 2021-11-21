package net.dark_roleplay.projectbrazier.experimental_features.data_props;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierCreativeTabs;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.JSONUtils;

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

        prop.stacksTo(JSONUtils.getAsInt(propObj, "MaxCount", 64));

        if(propObj.has("MaxDamage"))
            prop.durability(JSONUtils.getAsInt(propObj, "MaxDamage"));

        if(propObj.has("ItemGroup"))
            prop.tab(BrazierCreativeTabs.getGroupFromName(JSONUtils.getAsString(propObj, "ItemGroup")));

        if(propObj.has("Food"))
            prop.food(loadFood(JSONUtils.getAsJsonObject(propObj, "Food")));

        return prop;
    }

    private static Food loadFood(JsonObject foodObj){
        Food.Builder builder = new Food.Builder();
        builder.nutrition(JSONUtils.getAsInt(foodObj, "Hunger"));
        builder.saturationMod(JSONUtils.getAsFloat(foodObj, "Saturation"));
        if(JSONUtils.getAsBoolean(foodObj, "IsMeat", false)) builder.meat();
        if(JSONUtils.getAsBoolean(foodObj, "FastEat", false)) builder.fast();
        if(JSONUtils.getAsBoolean(foodObj, "AlwaysEdible", false)) builder.alwaysEat();
        return builder.build();
    }
}
