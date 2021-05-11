package net.dark_roleplay.projectbrazier.feature.items;

import net.dark_roleplay.projectbrazier.feature_client.listeners.SpyglassListeners;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SpyglassItem extends Item {

	protected int[] zoomFOVs = new int[]{50, 30, 10};

	public SpyglassItem(Properties properties) {
		super(properties);
	}

	public int getZoomCount() {
		return 3;
	}

	public int[] getZoomFOVs() {
		return zoomFOVs;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		if(world.isRemote){
			SpyglassListeners.toogleZoom();
		}
		return ActionResult.resultConsume(itemstack);
	}
}