package net.dark_roleplay.projectbrazier.feature.world_gen;

import com.mojang.serialization.Codec;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SurfaceLayerReplacerFeature extends Feature<NoneFeatureConfiguration> {

	private float trD0 = 0.6F, tr0 = 0.77F, tr1 = 0.78F, tr2 = 0.79F, tr3 = 0.8F;

	public SurfaceLayerReplacerFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		int xOffset = 0;//context.random().nextInt(1000);
		int zOffset = 0;//context.random().nextInt(1000);
		float frequency = 800;

		WorldGenLevel worldGenLevel = context.level();
		BlockPos pos = context.origin();
		BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();

		BlockState clayInGrassyDirt = BrazierBlocks.CLAY_IN_GRASSY_DIRT.get().defaultBlockState();
		BlockState clayInDirt = BrazierBlocks.CLAY_IN_DIRT.get().defaultBlockState();

		for(int x = 0; x < 16; ++x){
			for(int z = 0; z < 16; ++z){
				int x2 = pos.getX() + x;
				int z2 = pos.getZ() + z;
				float chance = SimplexNoise.noise(x2 + xOffset, z2 + zOffset, frequency);
				float chance2 = SimplexNoise.noise(x2 + xOffset + 50, z2 + zOffset + 50, 5);
				if(chance2 > trD0 && chance >= tr0 && chance < tr1) chance = tr1;

				int maxDepth = chance > tr3 ? 3 : chance >= tr2 ? 2 : chance >= tr1 ? 1 : 0;
				if(maxDepth == 0) continue;

				int y2 = worldGenLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x2, z2);
				mutPos.set(x2, y2 - 1, z2);
				Biome.BiomeCategory cat = worldGenLevel.getBiome(mutPos).getBiomeCategory();
				if(!(cat == Biome.BiomeCategory.FOREST ||
						cat == Biome.BiomeCategory.PLAINS ||
						cat == Biome.BiomeCategory.JUNGLE ||
						cat == Biome.BiomeCategory.TAIGA ||
						cat == Biome.BiomeCategory.SAVANNA)) continue;

				for(int y = 0; y < maxDepth; ++y){
					BlockState state = worldGenLevel.getBlockState(mutPos);
					boolean flag = false;
					if(state.getBlock() == Blocks.DIRT || (flag = state.getBlock() == Blocks.GRASS_BLOCK))
						worldGenLevel.setBlock(mutPos, flag ? clayInGrassyDirt : clayInDirt, 2);
					mutPos.move(0, -1, 0);
				}
			}
		}

		return true;
	}
}
