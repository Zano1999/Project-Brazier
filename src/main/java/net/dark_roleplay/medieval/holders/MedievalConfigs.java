package net.dark_roleplay.medieval.holders;

import net.dark_roleplay.medieval.objects.configs.SkillConfig;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class MedievalConfigs {

	public static final SkillConfig SKILL_CONFIG;
	public static final ForgeConfigSpec SKILL_CONFIG_SPEC;


	static {
		Pair<SkillConfig, ForgeConfigSpec> skillPair = new ForgeConfigSpec.Builder().configure(SkillConfig::new);
		SKILL_CONFIG = skillPair.getLeft();
		SKILL_CONFIG_SPEC = skillPair.getRight();
	}
}
