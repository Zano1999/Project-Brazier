package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.commands.DRPCommand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@EventBusSubscriber(modid = DarkRoleplayMedieval.MODID)
public class MedievalCommands {

	@SubscribeEvent
	public static void serverStarting(FMLServerStartingEvent event) {
		DRPCommand.register(event.getCommandDispatcher());
	}
}
