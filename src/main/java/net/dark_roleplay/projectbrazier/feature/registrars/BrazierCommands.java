package net.dark_roleplay.projectbrazier.feature.registrars;

import net.dark_roleplay.projectbrazier.feature.commands.MargHelperCommand;
import net.dark_roleplay.projectbrazier.feature.commands.SitdownCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class BrazierCommands {


	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(SitdownCommand.SITDOWN);
		if(!FMLEnvironment.production)
			event.getDispatcher().register(MargHelperCommand.MARG_GENERATE);
	}
}
