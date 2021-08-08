package net.dark_roleplay.projectbrazier.experimental_features.brewing.screen;

import net.dark_roleplay.projectbrazier.experimental_features.walking_gui.PassiveScreen;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class BrewingListeners {

	public static KeyBinding DEBUG = new KeyBinding("debug", GLFW.GLFW_KEY_J, "keybind.category.projectbrazier.gameplay");

	@SubscribeEvent
	public static void keyInput(InputEvent.KeyInputEvent event){
		if(DEBUG.isKeyDown()){
			Minecraft.getInstance().displayGuiScreen(new BrewingScreen()); //PassiveScreen
		}
	}
}
