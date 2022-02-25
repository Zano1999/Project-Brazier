package net.dark_roleplay.projectbrazier.feature.items;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DryClayItem extends Item {
	public DryClayItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		if(entity.isInWaterOrRain()){
			entity.setItem(new ItemStack(Items.CLAY_BALL, entity.getItem().getCount()));
		}
		return false;
	}
}
