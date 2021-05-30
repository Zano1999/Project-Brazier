package net.dark_roleplay.projectbrazier.feature_client.registrars;

import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.feature.items.SpyglassItem;
import net.dark_roleplay.projectbrazier.feature_client.listeners.SpyglassListeners;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class BrazierKeybinds {

	public static KeyBinding INC_ZOOM_ALT = new KeyBinding("keybind.projectbrazier.zoom.increase", KeyConflicts.ZOOM_ITEM, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UP, "keybind.category.projectbrazier.gameplay");
	public static KeyBinding DEC_ZOOM_ALT = new KeyBinding("keybind.projectbrazier.zoom.decrease", KeyConflicts.ZOOM_ITEM, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "keybind.category.projectbrazier.gameplay");

	public static KeyBinding SELECTIVE_BLOCK_ITEM_PREV = new KeyBinding("keybind.projectbrazier.selective_item.prev", KeyConflicts.SELECTIVE_ITEM, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UP, "keybind.category.projectbrazier.gameplay");
	public static KeyBinding SELECTIVE_BLOCK_ITEM_NEXT = new KeyBinding("keybind.projectbrazier.selective_item.next", KeyConflicts.SELECTIVE_ITEM, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "keybind.category.projectbrazier.gameplay");

	public static KeyBinding TER_ACTION = new KeyBinding("keybind.projectbrazier.tertiary_action", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_C, "keybind.category.projectbrazier.gameplay");

	public static KeyBinding EXP_PASSIVE_SCREEN = new KeyBinding("keybind.projectbrazier.experimental.activate", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "keybind.category.projectbrazier.gameplay");

	public enum KeyConflicts implements IKeyConflictContext {
		ZOOM_ITEM{
			@Override
			public boolean isActive() {
				return SpyglassListeners.isZoomActive();
			}

			@Override
			public boolean conflicts(IKeyConflictContext other) {return this == other;}
		},
		SELECTIVE_ITEM{
			@Override
			public boolean isActive() {
				return Minecraft.getInstance().player != null && SelectiveBlockItem.getHeldSelectiveBlockItem(Minecraft.getInstance().player) != null;
			}

			@Override
			public boolean conflicts(IKeyConflictContext other) {return this == other;}
		}
	}

	public static void registerKeybinds(FMLClientSetupEvent event){
		ClientRegistry.registerKeyBinding(INC_ZOOM_ALT);
		ClientRegistry.registerKeyBinding(DEC_ZOOM_ALT);
		ClientRegistry.registerKeyBinding(TER_ACTION);

		ClientRegistry.registerKeyBinding(SELECTIVE_BLOCK_ITEM_PREV);
		ClientRegistry.registerKeyBinding(SELECTIVE_BLOCK_ITEM_NEXT);

		//TODO Experimental!
//		ClientRegistry.registerKeyBinding(EXP_PASSIVE_SCREEN);
	}
}
