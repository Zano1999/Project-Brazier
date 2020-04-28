package net.dark_roleplay.project_brazier.util.json;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.project_brazier.handler.MedievalCreativeTabs;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.io.IOException;
import java.io.InputStreamReader;

public class ItemPropertyLoader {

	public static Item.Properties properties(String name){
		Item.Properties props = new Item.Properties();
		if(name != null) {
			try (JsonReader reader = new JsonReader(new InputStreamReader(ItemPropertyLoader.class.getClassLoader().getResourceAsStream("fixed_data/drpmedieval/properties/item/" + name + ".json")))) {
				reader.beginObject();

				while (reader.hasNext()) {
					switch (reader.nextName()) {
						case "MaxCount":
							props.maxStackSize(reader.nextInt());
							break;
						case "ItemGroup":
							props.group(getGroupFromName(reader.nextString()));
							break;
						case "MaxDamage":
							props.maxDamage(reader.nextInt());
							break;
						case "Food":
							props.food(getFood(reader));
							break;
						default:
							reader.skipValue();
							break;
					}
				}

				reader.endObject();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
	}

	private static ItemGroup getGroupFromName(String name){
		switch(name){
			case "misc":
				return MedievalCreativeTabs.misc();
			case "food":
				return MedievalCreativeTabs.food();
			default:
				return null;
		}
	}

	private static Food getFood(JsonReader reader) throws IOException {
		Food.Builder builder = new Food.Builder();
		reader.beginObject();
		while(reader.hasNext()) {
			switch (reader.nextName()) {
				case "Value":
					builder.hunger(reader.nextInt());
					break;
				case "Saturation":
					builder.saturation((float) reader.nextDouble());
					break;
				case "IsMeat":
					if(reader.nextBoolean()) builder.meat();
					break;
				case "FastEat":
					if(reader.nextBoolean()) builder.fastToEat();
					break;
				case "AlwaysEdible":
					if(reader.nextBoolean()) builder.setAlwaysEdible();
					break;
			}
		}
		reader.endObject();

		return builder.build();
	}
}
