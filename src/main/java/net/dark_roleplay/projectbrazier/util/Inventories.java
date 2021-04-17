package net.dark_roleplay.projectbrazier.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class Inventories {

	public static ItemStack givePlayerItem(PlayerEntity player, ItemStack stack, Hand hand, boolean dropOnGround){
		if(player.getHeldItem(hand).isEmpty()) {
			player.setHeldItem(hand, stack);
			return ItemStack.EMPTY;
		}else{
			if(!player.addItemStackToInventory(stack)){
				player.dropItem(stack, false);
			}
			return ItemStack.EMPTY;
		}
	}
}
