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

public class NailBlock extends WallHFacedDecoBlock {

	public static final Map<Item, HangingItemBlock> HANGABLE_ITEMS = new HashMap<>();

	public static final BooleanProperty HIDDEN_LEVER = BrazierStateProperties.HIDDEN_LEVER;

	public NailBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState mainState = super.getStateForPlacement(context);
		return mainState != null ? mainState.with(HIDDEN_LEVER, false) : null;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HIDDEN_LEVER);
	}

	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack held = player.getHeldItem(hand);

		HangingItemBlock replacement = getReplacement(held.getItem());
		if(replacement == null) return ActionResultType.PASS;
		if(world.isRemote()) return ActionResultType.SUCCESS;

		BlockState state2 = replacement.getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)).with(HIDDEN_LEVER, state.get(HIDDEN_LEVER));

		world.setBlockState(pos, state2);
		replacement.updateNeighbors(state2, world, pos);

		ItemStack newStack = null;
		if(state2.hasTileEntity())
			newStack = replacement.setItemStack(world, pos, state, held.copy());

		if (!player.isCreative()){
			if(newStack == null) held.shrink(1);
			else player.setHeldItem(hand, newStack );
		}

		return ActionResultType.SUCCESS;
	}

	public static HangingItemBlock getReplacement(Item item) {
		return HANGABLE_ITEMS.get(item);
	}
}
