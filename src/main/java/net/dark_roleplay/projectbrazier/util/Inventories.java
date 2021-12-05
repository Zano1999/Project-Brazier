package net.dark_roleplay.projectbrazier.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Inventories {

	public static ItemStack givePlayerItem(Player player, ItemStack stack, InteractionHand hand, boolean dropOnGround){
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
