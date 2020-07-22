package net.dark_roleplay.projectbrazier.features.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.fml.RegistryObject;

public class LatticeItemBlock extends Item {


	protected final RegistryObject<Block> blockOuter;
	protected final RegistryObject<Block> blockCentered;

	public LatticeItemBlock(Properties props, RegistryObject<Block> blockOuter, RegistryObject<Block> blockCentered) {
		super(props);
		this.blockOuter = blockOuter;
		this.blockCentered = blockCentered;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		return ActionResultType.PASS;
	}

	protected BlockState getStateForPlacement(Block b, BlockItemUseContext context) {
		BlockState blockstate = b.getStateForPlacement(context);
		//return blockstate != null && b.canPlace(context, blockstate) ? blockstate : null;
		//TODO Implement
		return null;
	}
}
