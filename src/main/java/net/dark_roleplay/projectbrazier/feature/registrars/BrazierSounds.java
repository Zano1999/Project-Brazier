package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BrazierSounds extends Registrar {
	public static final RegistryObject<SoundEvent>
			WAR_HORN = BrazierRegistries.SOUNDS.register("war_horn", () -> new SoundEvent(new ResourceLocation(ProjectBrazier.MODID, "war_horn")));


	public static void preRegistry(){}

	public static void postRegistry(FMLCommonSetupEvent event) {}
}
