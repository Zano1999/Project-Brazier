package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.feature.commands.SitdownCommand;
import net.minecraftforge.event.RegisterCommandsEvent;

public class BrazierCommands {


	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(SitdownCommand.SITDOWN);
	}
}
