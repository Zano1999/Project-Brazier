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
