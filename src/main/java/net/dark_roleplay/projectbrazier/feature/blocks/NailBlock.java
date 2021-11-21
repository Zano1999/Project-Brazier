package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.WallHFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.blocks.BrazierStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.AbstractBlock.Properties;

public class NailBlock extends WallHFacedDecoBlock {

	public static final Map<Item, HangingItemBlock> HANGABLE_ITEMS = new HashMap<>();

	public static final BooleanProperty HIDDEN_LEVER = BrazierStateProperties.HIDDEN_LEVER;

	public NailBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState mainState = super.getStateForPlacement(context);
		return mainState != null ? mainState.setValue(HIDDEN_LEVER, false) : null;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HIDDEN_LEVER);
	}

	@Deprecated
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack held = player.getItemInHand(hand);

		HangingItemBlock replacement = getReplacement(held.getItem());
		if(replacement == null) return ActionResultType.PASS;
		if(world.isClientSide()) return ActionResultType.SUCCESS;

		BlockState state2 = replacement.defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)).setValue(HIDDEN_LEVER, state.getValue(HIDDEN_LEVER));

		world.setBlockAndUpdate(pos, state2);
		replacement.updateNeighbors(state2, world, pos);

		ItemStack newStack = null;
		if(state2.hasTileEntity())
			newStack = replacement.setItemStack(world, pos, state, held.copy());

		if (!player.isCreative()){
			if(newStack == null) held.shrink(1);
			else player.setItemInHand(hand, newStack );
		}

		return ActionResultType.SUCCESS;
	}

	public static HangingItemBlock getReplacement(Item item) {
		return HANGABLE_ITEMS.get(item);
	}
}
