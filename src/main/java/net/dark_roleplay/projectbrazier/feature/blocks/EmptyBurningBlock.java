package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;
import java.util.Map;

public class EmptyBurningBlock extends DecoBlock{
	private Map<Item, Block> replacements = new HashMap<>();

	public EmptyBurningBlock(BlockBehaviour.Properties props, String shapeName) {
		super(props, shapeName);
	}

	public void addItem(Item item, Block replacement){
		this.replacements.put(item, replacement);
		if(replacement instanceof BurningBlock)
			((BurningBlock) replacement).setItem(item, this);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		ItemStack heldItem = player.getItemInHand(hand);
		Block replacement = replacements.get(heldItem.getItem());

		if(replacement == null) return InteractionResult.PASS;
		if(world.isClientSide) return InteractionResult.SUCCESS;

		BlockState newState = replacement.defaultBlockState();
		world.setBlockAndUpdate(pos, newState);
		if(!player.isCreative())
			heldItem.shrink(1);
		return InteractionResult.SUCCESS;
	}
}