package net.dark_roleplay.project_brazier.handler;

import net.dark_roleplay.project_brazier.ProjectBrazier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class MedievalSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = MedievalRegistries.SOUNDS;


	public static final RegistryObject<SoundEvent>
			WAR_HORN = SOUNDS.register("war_horn", () -> new SoundEvent(new ResourceLocation(ProjectBrazier.MODID, "war_horn")));
}