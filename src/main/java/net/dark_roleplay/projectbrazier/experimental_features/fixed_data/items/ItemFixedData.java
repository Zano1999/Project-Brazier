package net.dark_roleplay.projectbrazier.experimental_features.fixed_data.items;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.FixedDataPack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Logger;

public class ItemFixedData {
	private static Logger LOGGER = Logger.getLogger(ProjectBrazier.MODID + ".fixed_data.item");

	public static void registerItems(RegistryEvent<Item> event){
		FixedDataPack pack = FixedDataPack.getPackForMod(ProjectBrazier.MODID);

		Collection<ResourceLocation> itemLocations = pack.getResources(ProjectBrazier.MODID, "item/instances/", Integer.MAX_VALUE, file -> file.endsWith(".json"));

		for(ResourceLocation itemLocation : itemLocations){
			try {
				String[] splitPath = itemLocation.getPath().split("/");
				ResourceLocation registryName = new ResourceLocation(ProjectBrazier.MODID, splitPath[splitPath.length-1].replace(".json", ""));
				System.out.println(registryName);
				InputStream itemStream = pack.getResource(itemLocation);

				//TODO Load & Register Items
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Test");
	}
}
