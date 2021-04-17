package net.dark_roleplay.projectbrazier.experimental_features.walking_gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;

public class PassiveScreenHelper {

	public static void editKeybinds() {

		IKeyConflictContext ctx = new IKeyConflictContext() {
			@Override
			public boolean isActive() {
				return KeyConflictContext.IN_GAME.isActive() || Minecraft.getInstance().currentScreen instanceof PassiveScreen ;
			}

			@Override
			public boolean conflicts(IKeyConflictContext other) {
				return this == other || other == KeyConflictContext.IN_GAME;
			}
		};

		GameSettings setting = Minecraft.getInstance().gameSettings;

		setting.keyBindForward.setKeyConflictContext(ctx);
		setting.keyBindLeft.setKeyConflictContext(ctx);
		setting.keyBindBack.setKeyConflictContext(ctx);
		setting.keyBindRight.setKeyConflictContext(ctx);
		setting.keyBindJump.setKeyConflictContext(ctx);
		setting.keyBindSneak.setKeyConflictContext(ctx);
		setting.keyBindSprint.setKeyConflictContext(ctx);
		setting.keyBindAttack.setKeyConflictContext(ctx);
	}
}
