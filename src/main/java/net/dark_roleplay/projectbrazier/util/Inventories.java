package net.dark_roleplay.projectbrazier.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class Inventories {

	public static ItemStack givePlayerItem(PlayerEntity player, ItemStack stack, Hand hand, boolean dropOnGround){
		if(player.getItemInHand(hand).isEmpty()) {
			player.setItemInHand(hand, stack);
			return ItemStack.EMPTY;
		}else{
			if(!player.addItem(stack)){
				player.drop(stack, false);
			}
			return ItemStack.EMPTY;
		}
	}
}
