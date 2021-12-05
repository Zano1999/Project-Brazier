package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class BrazierSounds {
	public static final RegistryObject<SoundEvent>
			WAR_HORN = BrazierRegistries.SOUNDS.register("war_horn", () -> new SoundEvent(new ResourceLocation(ProjectBrazier.MODID, "war_horn")));


	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {}
}
