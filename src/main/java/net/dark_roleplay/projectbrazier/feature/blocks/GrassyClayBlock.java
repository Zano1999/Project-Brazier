package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.mechanics.spreader.SpreadBehaviors;
import net.dark_roleplay.projectbrazier.feature.mechanics.spreader.SpreaderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;

import java.util.Random;

public class GrassyClayBlock extends GrassBlock {
	private static boolean canBeGrass(BlockState p_56824_, LevelReader p_56825_, BlockPos p_56826_) {
		BlockPos blockpos = p_56826_.above();
		BlockState blockstate = p_56825_.getBlockState(blockpos);
		if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		} else if (blockstate.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int i = LayerLightEngine.getLightBlockInto(p_56825_, p_56824_, p_56826_, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(p_56825_, blockpos));
			return i < p_56825_.getMaxLightLevel();
		}
	}

	public GrassyClayBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		if (!canBeGrass(state, level, pos)) {
			if (!level.isAreaLoaded(pos, 1)) return;
			if(SpreadBehaviors.canSpread(state, SpreaderType.REVERT))
				level.setBlockAndUpdate(pos, SpreadBehaviors.getSpreadState(state, level, pos, SpreaderType.REVERT));
		} else {
			if (!level.isAreaLoaded(pos, 3)) return;
			if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
				for(int i = 0; i < 4; ++i) {
					BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					BlockState targetState = level.getBlockState(blockpos);
					if(SpreadBehaviors.canSpread(targetState, SpreaderType.GRASS)){
						level.setBlockAndUpdate(blockpos, SpreadBehaviors.getSpreadState(targetState, level, pos, SpreaderType.GRASS));
					}
				}
			}
		}
	}
}
