package net.dark_roleplay.projectbrazier.feature.items;

import com.mojang.authlib.GameProfile;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;

public class PlatformBlockItem extends SelectiveBlockItem {

	protected Block[] bottomBlocks;

	public PlatformBlockItem(Block[] topBlocks, Block[] bottomBlocks, Properties builder) {
		super(topBlocks, builder);
		this.bottomBlocks = bottomBlocks;
	}

	@Override
	protected BlockState getStateForPlacement(BlockPlaceContext context) {
		double hitY = context.getClickLocation().y() - context.getClickedPos().getY();
		BlockState blockstate;
		if(hitY > 0.5F)
			blockstate = getCurrentBlock(context.getPlayer().getGameProfile()).getStateForPlacement(context);
		else
			blockstate = getCurrentBottomBlock(context.getPlayer().getGameProfile()).getStateForPlacement(context);

		return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
	}

	public Block getCurrentBottomBlock(GameProfile profile){
		return bottomBlocks[PLAYER_SELECTED.computeIfAbsent(profile, prof -> 0)];
	}
}
