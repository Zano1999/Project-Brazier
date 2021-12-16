package net.dark_roleplay.projectbrazier.feature.items;

import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature.helpers.ZiplineHelper;
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
		ZiplineHelper.startZipline(
				player,
				world,
				new Vector3f(57, 64, 684),
				new Vector3f(7, 50, 701),
				new Vector3f(29, 52, 693)
		);
		ItemStack itemstack = player.getItemInHand(hand);
//		if(world.isClientSide){
//			SpyglassListeners.toogleZoom();
//		}
		return InteractionResultHolder.consume(itemstack);
	}
}