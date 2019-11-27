package net.dark_roleplay.medieval.holders;

import net.dark_roleplay.medieval.objects.configs.SkillConfig;
import org.apache.commons.lang3.tuple.Pair;

import net.dark_roleplay.medieval.one_twelve.objects.configs.ConfigWorldGen;
import net.dark_roleplay.medieval.one_twelve.objects.configs.blocks.ConfigRegeneratingOres;
import net.minecraftforge.common.ForgeConfigSpec;

public class MedievalConfigs {

	public static final ConfigWorldGen	WORLD_GEN;
	public static final ForgeConfigSpec	WORLD_GENS_SPEC;
	public static final SkillConfig SKILL_CONFIG;
	public static final ForgeConfigSpec SKILL_CONFIG_SPEC;


	static {
		Pair<ConfigWorldGen, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigWorldGen::new);
		WORLD_GEN = specPair.getLeft();
		WORLD_GENS_SPEC = specPair.getRight();

		Pair<SkillConfig, ForgeConfigSpec> skillPair = new ForgeConfigSpec.Builder().configure(SkillConfig::new);
		SKILL_CONFIG = skillPair.getLeft();
		SKILL_CONFIG_SPEC = skillPair.getRight();
	}

	public static class Misc {

		public static final ConfigRegeneratingOres	REGENERATING_ORES;
		public static final ForgeConfigSpec			REGENERATING_ORES_SPEC;
		static {
			Pair<ConfigRegeneratingOres, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigRegeneratingOres::new);
			REGENERATING_ORES = specPair.getLeft();
			REGENERATING_ORES_SPEC = specPair.getRight();
		}
	}
}
