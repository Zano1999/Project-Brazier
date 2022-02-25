package net.dark_roleplay.projectbrazier.experimental_features.fixed_data.creative_tabs.types;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public interface ICreativeTabProvider<T extends CreativeModeTab> {
	CreativeModeTab construct(ResourceLocation path);
}
