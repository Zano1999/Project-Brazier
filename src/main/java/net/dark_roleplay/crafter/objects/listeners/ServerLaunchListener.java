package net.dark_roleplay.crafter.objects.listeners;

import net.dark_roleplay.crafter.objects.reload_listeners.RecipeController;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerLaunchListener {

    @SubscribeEvent
    public static void serverStarting(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(RecipeController.INSTANCE);
    }
}
