package net.dark_roleplay.projectbrazier.feature_client.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.WallBurningBlock;
import net.minecraft.block.BlockState;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DisplayTickers {
	public static void animateSoulTorchHolder(BlockState state, World world, BlockPos pos, Random rand){
		animatedTypedTorchHolder(state, world, pos, rand, ParticleTypes.SOUL_FIRE_FLAME);
	}

	public static void animateTorchHolder(BlockState state, World world, BlockPos pos, Random rand){
		animatedTypedTorchHolder(state, world, pos, rand, ParticleTypes.FLAME);
	}

	private static void animatedTypedTorchHolder(BlockState state, World world, BlockPos pos, Random rand, IParticleData data){
		if(!state.get(WallBurningBlock.BURNING)) return;
		Direction dir = state.get(BlockStateProperties.HORIZONTAL_FACING);
		Direction dirOpp = dir.getOpposite();

		double x = pos.getX() + 0.5F;
		double y = pos.getY() + 0.5F;
		double z = pos.getZ() + 0.5F;

		double d0 = 0.125D;
		double d1 = 0.3125D;
		double d2 = 0.125D;

		world.addParticle(ParticleTypes.SMOKE, x + d0 * (double)dirOpp.getXOffset(), y + d1, z + d2 * (double)dirOpp.getZOffset(), 0.0D, 0.0D, 0.0D);
		world.addParticle(data, x + d0 * (double)dirOpp.getXOffset(), y + d1, z + d2 * (double)dirOpp.getZOffset(), 0.0D, 0.0D, 0.0D);
	}

	public static void animatedWallCandleHolder(BlockState state, World world, BlockPos pos, Random rand){
		if(!state.get(WallBurningBlock.BURNING)) return;
		Direction dir = state.get(BlockStateProperties.HORIZONTAL_FACING);
		Direction dirOpp = dir.getOpposite();

		double x = pos.getX() + 0.5F;
		double y = pos.getY() + 0.5F;
		double z = pos.getZ() + 0.5F;

		double d0 = 0.28125D;
		double d1 = 0.28D;
		double d2 = 0.28125D;

		if(rand.nextInt(10) == 0)
			world.addParticle(ParticleTypes.SMOKE, x + d0 * (double)dirOpp.getXOffset(), y + d1, z + d2 * (double)dirOpp.getZOffset(), 0.0D, 0.0D, 0.0D);
		world.addParticle(ParticleTypes.FLAME, x + d0 * (double)dirOpp.getXOffset(), y + d1, z + d2 * (double)dirOpp.getZOffset(), 0.0D, 0.0D, 0.0D);
	}

	public static void animatedCandleHolder(BlockState state, World world, BlockPos pos, Random rand){
		if(!state.get(WallBurningBlock.BURNING)) return;

		double x = pos.getX() + 0.5F;
		double y = pos.getY() + 0.5F;
		double z = pos.getZ() + 0.5F;

		double d0 = 0D;
		double d1 = 0.5625D;
		double d2 = 0D;

		if(rand.nextInt(10) == 0)
			world.addParticle(ParticleTypes.SMOKE, x + d0, y + d1, z + d2, 0.0D, 0.0D, 0.0D);
		world.addParticle(ParticleTypes.FLAME, x + d0, y + d1, z + d2, 0.0D, 0.0D, 0.0D);
	}
}
