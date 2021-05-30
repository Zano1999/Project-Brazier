package net.dark_roleplay.projectbrazier.feature.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;

public class PlantSeedsItem extends BlockNamedItem implements IPlantable {

	public PlantSeedsItem(Block blockIn, Properties properties) {
		super(blockIn, properties);
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return this.getBlock().getDefaultState();
	}
}
