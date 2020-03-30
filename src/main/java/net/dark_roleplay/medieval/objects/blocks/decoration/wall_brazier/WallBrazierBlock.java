package net.dark_roleplay.medieval.objects.blocks.decoration.wall_brazier;

import java.util.Random;

import javax.annotation.Nullable;

import net.dark_roleplay.medieval.objects.enums.properties.BrazierAddons;
import net.dark_roleplay.medieval.objects.enums.properties.BrazierState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WallBrazierBlock extends Block {

	private static VoxelShape EMPTY_NORTH = Block.makeCuboidShape(4, 5.25, 10.5, 12, 10.55, 16);
	private static VoxelShape EMPTY_EAST = Block.makeCuboidShape(0, 5.25, 4, 5.5, 10.55, 12);
	private static VoxelShape EMPTY_SOUTH = Block.makeCuboidShape(4, 5.25, 0, 12, 10.55, 5.5);
	private static VoxelShape EMPTY_WEST = Block.makeCuboidShape(10.5, 5.25, 4, 16, 10.55, 12);
	
	protected static final EnumProperty<BrazierState> COAL_STATE = EnumProperty.create("coal_state",
			BrazierState.class);
	protected static final EnumProperty<BrazierAddons> ADDONS = EnumProperty.create("addons",
			BrazierAddons.class);
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	public WallBrazierBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(COAL_STATE, BrazierState.EMPTY).with(ADDONS, BrazierAddons.NONE));
	}

//	@Override
//	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
//		if (!world.isRemote) {
//			if (state.get(ADDONS) == Addons.LIGHTER
//					&& (state.get(TORCH) == Torch.UNLIT || state.get(TORCH) == Torch.LIT)) {
//				if (world.isBlockPowered(pos)) {
//					world.getPendingBlockTicks().scheduleTick(pos, this, 4);
//				}
//			}
//		}
//	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if (world.isRemote) {
			return ActionResultType.SUCCESS;
		}
		
		ItemStack heldItem = player.getHeldItem(hand);
		
		if(state.get(COAL_STATE) == BrazierState.EMPTY) {
			if(heldItem.getItem() == Items.COAL) {
				world.setBlockState(pos, state.with(COAL_STATE, BrazierState.COAL));
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.PLAYERS, 1F, 1F);
				if (!player.isCreative())
					heldItem.shrink(1);
				return ActionResultType.SUCCESS;
			}
		}else if(state.get(COAL_STATE) == BrazierState.COAL) {
			if(player.isCrouching()) {
				world.setBlockState(pos, state.with(COAL_STATE, BrazierState.EMPTY));
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.PLAYERS, 1F, 1F);
				if(!player.isCreative() && !player.inventory.addItemStackToInventory(new ItemStack(Items.COAL)))
					((ServerWorld) world).summonEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.COAL)));
				return ActionResultType.SUCCESS;
			}else {
				if(heldItem.getItem() == Items.FLINT_AND_STEEL) {
					world.setBlockState(pos, state.with(COAL_STATE, BrazierState.BURNING), 3);
					world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 0.9F);
					if (!player.isCreative())
						heldItem.attemptDamageItem(1, player.getRNG(), (ServerPlayerEntity) player);
					return ActionResultType.SUCCESS;
				}
			}
		}else if(state.get(COAL_STATE) == BrazierState.BURNING) {
			if(player.isCrouching()) {
				world.setBlockState(pos, state.with(COAL_STATE, BrazierState.COAL));
				world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.5F);
			}
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.PASS;
	}
	

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		switch ((Direction) state.get(HORIZONTAL_FACING)) {
		case NORTH:
		default:
			return EMPTY_NORTH;
		case SOUTH:
			return EMPTY_SOUTH;
		case WEST:
			return EMPTY_WEST;
		case EAST:
			return EMPTY_EAST;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (state.get(COAL_STATE) == BrazierState.BURNING) {
			Direction Direction = state.get(HORIZONTAL_FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.6D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.35D;
			Direction Direction1 = Direction.getOpposite();
			for(int i = 0; i < rand.nextInt(4) + 4; i++)
				world.addParticle(ParticleTypes.SMOKE, d0 + d3 * (double) Direction1.getXOffset(), d1,
						d2 + d3 * (double) Direction1.getZOffset(), 0.0D, 0.03D, 0.0D);
			for(int i = 0; i < rand.nextInt(4) + 2; i++)
				world.addParticle(ParticleTypes.FLAME,
						((rand.nextFloat() - 0.5F) * 0.2F) + d0 + d3 * (double) Direction1.getXOffset(),
						(rand.nextFloat() * 0.2F) + d1,
						((rand.nextFloat() - 0.5F) * 0.2F) + d2 + d3 * (double) Direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getLightValue(BlockState state) {
		return state.get(COAL_STATE) == BrazierState.BURNING ? 14 : 0;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, COAL_STATE, ADDONS);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction face = context.getFace();
		if (face.getAxis() == Axis.Y)
			return null;

		if (!Block.hasEnoughSolidSide(context.getWorld(), context.getPos().offset(face.getOpposite()), face))
			return Blocks.AIR.getDefaultState();

		return this.getDefaultState().with(HORIZONTAL_FACING, face);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (facing != state.get(HORIZONTAL_FACING).getOpposite())
			return state;

		if (!Block.hasEnoughSolidSide(world, facingPos, facing))
			return Blocks.AIR.getDefaultState();
		return state;
	}

}
