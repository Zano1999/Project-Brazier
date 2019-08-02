package net.dark_roleplay.medieval.holders;

import net.minecraft.item.Food;

public class MedievalFoods {
	
	public static final Food
		//RAW FRUITS
		PEAR 				= new Food.Builder().hunger(4).saturation(0.3F).build(),
		BLUEBERRIES 		= new Food.Builder().hunger(1).saturation(0.1F).fastToEat().build(),
		GRAPES 				= new Food.Builder().hunger(2).saturation(0.2F).build(),
		
		BELL_PEPPER 		= new Food.Builder().hunger(4).saturation(0.2F).build(),
		CAULIFLOWER 		= new Food.Builder().hunger(6).saturation(0.3F).build(),
		EGGPLANT 			= new Food.Builder().hunger(4).saturation(0.3F).build(),
		GARLIC 				= new Food.Builder().hunger(1).saturation(0.1F).build(),
		HONEY_COMB 			= new Food.Builder().hunger(10).saturation(0.6F).build(),
		HOPS 				= new Food.Builder().hunger(2).saturation(0.1F).build(),
		ONION 				= new Food.Builder().hunger(3).saturation(0.2F).build(),
		RAW_CATFISH 		= new Food.Builder().hunger(3).saturation(0.2F).build(),
		RAW_WOLF 			= new Food.Builder().hunger(4).saturation(0.2F).build(),
		TURNIP 				= new Food.Builder().hunger(4).saturation(0.2F).build(),
	
		//PROCESSED FOODS
		SPRUCE_TEA			= new Food.Builder().hunger(0).saturation(0.2F).build(),	
		BUTTER 				= new Food.Builder().hunger(8).saturation(0.2F).build(),
		CARAMELIZED_APPLE	= new Food.Builder().hunger(6).saturation(0.4F).build(),
		GRILLED_CATFISH 	= new Food.Builder().hunger(6).saturation(0.5F).build(),
		GRILLED_WOLF 		= new Food.Builder().hunger(6).saturation(0.5F).build();
	
	
//	new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(Foods.MUSHROOM_STEW))
//	reg(new SoupItem(		6, new Properties().maxStackSize(1).group(FOOD)), "vegetable_stew");
//	reg(new SoupItem(		6, new Properties().maxStackSize(1).group(FOOD)), "pumpkin_stew");
//	reg(new SoupItem(		7, new Properties().maxStackSize(1).group(FOOD)), "chicken_stew");
//	reg(new SoupItem(		6, new Properties().maxStackSize(1).group(FOOD)), "cod_stew");

}
