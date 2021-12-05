package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.WallHFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.blocks.BrazierStateProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;
import java.util.Map;

public class NailBlock extends WallHFacedDecoBlock {

	public static final Map<Item, HangingItemBlock> HANGABLE_ITEMS = new HashMap<>();

	public static final BooleanProperty HIDDEN_LEVER = BrazierStateProperties.HIDDEN_LEVER;

	public NailBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState mainState = super.getStateForPlacement(context);
		return mainState != null ? mainState.setValue(HIDDEN_LEVER, false) : null;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HIDDEN_LEVER);
	}

	@Deprecated
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack held = player.getItemInHand(hand);

		HangingItemBlock replacement = getReplacement(held.getItem());
		if(replacement == null) return InteractionResult.PASS;
		if(world.isClientSide()) return InteractionResult.SUCCESS;

		BlockState state2 = replacement.defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)).setValue(HIDDEN_LEVER, state.getValue(HIDDEN_LEVER));

		world.setBlockAndUpdate(pos, state2);
		replacement.updateNeighbors(state2, world, pos);

		ItemStack newStack = null;
		if(state2.hasBlockEntity())
			newStack = replacement.setItemStack(world, pos, state, held.copy());

		if (!player.isCreative()){
			if(newStack == null) held.shrink(1);
			else player.setItemInHand(hand, newStack );
		}

		return InteractionResult.SUCCESS;
	}

	public static HangingItemBlock getReplacement(Item item) {
		return HANGABLE_ITEMS.get(item);
	}
}
