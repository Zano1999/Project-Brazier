package net.dark_roleplay.projectbrazier.experimental_features.fixed_data.creative_tabs.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class BasicTabProvider implements ICreativeTabProvider<CreativeModeTab> {
	public static final MapCodec<BasicTabProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("icon").forGetter(tab -> tab.icon)
	).apply(instance, BasicTabProvider::new));

	private ResourceLocation icon;

	public BasicTabProvider(ResourceLocation icon){
		this.icon = icon;
	}

	@Override
	public CreativeModeTab construct(ResourceLocation name) {
		return new CreativeModeTab(name.getNamespace() + "." + name.getPath()){
			@Override
			public ItemStack makeIcon() {
				return ForgeRegistries.ITEMS.getValue(icon).getDefaultInstance();
			}
		};
	}
}
