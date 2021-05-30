package net.dark_roleplay.projectbrazier.util.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class KeybindRenderer {

	private static Map<Integer, ScreenTexture[]> SPECIAL_KEYS = new HashMap<>();

	static{
		SPECIAL_KEYS.put(GLFW.GLFW_KEY_UP, new ScreenTexture[]{
				TextureList.KEYBOARD_BUTTONS_TEXTURE.createTexture(0, 26, 13, 39),
				TextureList.KEYBOARD_BUTTONS_TEXTURE.createTexture(0, 39, 13, 52)
		});
		SPECIAL_KEYS.put(GLFW.GLFW_KEY_DOWN, new ScreenTexture[]{
				TextureList.KEYBOARD_BUTTONS_TEXTURE.createTexture(13, 26, 26, 39),
				TextureList.KEYBOARD_BUTTONS_TEXTURE.createTexture(13, 39, 26, 52)
		});
		SPECIAL_KEYS.put(GLFW.GLFW_KEY_LEFT, new ScreenTexture[]{
				TextureList.KEYBOARD_BUTTONS_TEXTURE.createTexture(26, 26, 39, 39),
				TextureList.KEYBOARD_BUTTONS_TEXTURE.createTexture(26, 39, 39, 52)
		});
		SPECIAL_KEYS.put(GLFW.GLFW_KEY_RIGHT, new ScreenTexture[]{
				TextureList.KEYBOARD_BUTTONS_TEXTURE.createTexture(39, 26, 52, 39),
				TextureList.KEYBOARD_BUTTONS_TEXTURE.createTexture(39, 39, 52, 52)
		});
	}

	public static int getKeybindWidth(KeyBinding keybind, FontRenderer fontRenderer){
		if(SPECIAL_KEYS.containsKey(keybind.getKey().getKeyCode())) return 13;
		return fontRenderer.getStringPropertyWidth(keybind.func_238171_j_()) + 7;
	}

	public static void renderKeybind(KeyBinding keybind, MatrixStack matrix, FontRenderer fontRenderer, int posX, int posY, boolean centered){
		ScreenTexture up = TextureList.KEYBOARD_BUTTON;
		ScreenTexture down = TextureList.KEYBOARD_BUTTON_PRESSED;

		boolean isSpecialKey = false;
		ScreenTexture[] textures = SPECIAL_KEYS.get(keybind.getKey().getKeyCode());
		if(textures != null){
			up = textures[0];
			down = textures[1];
			isSpecialKey = true;
		}

		boolean isPressed = Minecraft.getInstance().world.getGameTime() % 30 > 15;
		if(isSpecialKey){
			if(isPressed) up.render(matrix, posX, posY);
			else down.render(matrix, posX, posY);
		}else{
			int width = fontRenderer.getStringPropertyWidth(keybind.func_238171_j_()) + 7;
			(isPressed ? TextureList.KEYBOARD_BUTTON_PRESSED : TextureList.KEYBOARD_BUTTON).renderSegmented(matrix, posX - (centered ? (width / 2) : 0), posY, width, 13);
			fontRenderer.func_243248_b(matrix, keybind.func_238171_j_(), posX - (centered ? (width / 2) : 0) + 4, posY + ((isPressed ? 3 : 2)), 0xFFFFFFFF);
		}
	}
}
