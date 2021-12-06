package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.WallHFacedDecoBlock;
import net.dark_roleplay.projectbrazier.feature.blockentities.HangingItemBlockEntity;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.dark_roleplay.projectbrazier.util.Inventories;
import net.dark_roleplay.projectbrazier.util.blocks.BrazierStateProperties;
import net.dark_roleplay.projectbrazier.util.capabilities.ItemHandlerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class HangingItemBlock extends WallHFacedDecoBlock implements EntityBlock {

	public static final BooleanProperty HIDDEN_LEVER = BrazierStateProperties.HIDDEN_LEVER;

	private final int power;

	public HangingItemBlock(Properties props, String shapeName, int power) {
		super(props, shapeName);
		this.power = power;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(HIDDEN_LEVER, false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HIDDEN_LEVER);
	}

//	@Override
//	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player){
//		return this.getItemStack(world, pos, state);
//	}

	@Override
	@Deprecated
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if(player.isCreative()){
			setItemStack(world, pos, state, ItemStack.EMPTY);
			return InteractionResult.SUCCESS;
		}

		ItemStack result = Inventories.givePlayerItem(player, getItemStack(world, pos, state), hand, true);
		if(result.isEmpty()){
			setItemStack(world, pos, state, ItemStack.EMPTY);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Deprecated
	public boolean isSignalSource(BlockState state) {
		return state.getValue(HIDDEN_LEVER);
	}

	@Override
	@Deprecated
	public int getSignal(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return !this.isSignalSource(state) ? 0 : power;
	}

	@Override
	@Deprecated
	public int getDirectSignal(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return !this.isSignalSource(state) ? 0 : (state.getValue(HORIZONTAL_FACING) == side) ? power : 0;
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock()))
			ItemHandlerUtil.dropContainerItems(world, pos);

		if (!isMoving && state.getBlock() != newState.getBlock()) {
			if (state.getValue(HIDDEN_LEVER)) {
				this.updateNeighbors(state, world, pos);
			}

			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	public void updateNeighbors(BlockState state, Level world, BlockPos pos) {
		world.updateNeighborsAt(pos, this);
		world.updateNeighborsAt(pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite()), this);
	}

	public ItemStack setItemStack(Level world, BlockPos pos, BlockState state, ItemStack stack){
		BlockEntity te = world.getBlockEntity(pos);
		if(!(te instanceof HangingItemBlockEntity)) return stack;

		LazyOptional<IItemHandler> inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if(!inventory.isPresent()) return stack;

		IItemHandler handler = inventory.orElse(null);
		if(handler == null) return stack;

		ItemStack old = handler.getStackInSlot(0);

		if(stack.isEmpty()){
			BlockState state2 = BrazierBlocks.NAIL.get().defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)).setValue(HIDDEN_LEVER, state.getValue(HIDDEN_LEVER));

			world.setBlockAndUpdate(pos, state2);
			return old;
		}

		return handler.insertItem(0, stack, false);
	}

	public ItemStack getItemStack(BlockGetter world, BlockPos pos, BlockState state){
		BlockEntity te = world.getBlockEntity(pos);
		if(!(te instanceof HangingItemBlockEntity)) return ItemStack.EMPTY;

		LazyOptional<IItemHandler> inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if(!inventory.isPresent())  return ItemStack.EMPTY;

		IItemHandler handler = inventory.orElse(null);
		if(handler == null) return ItemStack.EMPTY;

		return handler.getStackInSlot(0);
	}

	@org.jetbrains.annotations.Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BrazierBlockEntities.SINGLE_ITEM_STORAGE.get().create(pos, state);
	}
}
