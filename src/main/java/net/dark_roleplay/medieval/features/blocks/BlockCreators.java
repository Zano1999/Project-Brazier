package net.dark_roleplay.medieval.features.blocks;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.medieval.features.blocks.templates.AxisDecoBlock;
import net.dark_roleplay.medieval.features.blocks.templates.DecoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCreators {

	public static Block createClosedBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new DecoBlock(properties, "closed_barrel");
	}


	public static Block createOpenBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new DecoBlock(properties, "open_barrel");
	}

	public static Block createWoodPlatform(IMaterial material, boolean isTop){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new AxisDecoBlock(properties, isTop ? "top_wood_platform" : "bottom_wood_platform");
	}
}
