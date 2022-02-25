package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.fixed_data.creative_tabs.CreativeTabFixedData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public class BrazierCreativeTabs {

	public static CreativeModeTab FOOD = null;
	public static Lazy<CreativeModeTab> MISCELLANEOUS = Lazy.of(() -> CreativeTabFixedData.getTab(new ResourceLocation(ProjectBrazier.MODID, "miscellaneous")));
	public static Lazy<CreativeModeTab> DECORATION = Lazy.of(() -> CreativeTabFixedData.getTab(new ResourceLocation(ProjectBrazier.MODID, "decoration")));

	public static CreativeModeTab food() {
		return misc();
	}
	public static CreativeModeTab misc() { return MISCELLANEOUS.get(); }
	public static CreativeModeTab decor() { return DECORATION.get(); }

	public static CreativeModeTab getGroupFromName(String name) {
		return CreativeTabFixedData.getTab(new ResourceLocation(ProjectBrazier.MODID, name));
	}
}
