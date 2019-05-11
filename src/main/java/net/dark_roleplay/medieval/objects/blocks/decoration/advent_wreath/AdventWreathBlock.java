package net.dark_roleplay.medieval.objects.blocks.decoration.advent_wreath;

import java.util.Random;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AdventWreathBlock extends Block{
	
	private static final VoxelShape HITBOX = Stream.of(
			Block.makeCuboidShape(0, 0, 0, 16, 3, 16),
			Block.makeCuboidShape(0, 3, 6.5, 3, 9, 9.5),
			Block.makeCuboidShape(13, 3, 6.5, 16, 9, 9.5),
			Block.makeCuboidShape(6.5, 3, 0, 9.5, 9, 3),
			Block.makeCuboidShape(6.5, 3, 13, 9.5, 9, 16)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	
	private static final float[][] CANDLE_POSITIONS = new float[][]{
		{0.5F, 0.75F, 0.1F},
		{0.9F, 0.75F, 0.5F},
		{0.5F, 0.75F, 0.9F},
		{0.1F, 0.75F, 0.5F}
	};
	
	public AdventWreathBlock(Properties properties) {
		super(properties);
	}

	public static final IntegerProperty BURNING_CANDLES = IntegerProperty.create("burning_candles", 0, 4);

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(BURNING_CANDLES);
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		int currentLit = state.get(BURNING_CANDLES);
		return currentLit == 0 ? 0 : 7 + (currentLit * 2);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(IBlockState state, World world, BlockPos pos, Random rand) {
		int currentLit = state.get(BURNING_CANDLES);

		for(int i = 0 ; i < currentLit; i++) {

			double d0 = pos.getX() + CANDLE_POSITIONS[i][0];
			double d1 = pos.getY() + CANDLE_POSITIONS[i][1];
			double d2 = pos.getZ() + CANDLE_POSITIONS[i][2];

			world.spawnParticle(Particles.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(Particles.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Nullable
	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(BURNING_CANDLES, 0);
	}
	
	@Override
	public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (facing == EnumFacing.DOWN && facingState.getBlockFaceShape(world, facingPos, EnumFacing.UP) != BlockFaceShape.SOLID)
			return Blocks.AIR.getDefaultState();
		return state;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return HITBOX;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
	      return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			int currentLit = state.get(BURNING_CANDLES);
			if(currentLit > 0) {
				world.setBlockState(pos, state.with(BURNING_CANDLES, --currentLit));
				world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.5F);
				return true;
			}
		}
		
		if(player.getHeldItem(hand).getItem() != Items.FLINT_AND_STEEL) return false;
		
		int currentLit = state.get(BURNING_CANDLES);
		if(currentLit < 4) {
			world.setBlockState(pos, state.with(BURNING_CANDLES, ++currentLit));
			world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 0.9F);
			return true;
		}
		
		return false;
	}
}