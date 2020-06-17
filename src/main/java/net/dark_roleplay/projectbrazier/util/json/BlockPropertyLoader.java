package net.dark_roleplay.projectbrazier.util.json;

import com.google.gson.stream.JsonReader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

import java.io.IOException;
import java.io.InputStreamReader;

public class BlockPropertyLoader {

	public static Block.Properties properties(String name){
		Block.Properties props = Block.Properties.create(null, (MaterialColor) null);
		if(name != null) {
			try (JsonReader reader = new JsonReader(new InputStreamReader(ItemPropertyLoader.class.getClassLoader().getResourceAsStream("fixed_data/projectbrazier/properties/block/" + name + ".json")))) {
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


	private static Material getMaterialFromName(String name){
		switch(name.toUpperCase()){
			case "AIR": return Material.AIR;
			case "STRUCTURE_VOID": return Material.STRUCTURE_VOID;
			case "PORTAL": return Material.PORTAL;
			case "CARPET": return Material.CARPET;
			case "PLANTS": return Material.PLANTS;
			case "OCEAN_PLANT": return Material.OCEAN_PLANT;
			case "TALL_PLANTS": return Material.TALL_PLANTS;
			case "SEA_GRASS": return Material.SEA_GRASS;
			case "WATER": return Material.WATER;
			case "BUBBLE_COLUMN": return Material.BUBBLE_COLUMN;
			case "SNOW": return Material.SNOW;
			case "FIRE": return Material.FIRE;
			case "WEB": return Material.WEB;
			case "REDSTONE_LIGHT": return Material.REDSTONE_LIGHT;
			case "CLAY": return Material.CLAY;
			case "EARTH": return Material.EARTH;
			case "ORGANIC": return Material.ORGANIC;
			case "PACKED_ICE": return Material.PACKED_ICE;
			case "SAND": return Material.SAND;
			case "SPONGE": return Material.SPONGE;
			case "SHULKER": return Material.SHULKER;
			case "WOOD": return Material.WOOD;
			case "BAMBOO_SAPLING": return Material.BAMBOO_SAPLING;
			case "BAMBOO": return Material.BAMBOO;
			case "WOOL": return Material.WOOL;
			case "TNT": return Material.TNT;
			case "LEAVES": return Material.LEAVES;
			case "GLASS": return Material.GLASS;
			case "ICE": return Material.ICE;
			case "CACTUS": return Material.CACTUS;
			case "ROCK": return Material.ROCK;
			case "IRON": return Material.IRON;
			case "SNOW_BLOCK": return Material.SNOW_BLOCK;
			case "ANVIL": return Material.ANVIL;
			case "BARRIER": return Material.BARRIER;
			case "PISTON": return Material.PISTON;
			case "CORAL": return Material.CORAL;
			case "GOURD": return Material.GOURD;
			case "DRAGON_EGG": return Material.DRAGON_EGG;
			case "CAKE": return Material.CAKE;
			case "MISCELLANEOUS":
			default: return Material.MISCELLANEOUS;
		}
	}

}
