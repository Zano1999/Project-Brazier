package net.dark_roleplay.projectbrazier.feature_client.registrars;

import com.mojang.blaze3d.platform.InputConstants;
import net.dark_roleplay.projectbrazier.experimental_features.selective_item_block.SelectiveBlockItem;
import net.dark_roleplay.projectbrazier.feature_client.listeners.SpyglassListeners;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class BrazierKeybinds {

	public static KeyMapping INC_ZOOM_ALT = new KeyMapping("keybind.projectbrazier.zoom.increase", KeyConflicts.ZOOM_ITEM, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UP, "keybind.category.projectbrazier.gameplay");
	public static KeyMapping DEC_ZOOM_ALT = new KeyMapping("keybind.projectbrazier.zoom.decrease", KeyConflicts.ZOOM_ITEM, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "keybind.category.projectbrazier.gameplay");

	public static KeyMapping SELECTIVE_BLOCK_ITEM_PREV = new KeyMapping("keybind.projectbrazier.selective_item.prev", KeyConflicts.SELECTIVE_ITEM, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UP, "keybind.category.projectbrazier.gameplay");
	public static KeyMapping SELECTIVE_BLOCK_ITEM_NEXT = new KeyMapping("keybind.projectbrazier.selective_item.next", KeyConflicts.SELECTIVE_ITEM, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "keybind.category.projectbrazier.gameplay");

	public static KeyMapping TER_ACTION = new KeyMapping("keybind.projectbrazier.tertiary_action", KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, "keybind.category.projectbrazier.gameplay");

	public static KeyMapping EXP_PASSIVE_SCREEN = new KeyMapping("keybind.projectbrazier.experimental.activate", KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "keybind.category.projectbrazier.gameplay");

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
//		ClientRegistry.registerKeyMapping(EXP_PASSIVE_SCREEN);
	}
}
