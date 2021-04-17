package net.dark_roleplay.projectbrazier.feature.items;

import net.minecraft.item.Item;

public class SpyglassItem extends Item {

	protected int[] zoomFOVs = new int[]{50, 30, 10};

	public SpyglassItem(Properties properties) {
		super(properties);
	}

	public int getZoomCount(){
		return 3;
	}

	public int[] getZoomFOVs(){
		return zoomFOVs;
	}
}
