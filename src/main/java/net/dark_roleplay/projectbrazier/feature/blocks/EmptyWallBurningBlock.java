package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.WallHFacedDecoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class EmptyWallBurningBlock extends WallHFacedDecoBlock {

	private Map<Item, Block> replacements = new HashMap<>();

	public EmptyWallBurningBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	public void addItem(Item item, Block replacement){
		this.replacements.put(item, replacement);
		if(replacement instanceof WallBurningBlock)
			((WallBurningBlock) replacement).setItem(item, this);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

		ItemStack heldItem = player.getHeldItem(hand);
		Block replacement = replacements.get(heldItem.getItem());

		if(replacement == null) return ActionResultType.PASS;
		if(world.isRemote) return ActionResultType.SUCCESS;

		BlockState newState = replacement.getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING));
		world.setBlockState(pos, newState);
		if(!player.isCreative())
			heldItem.shrink(1);
		return ActionResultType.SUCCESS;
	}
}
