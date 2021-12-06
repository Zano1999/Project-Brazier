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
				return KeyConflictContext.IN_GAME.isActive() || Minecraft.getInstance().screen instanceof PassiveScreen ;
			}

			@Override
			public boolean conflicts(IKeyConflictContext other) {
				return this == other || other == KeyConflictContext.IN_GAME;
			}
		};

		GameSettings setting = Minecraft.getInstance().options;

		setting.keyUp.setKeyConflictContext(ctx);
		setting.keyLeft.setKeyConflictContext(ctx);
		setting.keyDown.setKeyConflictContext(ctx);
		setting.keyRight.setKeyConflictContext(ctx);
		setting.keyJump.setKeyConflictContext(ctx);
		setting.keyShift.setKeyConflictContext(ctx);
		setting.keySprint.setKeyConflictContext(ctx);
		setting.keyAttack.setKeyConflictContext(ctx);
	}
}
