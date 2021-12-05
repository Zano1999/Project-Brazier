package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class BrazierCreativeTabs {

	public static ItemGroup FOOD = null;
	public static ItemGroup MISCELLANEOUS = null;
	public static ItemGroup DECORATION = null;

	public static ItemGroup food(){
		return misc();

//		if(FOOD == null)
//			FOOD = new SimpleItemGroup("food", () -> new ItemStack(BrazierItems.HOPS.get()));
//		return FOOD;
	}

	public static ItemGroup misc(){
		if(MISCELLANEOUS == null)
			MISCELLANEOUS = new SimpleItemGroup("miscellaneous", () -> new ItemStack(BrazierItems.GOLD_COIN.get()));
		return MISCELLANEOUS;
	}

	public static ItemGroup decor(){
		if(DECORATION == null)
			DECORATION = new SimpleItemGroup("decoration", () -> new ItemStack(BrazierBlocks.IRON_BRAZIER_COAL.get()));
		return DECORATION;
	}


	private static class SimpleItemGroup extends ItemGroup{
		private Supplier<ItemStack> icon;

		public SimpleItemGroup(String lbl, Supplier<ItemStack> icon){
			super(ProjectBrazier.MODID + "." + lbl);
			this.icon = icon;
		}

		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return icon.get();
		}
	}

	public static ItemGroup getGroupFromName(String name){
		switch(name){
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
