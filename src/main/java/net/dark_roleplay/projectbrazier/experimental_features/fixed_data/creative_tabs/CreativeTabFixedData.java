package net.dark_roleplay.projectbrazier.experimental_features.fixed_data.creative_tabs;

import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.util.FixedData;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.FixedDataPack;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.creative_tabs.types.BasicTabProvider;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.creative_tabs.types.ICreativeTabProvider;
import net.dark_roleplay.projectbrazier.experimental_features.link.either_codec_registry.CodecDispatchRegistry;
import net.dark_roleplay.projectbrazier.experimental_features.link.either_codec_registry.CodecDispatchType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.RegistryEvent;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CreativeTabFixedData extends FixedData {

	private static Map<ResourceLocation, CreativeModeTab> TABS = new HashMap<>();

	public static final CodecDispatchRegistry<ICreativeTabProvider> CREATIVE_TYPE_PROVIDERS = new CodecDispatchRegistry<>();
	static{
		CREATIVE_TYPE_PROVIDERS.register(new CodecDispatchType(new ResourceLocation(ProjectBrazier.MODID, "creative_mode_tab"), BasicTabProvider.class, BasicTabProvider.CODEC));
	}

	public static void load(RegistryEvent.NewRegistry event){
		TABS.clear();
		FixedDataPack pack = FixedDataPack.getPackForMod(ProjectBrazier.MODID);

		Collection<ResourceLocation> itemLocations = pack.getResources(ProjectBrazier.MODID, "creative_tabs/", Integer.MAX_VALUE, file -> file.endsWith(".json"));

		for(ResourceLocation tabLocation : itemLocations){
			try {
				String[] splitPath = tabLocation.getPath().split("/");
				ResourceLocation registryName = new ResourceLocation(ProjectBrazier.MODID, splitPath[splitPath.length-1].replace(".json", ""));
				InputStreamReader itemStream = new InputStreamReader(pack.getResource(tabLocation));

				DataResult<ICreativeTabProvider> result = CREATIVE_TYPE_PROVIDERS.getCodec().parse(JsonOps.INSTANCE, JsonParser.parseReader(itemStream));
				ICreativeTabProvider provider = result.getOrThrow(false, LOGGER::warn);

				TABS.put(registryName, provider.construct(registryName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static CreativeModeTab getTab(ResourceLocation name){
		if(!TABS.containsKey(name))
			LOGGER.error("Tried to query missing creative Tab {}", name);
		return TABS.get(name);
	}
}
