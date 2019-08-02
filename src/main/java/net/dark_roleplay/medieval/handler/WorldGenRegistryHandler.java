package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Bus.MOD)
public class WorldGenRegistryHandler {

	public static void init(FMLCommonSetupEvent event) {
		OreFeatureConfig	minableConfig	= new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_BLOCK.getDefaultState(), 20);
		CountRangeConfig	placementConfig	= new CountRangeConfig(1, 0, 0, 16);

		//ForgeRegistries.BIOMES.forEach((biome) -> biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, minableConfig, Placement.EMERALD_ORE, placementConfig)));
	}
}
