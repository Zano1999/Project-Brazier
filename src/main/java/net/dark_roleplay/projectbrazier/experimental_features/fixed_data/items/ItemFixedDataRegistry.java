package net.dark_roleplay.projectbrazier.experimental_features.fixed_data.items;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.CodecUtil;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.FixedDataPack;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.ItemPropertyLoader;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.creative_tabs.types.ICreativeTabProvider;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.util.ForgeFixedData;
import net.dark_roleplay.projectbrazier.experimental_features.link.either_codec_registry.CodecDispatchRegistry;
import net.dark_roleplay.projectbrazier.experimental_features.link.either_codec_registry.CodecDispatchType;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.feature.items.DryClayItem;
import net.dark_roleplay.projectbrazier.feature.items.PlantSeedsItem;
import net.dark_roleplay.projectbrazier.feature.items.SpyglassItem;
import net.dark_roleplay.projectbrazier.feature.items.WarHornItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ItemFixedDataRegistry extends ForgeFixedData<Item> {
	private static final CodecDispatchRegistry<Item> ITEM_PROVIDER = new CodecDispatchRegistry<>();

	static{
		ITEM_PROVIDER.register(new CodecDispatchType(new ResourceLocation(ProjectBrazier.MODID, "item"), Item.class, MapCodec.of(Encoder.empty(), CodecUtil.ITEM_PROPERTIES.map(Item::new))));
		ITEM_PROVIDER.register(new CodecDispatchType(new ResourceLocation(ProjectBrazier.MODID, "dry_clay"), DryClayItem.class, MapCodec.of(Encoder.empty(), CodecUtil.ITEM_PROPERTIES.map(DryClayItem::new))));
		ITEM_PROVIDER.register(new CodecDispatchType(new ResourceLocation(ProjectBrazier.MODID, "war_horn"), WarHornItem.class, MapCodec.of(Encoder.empty(), CodecUtil.ITEM_PROPERTIES.map(WarHornItem::new))));
		ITEM_PROVIDER.register(new CodecDispatchType(new ResourceLocation(ProjectBrazier.MODID, "spyglass"), SpyglassItem.class, MapCodec.of(Encoder.empty(), CodecUtil.ITEM_PROPERTIES.map(SpyglassItem::new))));
		ITEM_PROVIDER.register(new CodecDispatchType(new ResourceLocation(ProjectBrazier.MODID, "plant_seeds"), PlantSeedsItem.class, ItemCodecs.PLANT_SEED_ITEM));
		ITEM_PROVIDER.register(new CodecDispatchType(new ResourceLocation(ProjectBrazier.MODID, "selective_block"), SelectiveBlockItem.class, ItemCodecs.SELECTIVE_BLOCK_ITEM));
	}

	public ItemFixedDataRegistry(IForgeRegistry<Item> registry, String namespace) {
		super(registry, namespace);
	}

	@Override
	protected Set<Item> loadObjects() {
		Set<Item> items = new HashSet<>();
		FixedDataPack pack = FixedDataPack.getPackForMod(ProjectBrazier.MODID);

		Collection<ResourceLocation> itemLocations = pack.getResources(ProjectBrazier.MODID, "item/instances/", Integer.MAX_VALUE, file -> file.endsWith(".json"));

		for (ResourceLocation itemLocation : itemLocations) {
			try {
				String[] splitPath = itemLocation.getPath().split("/");
				ResourceLocation registryName = new ResourceLocation(ProjectBrazier.MODID, splitPath[splitPath.length - 1].replace(".json", ""));
				InputStreamReader itemStream = new InputStreamReader(pack.getResource(itemLocation));

				JsonElement element = JsonParser.parseReader(itemStream);

//				DataResult<Item.Properties> result = CodecUtil.ITEM_PROPERTIES.parse(JsonOps.INSTANCE, element);
//				Item.Properties props = result.getOrThrow(true, LOGGER::warn);

				try{
					DataResult<Item> result = ITEM_PROVIDER.getCodec().parse(JsonOps.INSTANCE, element);
					Item item = result.getOrThrow(false, err -> LOGGER.error("Failed to load item json {}", registryName, err));
					item.setRegistryName(registryName);
					items.add(item);
				}catch(Exception e){
					LOGGER.error("Failed to load item json {}", registryName, e);
				}

			} catch (IOException e) {
				LOGGER.error("Failed to load item json {}", itemLocation, e);
			}
		}
		return items;
	}
}
