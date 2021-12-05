package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class BrazierCreativeTabs {

	public static CreativeModeTab FOOD = null;
	public static CreativeModeTab MISCELLANEOUS = null;
	public static CreativeModeTab DECORATION = null;

	public static CreativeModeTab food() {
		return misc();

//		if(FOOD == null)
//			FOOD = new SimpleCreativeModeTab("food", () -> new ItemStack(BrazierItems.HOPS.get()));
//		return FOOD;
	}

	public static CreativeModeTab misc() {
		if (MISCELLANEOUS == null)
			MISCELLANEOUS = new SimpleCreativeModeTab("miscellaneous", () -> new ItemStack(BrazierItems.GOLD_COIN.get()));
		return MISCELLANEOUS;
	}

	public static CreativeModeTab decor() {
		if (DECORATION == null)
			DECORATION = new SimpleCreativeModeTab("decoration", () -> new ItemStack(BrazierBlocks.IRON_BRAZIER_COAL.get()));
		return DECORATION;
	}


	private static class SimpleCreativeModeTab extends CreativeModeTab {
		private Supplier<ItemStack> icon;

		public SimpleCreativeModeTab(String lbl, Supplier<ItemStack> icon) {
			super(ProjectBrazier.MODID + "." + lbl);
			this.icon = icon;
		}

		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return icon.get();
		}
	}

	public static CreativeModeTab getGroupFromName(String name) {
		switch (name) {
			case "misc":
				return BrazierCreativeTabs.misc();
			case "food":
				return BrazierCreativeTabs.food();
			case "deco":
				return BrazierCreativeTabs.decor();
			default:
				return null;
		}
	}
}
