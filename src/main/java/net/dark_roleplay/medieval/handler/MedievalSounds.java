package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class MedievalSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = MedievalRegistries.SOUNDS;


	public static final RegistryObject<SoundEvent>
			WAR_HORN = SOUNDS.register("war_horn", () -> new SoundEvent(new ResourceLocation(DarkRoleplayMedieval.MODID, "war_horn")));
}