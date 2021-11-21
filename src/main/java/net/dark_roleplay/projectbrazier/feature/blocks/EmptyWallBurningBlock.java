package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.WallHFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.blocks.IDisplayTicker;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

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
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

		ItemStack heldItem = player.getItemInHand(hand);
		Block replacement = replacements.get(heldItem.getItem());

		if(replacement == null) return ActionResultType.PASS;
		if(world.isClientSide) return ActionResultType.SUCCESS;

		BlockState newState = replacement.defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING));
		world.setBlockAndUpdate(pos, newState);
		if(!player.isCreative())
			heldItem.shrink(1);
		return ActionResultType.SUCCESS;
	}
}
