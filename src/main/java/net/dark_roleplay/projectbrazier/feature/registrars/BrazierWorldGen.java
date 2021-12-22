package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.world_gen.SurfaceLayerReplacerFeature;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class BrazierWorldGen {

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> SURFACE_LAYER_REPLACER = Registrar.registerFeature("surface_layer_replacer", () -> new SurfaceLayerReplacerFeature(NoneFeatureConfiguration.CODEC));

	public static ConfiguredFeature<?, ?> GLIMMERTAIL_CF;
	public static PlacedFeature GLIMMERTAIL_PF, DRY_CLAY_PF;

	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			GLIMMERTAIL_CF = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ProjectBrazier.MODID, "glimmertail_patch"),
					Feature.RANDOM_PATCH.configured(
							new RandomPatchConfiguration(
									96,
									7,
									3,
									() -> Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BrazierBlocks.GLIMMERTAIL.get()))).onlyWhenEmpty()
							)));

			GLIMMERTAIL_PF = Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(ProjectBrazier.MODID, "glimmertail_patch"),
					GLIMMERTAIL_CF.placed(
							RarityFilter.onAverageOnceEvery(7),
							InSquarePlacement.spread(),
							HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
							CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
							BiomeFilter.biome())
			);

			ConfiguredFeature<?, ?> DRY_CLAY_CF = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ProjectBrazier.MODID, "dry_clay_surface"),
					SURFACE_LAYER_REPLACER.get()
							.configured(FeatureConfiguration.NONE)
			);

			DRY_CLAY_PF = Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(ProjectBrazier.MODID, "dry_clay_surface"),
					DRY_CLAY_CF
							.placed(
									//BiomeFilter.biome()
							)
			);
		});
	}

	public static void preRegistry() {
	}

	public static void biomeLoad(final BiomeLoadingEvent event) {
		Biome.BiomeCategory category = event.getCategory();

		if (category == Biome.BiomeCategory.FOREST) {
			event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal(), () -> GLIMMERTAIL_PF);
		}

		if (
				category == Biome.BiomeCategory.FOREST ||
						category == Biome.BiomeCategory.PLAINS ||
						category == Biome.BiomeCategory.JUNGLE ||
						category == Biome.BiomeCategory.TAIGA ||
						category == Biome.BiomeCategory.SAVANNA
		) {
			event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION.ordinal(), () -> DRY_CLAY_PF);
		}
	}
}
