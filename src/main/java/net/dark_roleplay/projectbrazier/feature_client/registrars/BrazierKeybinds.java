package net.dark_roleplay.projectbrazier.feature_client.registrars;

import net.dark_roleplay.projectbrazier.feature.items.SpyglassItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class BrazierKeybinds {

	public static KeyBinding INC_ZOOM_ALT = new KeyBinding("keybind.drpmedieval.zoom.increase", KeyConflicts.ZOOM_ITEM, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, "drpmedieval.gameplay");
	public static KeyBinding DEC_ZOOM_ALT = new KeyBinding("keybind.drpmedieval.zoom.decrease", KeyConflicts.ZOOM_ITEM, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, "drpmedieval.gameplay");
	public static KeyBinding ACTIVATE_ZOOM_ALT = new KeyBinding("keybind.drpmedieval.zoom.activate", KeyConflicts.ZOOM_ITEM, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "drpmedieval.gameplay");

	public static KeyBinding TER_ACTION = new KeyBinding("keybind.drpmedieval.tertiary_action", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_C, "drpmedieval.gameplay");

	public static KeyBinding EXP_PASSIVE_SCREEN = new KeyBinding("keybind.drpmedieval.experimental.activate", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "drpmedieval.gameplay");

	public enum KeyConflicts implements IKeyConflictContext {
		ZOOM_ITEM{
			@Override
			public boolean isActive() {return Minecraft.getInstance().player != null && Minecraft.getInstance().player.getHeldItemMainhand().getItem() instanceof SpyglassItem;}

			@Override
			public boolean conflicts(IKeyConflictContext other) {return this == other;}
		};
	}

	public static void registerKeybinds(FMLClientSetupEvent event){
		ClientRegistry.registerKeyBinding(INC_ZOOM_ALT);
		ClientRegistry.registerKeyBinding(DEC_ZOOM_ALT);
		ClientRegistry.registerKeyBinding(ACTIVATE_ZOOM_ALT);
		ClientRegistry.registerKeyBinding(TER_ACTION);

		//TODO Experimental!
//		ClientRegistry.registerKeyBinding(EXP_PASSIVE_SCREEN);
	}
}
