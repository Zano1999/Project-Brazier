package net.dark_roleplay.projectbrazier.feature_client.listeners;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.entities.ZiplineEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ProjectBrazier.MODID)
public class RopeRenderTest {

	@SubscribeEvent
	public static void renderPlayer(RenderHandEvent event){
		if(Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle() instanceof ZiplineEntity)
			event.setCanceled(true);
	}
}
