package net.dark_roleplay.projectbrazier.util.json;

import com.google.gson.stream.JsonReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.io.IOException;
import java.io.InputStreamReader;

public class BlockPropertyLoader {

	public static Block.Properties properties(String name){
		Block.Properties props = Block.Properties.of(null, (MaterialColor) null);
		if(name != null) {
			try (JsonReader reader = new JsonReader(new InputStreamReader(BlockPropertyLoader.class.getClassLoader().getResourceAsStream("fixed_data/projectbrazier/properties/block/" + name + ".json")))) {
				reader.beginObject();

				while (reader.hasNext()) {
					switch (reader.nextName()) {
//						case "MaxCount":
//							props.maxStackSize(reader.nextInt());
//							break;
//						case "ItemGroup":
//							props.group(getGroupFromName(reader.nextString()));
//							break;
//						case "MaxDamage":
//							props.maxDamage(reader.nextInt());
//							break;
//						case "Food":
//							props.food(getFood(reader));
//							break;
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

		return null;
	}
}
