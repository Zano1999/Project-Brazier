package net.dark_roleplay.projectbrazier.feature.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.IPlantable;

import net.minecraft.world.item.Item.Properties;

public class PlantSeedsItem extends BlockNamedItem implements IPlantable {

	public PlantSeedsItem(Block blockIn, Properties properties) {
		super(blockIn, properties);
	}

	@Override
	public BlockState getPlant(BlockGetter world, BlockPos pos) {
		return this.getBlock().defaultBlockState();
	}
}
