package net.dark_roleplay.project_brazier.features.items;

import net.minecraft.item.Item;

public class ZoomItem extends Item {

	protected int[] zoomFOVs = new int[]{50, 30, 10};

	public ZoomItem(Properties properties) {
		super(properties);
	}

	public int getZoomCount(){
		return 3;
	}

	public int[] getZoomFOVs(){
		return zoomFOVs;
	}
}
