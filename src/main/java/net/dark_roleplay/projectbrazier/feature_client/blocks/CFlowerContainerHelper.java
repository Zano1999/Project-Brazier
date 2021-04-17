package net.dark_roleplay.projectbrazier.feature_client.blocks;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class CFlowerContainerHelper {

	public static List<CFlowerContainerData> createFlowerData(int flowerCount) {
		List<CFlowerContainerData> flowers = new ArrayList<>(flowerCount);
		for(int i = 0; i < flowerCount; i++)
			flowers.add(0, new CFlowerContainerData());
		return ImmutableList.copyOf(flowers);
	}

}
