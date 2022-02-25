package net.dark_roleplay.projectbrazier.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Inventories {

	public static void dropItems(Level level, BlockPos pos, ItemStack stack){
		ItemEntity itementity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
		itementity.setPickUpDelay(40);
	}

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

	public static int doesPlayerHaveEnoughItems(Player player, Item item, int required){
		Inventory inv = player.getInventory();
		for(int i = 0; i < player.getInventory().getContainerSize() && required > 0; i++){
			ItemStack stack = inv.getItem(i);
			if(stack.getItem() == item)
				required -= stack.getCount();
		}

		return required;
	}

	public static void consumeAmountOfItems(Player player, Item item, int required){
		Inventory inv = player.getInventory();
		for(int i = 0; i < player.getInventory().getContainerSize() && required > 0; i++){
			ItemStack stack = inv.getItem(i);
			if(stack.getItem() == item) {
				stack.shrink(Math.min(required, stack.getCount()));
				required -= stack.getCount();
			}
		}
	}
}
