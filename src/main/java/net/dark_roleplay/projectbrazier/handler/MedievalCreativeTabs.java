package net.dark_roleplay.projectbrazier.handler;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class MedievalCreativeTabs {

	public static ItemGroup FOOD = null;
	public static ItemGroup MISCELLANEOUS = null;
	public static ItemGroup DECORATION = null;

	public static ItemGroup food(){
		if(FOOD == null)
			FOOD = new SimpleItemGroup("food", () -> new ItemStack(Items.APPLE));
		return FOOD;
	}

	public static ItemGroup misc(){
		if(MISCELLANEOUS == null)
			MISCELLANEOUS = new SimpleItemGroup("miscellaneous", () -> new ItemStack(Items.DIAMOND));
		return MISCELLANEOUS;
	}

	public static ItemGroup decor(){
		if(DECORATION == null)
			DECORATION = new SimpleItemGroup("decoration", () -> new ItemStack(Items.PUMPKIN_SEEDS));
		return DECORATION;
	}


	private static class SimpleItemGroup extends ItemGroup{
		private Supplier<ItemStack> icon;

		public SimpleItemGroup(String lbl, Supplier<ItemStack> icon){
			super(ProjectBrazier.MODID + "." + lbl);
			this.icon = icon;
		}

		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return icon.get();
		}
	}

	public static ItemGroup getGroupFromName(String name){
		switch(name){
			case "misc":
				return MedievalCreativeTabs.misc();
			case "food":
				return MedievalCreativeTabs.food();
			case "deco":
				return MedievalCreativeTabs.decor();
			default:
				return null;
		}
	}
}
