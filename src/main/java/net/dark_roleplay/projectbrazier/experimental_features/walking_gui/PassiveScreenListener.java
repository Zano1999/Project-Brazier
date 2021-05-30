package net.dark_roleplay.projectbrazier.experimental_features.walking_gui;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.immersive_screen.DebugImmersiveScreen;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(modid= ProjectBrazier.MODID, value = Dist.CLIENT)
public class PassiveScreenListener {

	@SubscribeEvent
	public static void keyInput(InputEvent.KeyInputEvent event){
		if(BrazierKeybinds.EXP_PASSIVE_SCREEN.isKeyDown() && Minecraft.getInstance().currentScreen == null){
			Minecraft.getInstance().displayGuiScreen(new PassiveScreen()); //PassiveScreen
		}
	}
}
