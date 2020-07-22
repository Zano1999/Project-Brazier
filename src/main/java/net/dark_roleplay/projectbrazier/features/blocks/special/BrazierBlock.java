package net.dark_roleplay.projectbrazier.features.blocks.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BrazierBlock extends Block {

	public static final BooleanProperty BURNING = BooleanProperty.create("burning");

	public BrazierBlock(Properties props) {
		super(props);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(BURNING, false);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BURNING);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getHeldItem(hand);
		if(state.get(BURNING)){
			if(heldItem.getToolTypes().contains(ToolType.SHOVEL)){
				if(world.isRemote()) return ActionResultType.SUCCESS;
				heldItem.attemptDamageItem(1, player.getRNG(), (ServerPlayerEntity) (player));
				world.setBlockState(pos, state.with(BURNING, false));
				return ActionResultType.SUCCESS;
			}
		}else{
			if(heldItem.getItem() == Items.FLINT_AND_STEEL){
				if(world.isRemote()) return ActionResultType.SUCCESS;
				heldItem.attemptDamageItem(1, player.getRNG(), (ServerPlayerEntity) (player));
				world.setBlockState(pos, state.with(BURNING, true));
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}
}
