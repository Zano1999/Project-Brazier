package net.dark_roleplay.projectbrazier.objects.blocks.nail;

import net.dark_roleplay.projectbrazier.handler.MedievalBlocks;
import net.dark_roleplay.projectbrazier.handler.MedievalItems;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class HangableBehavior {
	private static final Map<Item, HangUpItemBlock> hangableItems = new HashMap<>();

	static{
		hangableItems.put(MedievalItems.BONE_WAR_HORN.get(), (HangUpItemBlock) MedievalBlocks.HANGING_HORN.get());
		hangableItems.put(MedievalItems.SILVER_SPYGLASS.get(), (HangUpItemBlock) MedievalBlocks.HANGING_SILVER_SPYGLASS.get());
		hangableItems.put(MedievalItems.GOLD_SPYGLASS.get(), (HangUpItemBlock) MedievalBlocks.HANGING_GOLD_SPYGLASS.get());
	}

	public static HangUpItemBlock getReplaceable(Item item){
		return hangableItems.get(item);
	}
}
