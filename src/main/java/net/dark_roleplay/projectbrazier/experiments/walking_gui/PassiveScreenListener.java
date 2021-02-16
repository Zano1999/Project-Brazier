package net.dark_roleplay.projectbrazier.experiments.walking_gui;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.handler.MedievalKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= ProjectBrazier.MODID, value = Dist.CLIENT)
public class PassiveScreenListener {

	@SubscribeEvent
	public static void keyInput(InputEvent.KeyInputEvent event){
		if(MedievalKeybinds.EXP_PASSIVE_SCREEN.isKeyDown() && Minecraft.getInstance().currentScreen == null){
			Minecraft.getInstance().displayGuiScreen(new PassiveScreen());
		}
	}
}
