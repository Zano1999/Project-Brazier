package net.dark_roleplay.projectbrazier.util.screens;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.resources.ResourceLocation;

public class TextureList {

	public static ScreenTextureWrapper KEYBOARD_BUTTONS_TEXTURE;
	public static ScreenTexture KEYBOARD_BUTTON, KEYBOARD_BUTTON_PRESSED;

	private static ScreenTextureWrapper SELECTIVE_BLOCK_ITEMS_TEXTURE;
	public static ScreenTexture SELECTIVE_BLOCK_ITEMS;

	static{
		KEYBOARD_BUTTONS_TEXTURE = new ScreenTextureWrapper(new ResourceLocation(ProjectBrazier.MODID, "textures/screen/keyboard.png"), 76, 52);

		KEYBOARD_BUTTON = KEYBOARD_BUTTONS_TEXTURE.createTexture(0, 0, 76, 13);
		KEYBOARD_BUTTON.set9Slice(4, 72, 4, 9);

		KEYBOARD_BUTTON_PRESSED = KEYBOARD_BUTTONS_TEXTURE.createTexture(0, 13, 76, 26);
		KEYBOARD_BUTTON_PRESSED.set9Slice(4, 72, 17, 22);

		SELECTIVE_BLOCK_ITEMS_TEXTURE = new ScreenTextureWrapper(new ResourceLocation(ProjectBrazier.MODID, "textures/screen/selective_block_item.png"), 30, 70);
		SELECTIVE_BLOCK_ITEMS = SELECTIVE_BLOCK_ITEMS_TEXTURE.createTexture(0, 0, 30, 70);
	}
}
