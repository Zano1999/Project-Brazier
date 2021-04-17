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

        prop.maxStackSize(JSONUtils.getInt(propObj, "MaxCount", 64));

        if(propObj.has("MaxDamage"))
            prop.maxDamage(JSONUtils.getInt(propObj, "MaxDamage"));

        if(propObj.has("ItemGroup"))
            prop.group(BrazierCreativeTabs.getGroupFromName(JSONUtils.getString(propObj, "ItemGroup")));

        if(propObj.has("Food"))
            prop.food(loadFood(JSONUtils.getJsonObject(propObj, "Food")));

        return prop;
    }

    private static Food loadFood(JsonObject foodObj){
        Food.Builder builder = new Food.Builder();
        builder.hunger(JSONUtils.getInt(foodObj, "Hunger"));
        builder.saturation(JSONUtils.getFloat(foodObj, "Saturation"));
        if(JSONUtils.getBoolean(foodObj, "IsMeat", false)) builder.meat();
        if(JSONUtils.getBoolean(foodObj, "FastEat", false)) builder.fastToEat();
        if(JSONUtils.getBoolean(foodObj, "AlwaysEdible", false)) builder.setAlwaysEdible();
        return builder.build();
    }
}
