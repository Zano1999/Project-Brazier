package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.marg.api.Materials;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MedievalCreativeTabs {

	//TODO Updateh MedievalItems
	public static final ItemGroup DECORATION = new ItemGroup("drpmedieval.decoration") {

		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(MedievalBlocks.WOOD_SOLID_ARMREST_CHAIR.get(Materials.getMaterial("spruce")).get());
		}
	};
	
	public static final ItemGroup BUILDING = new ItemGroup("drpmedieval.building") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(MedievalBlocks.DIAMOND_WOOD_WINDOW.get(Materials.getMaterial("birch")).get());
		}
	};
	
	public static final ItemGroup UTILITY = new ItemGroup("drpmedieval.utility") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(Blocks.POWERED_RAIL);
		}
	};
	
	public static final ItemGroup FOOD = new ItemGroup("drpmedieval.food") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(MedievalItems.GREEN_APPLE.get());
		}
	};
	
	public static final ItemGroup EQUIPMENT = new ItemGroup("drpmedieval.equipment") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(MedievalItems.GOLDEN_TELESCOPE.get());
		}
	};
	
	public static final ItemGroup MISCELLANEOUS = new ItemGroup("drpmedieval.miscellaneous") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(MedievalItems.HAY.get());
		}
	};
	
//	public static final ItemGroup CREATIVE = new ItemGroup("drpmedieval.creative_only") {
//		@OnlyIn(Dist.CLIENT)
//		public ItemStack createIcon() {
//			return new ItemStack(MedievalBlocks.BARRIER.asItem());
//		}
//	};
}
