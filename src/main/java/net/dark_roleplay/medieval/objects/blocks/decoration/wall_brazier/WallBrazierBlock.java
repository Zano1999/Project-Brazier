package net.dark_roleplay.medieval.objects.blocks.decoration.wall_brazier;

import java.util.Random;

import javax.annotation.Nullable;

import net.dark_roleplay.medieval.objects.enums.TorchHolderEnums.Addons;
import net.dark_roleplay.medieval.objects.enums.TorchHolderEnums.Torch;
import net.dark_roleplay.medieval.objects.enums.properties.BrazierAddons;
import net.dark_roleplay.medieval.objects.enums.properties.BrazierState;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
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

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
//		if (!world.isRemote) {
//			if (state.get(ADDONS) == Addons.LIGHTER
//					&& (state.get(TORCH) == Torch.UNLIT || state.get(TORCH) == Torch.LIT)) {
//				if (world.isBlockPowered(pos)) {
//					world.getPendingBlockTicks().scheduleTick(pos, this, 4);
//				}
//			}
//		}
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		
		ItemStack heldItem = player.getHeldItem(hand);
		
		if(state.get(COAL_STATE) == BrazierState.EMPTY) {
			if(heldItem.getItem() == Items.COAL) {
				world.setBlockState(pos, state.with(COAL_STATE, BrazierState.COAL));
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.PLAYERS, 1F, 1F);
				if (!player.isCreative())
					heldItem.shrink(1);
				return true;
			}
		}else if(state.get(COAL_STATE) == BrazierState.COAL) {
			if(player.isSneaking()) {
				world.setBlockState(pos, state.with(COAL_STATE, BrazierState.EMPTY));
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.PLAYERS, 1F, 1F);
				if(!player.isCreative() && !player.inventory.addItemStackToInventory(new ItemStack(Items.COAL)))
					world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.COAL)));
				return true;
			}else {
				if(heldItem.getItem() == Items.FLINT_AND_STEEL) {
					world.setBlockState(pos, state.with(COAL_STATE, BrazierState.BURNING), 3);
					world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 0.9F);
					if (!player.isCreative())
						heldItem.attemptDamageItem(1, player.getRNG(), (EntityPlayerMP) player);
					return true;
				}
			}
		}else if(state.get(COAL_STATE) == BrazierState.BURNING) {
			if(player.isSneaking()) {
				world.setBlockState(pos, state.with(COAL_STATE, BrazierState.COAL));
				world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.5F);
			}
			return true;
		}
		
		return true;
	}
	

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		switch ((EnumFacing) state.get(HORIZONTAL_FACING)) {
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
	public void animateTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (state.get(COAL_STATE) == BrazierState.BURNING) {
			EnumFacing enumfacing = state.get(HORIZONTAL_FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.6D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.35D;
			EnumFacing enumfacing1 = enumfacing.getOpposite();
			for(int i = 0; i < rand.nextInt(4) + 4; i++)
				world.spawnParticle(Particles.SMOKE, d0 + d3 * (double) enumfacing1.getXOffset(), d1,
						d2 + d3 * (double) enumfacing1.getZOffset(), 0.0D, 0.03D, 0.0D);
			for(int i = 0; i < rand.nextInt(4) + 2; i++)
				world.spawnParticle(Particles.FLAME,
						((rand.nextFloat() - 0.5F) * 0.2F) + d0 + d3 * (double) enumfacing1.getXOffset(),
						(rand.nextFloat() * 0.2F) + d1,
						((rand.nextFloat() - 0.5F) * 0.2F) + d2 + d3 * (double) enumfacing1.getZOffset(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getLightValue(IBlockState state) {
		return state.get(COAL_STATE) == BrazierState.BURNING ? 14 : 0;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(HORIZONTAL_FACING, COAL_STATE, ADDONS);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Nullable
	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context) {
		EnumFacing face = context.getFace();
		if (face.getAxis() == Axis.Y)
			return null;

		BlockFaceShape shape = context.getWorld().getBlockState(context.getPos().offset(face.getOpposite()))
				.getBlockFaceShape(context.getWorld(), context.getPos().offset(face.getOpposite()), face);
		if (shape != BlockFaceShape.SOLID)
			return null;

		return this.getDefaultState().with(HORIZONTAL_FACING, face);
	}

	@Override
	public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState facingState, IWorld world,
			BlockPos currentPos, BlockPos facingPos) {
		if (facing != state.get(HORIZONTAL_FACING).getOpposite())
			return state;
		if (facingState.getBlockFaceShape(world, facingPos, facing.getOpposite()) != BlockFaceShape.SOLID)
			return Blocks.AIR.getDefaultState();
		return state;
	}

}
