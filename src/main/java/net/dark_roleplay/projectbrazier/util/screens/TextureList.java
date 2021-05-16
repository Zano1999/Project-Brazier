package net.dark_roleplay.projectbrazier.util.screens;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.util.ResourceLocation;

public class TextureList {

	private static ScreenTextureWrapper KEYBOARD_BUTTONS_TEXTURE;
	public static ScreenTexture KEYBOARD_BUTTON, KEYBOARD_BUTTON_PRESSED;

	static{
		KEYBOARD_BUTTONS_TEXTURE = new ScreenTextureWrapper(new ResourceLocation(ProjectBrazier.MODID, "textures/screen/keyboard.png"), 76, 26);

		KEYBOARD_BUTTON = KEYBOARD_BUTTONS_TEXTURE.createTexture(0, 0, 76, 13);
		KEYBOARD_BUTTON.set9Slice(4, 72, 4, 9);

		KEYBOARD_BUTTON_PRESSED = KEYBOARD_BUTTONS_TEXTURE.createTexture(0, 13, 76, 26);
		KEYBOARD_BUTTON_PRESSED.set9Slice(4, 72, 17, 22);
	}
}
