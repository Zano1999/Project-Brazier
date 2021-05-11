package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.WallHFacedDecoBlock;
import net.dark_roleplay.projectbrazier.feature.blockentities.HangingItemBlockEntity;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlockEntities;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.dark_roleplay.projectbrazier.util.Inventories;
import net.dark_roleplay.projectbrazier.util.blocks.BrazierStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class HangingItemBlock extends WallHFacedDecoBlock {

	public static final BooleanProperty HIDDEN_LEVER = BrazierStateProperties.HIDDEN_LEVER;

	private final int power;

	public HangingItemBlock(Properties props, String shapeName, int power) {
		super(props, shapeName);
		this.power = power;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).with(HIDDEN_LEVER, false);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HIDDEN_LEVER);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player){
		return this.getItemStack(world, pos, state);
	}

	@Override
	@Deprecated
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if(player.isCreative()){
			setItemStack(world, pos, state, ItemStack.EMPTY);
			return ActionResultType.SUCCESS;
		}

		ItemStack result = Inventories.givePlayerItem(player, getItemStack(world, pos, state), hand, true);
		if(result.isEmpty()){
			setItemStack(world, pos, state, ItemStack.EMPTY);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Deprecated
	public boolean canProvidePower(BlockState state) {
		return state.get(HIDDEN_LEVER);
	}

	@Override
	@Deprecated
	public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return !this.canProvidePower(state) ? 0 : power;
	}

	@Override
	@Deprecated
	public int getStrongPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return !this.canProvidePower(state) ? 0 : (state.get(HORIZONTAL_FACING) == side) ? power : 0;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return BrazierBlockEntities.SINGLE_ITEM_STORAGE.get().create();
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && state.getBlock() != newState.getBlock()) {
			if (state.get(HIDDEN_LEVER)) {
				this.updateNeighbors(state, worldIn, pos);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	public void updateNeighbors(BlockState state, World world, BlockPos pos) {
		world.notifyNeighborsOfStateChange(pos, this);
		world.notifyNeighborsOfStateChange(pos.offset(state.get(HORIZONTAL_FACING).getOpposite()), this);
	}

	public ItemStack setItemStack(World world, BlockPos pos, BlockState state, ItemStack stack){
		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof HangingItemBlockEntity)) return stack;

		LazyOptional<IItemHandler> inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if(!inventory.isPresent()) return stack;

		IItemHandler handler = inventory.orElse(null);
		if(handler == null) return stack;

		ItemStack old = handler.getStackInSlot(0);

		if(stack.isEmpty()){
			BlockState state2 = BrazierBlocks.NAIL.get().getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)).with(HIDDEN_LEVER, state.get(HIDDEN_LEVER));

			world.setBlockState(pos, state2);
			return old;
		}

		return handler.insertItem(0, stack, false);
	}

	public ItemStack getItemStack(IBlockReader world, BlockPos pos, BlockState state){
		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof HangingItemBlockEntity)) return ItemStack.EMPTY;

		LazyOptional<IItemHandler> inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if(!inventory.isPresent())  return ItemStack.EMPTY;

		IItemHandler handler = inventory.orElse(null);
		if(handler == null) return ItemStack.EMPTY;

		return handler.getStackInSlot(0);
	}
}
