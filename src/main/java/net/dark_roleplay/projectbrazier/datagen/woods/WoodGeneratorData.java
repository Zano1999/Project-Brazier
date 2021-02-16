package net.dark_roleplay.projectbrazier.datagen.woods;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

public class WoodGeneratorData {
	public final Block LOG;
	public final Block STRIPPED_LOG;
	public final Block PLANK;
	public final Block WOOD;
	public final Block STRIPPED_WOOD;

	public WoodGeneratorData(RegistryObject<Block> LOG,
									 RegistryObject<Block> STRIPPED_LOG,
									 RegistryObject<Block> PLANK,
									 RegistryObject<Block> WOOD,
									 RegistryObject<Block> STRIPPED_WOOD) {
		this.LOG = LOG.get();
		this.STRIPPED_LOG = STRIPPED_LOG.get();
		this.PLANK = PLANK.get();
		this.WOOD = WOOD.get();
		this.STRIPPED_WOOD = STRIPPED_WOOD.get();
	}

	public Block getLog() {
		return LOG;
	}

	public Block getStrippedLog() {
		return STRIPPED_LOG;
	}

	public Block getPlanks() {
		return PLANK;
	}

	public Block getWood() {
		return WOOD;
	}

	public Block getStrippedWood() {
		return STRIPPED_WOOD;
	}
}
