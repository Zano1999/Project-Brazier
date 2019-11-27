package net.dark_roleplay.medieval.objects.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class SkillConfig {

    private static String DASH_LANG = "drpmedieval.config.abilities.dash.";

    public final ForgeConfigSpec.BooleanValue ALLOOW_AIRBORNE;
    public final ForgeConfigSpec.IntValue DASH_COUNT;
    public final ForgeConfigSpec.IntValue TOTAL_DASH_COUNT;
    public final ForgeConfigSpec.IntValue DASH_COOLDOWN;

    public SkillConfig(ForgeConfigSpec.Builder builder) {

        builder.comment("Some settings for the dashing ability");
        builder.push("Dash");
        {
            ALLOOW_AIRBORNE = builder
                    .comment("Can the player dash mid air?")
                    .translation(DASH_LANG + "airborn")
                    .define("airborne", true);
            DASH_COUNT = builder
                    .comment("How often can the player dash before touching the ground again?")
                    .translation(DASH_LANG + "count")
                    .defineInRange("count", 1, 1, Integer.MAX_VALUE);
            TOTAL_DASH_COUNT = builder
                    .comment("How many dtotal dashes does the player have?")
                    .translation(DASH_LANG + "count")
                    .defineInRange("count", 3, 1, Integer.MAX_VALUE);
            DASH_COOLDOWN = builder
                    .comment("How long does it take a dash to regenerate (in ticks)?")
                    .translation(DASH_LANG + "cooldown")
                    .defineInRange("cooldown", 100, 0, 24000);
        }
        builder.pop();
    }
}
