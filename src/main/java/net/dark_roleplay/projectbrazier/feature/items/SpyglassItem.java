package net.dark_roleplay.projectbrazier.feature.items;

import net.dark_roleplay.projectbrazier.feature_client.listeners.SpyglassListeners;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

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
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if(world.isClientSide){
			SpyglassListeners.toogleZoom();
		}
		return InteractionResultHolder.consume(itemstack);
	}
}