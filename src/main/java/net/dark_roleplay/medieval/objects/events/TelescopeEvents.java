package net.dark_roleplay.medieval.objects.events;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.enums.TelescopeZoom;
import net.dark_roleplay.medieval.objects.guis.TelescopeOverlay;
import net.dark_roleplay.medieval.objects.helper.TelescopeHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class TelescopeEvents {

	public static final TelescopeOverlay telescope = new TelescopeOverlay();
	
	private static SmoothCameraStage prevSmoothCamera = SmoothCameraStage.UNKNOWN;
	private static double currentModifier = 0F;

	@SubscribeEvent
	public static void updateFov(EntityViewRenderEvent.FOVModifier event) {
		TelescopeZoom prevZoom = TelescopeHelper.getPrevZoom();
		TelescopeZoom zoom = TelescopeHelper.getCurrentZoom();

		if(prevZoom.ID < zoom.ID)
			currentModifier = Math.min(currentModifier + (event.getRenderPartialTicks() * 0.5), event.getFOV() - zoom.targetFOV);
		else if(prevZoom.ID > zoom.ID)
			currentModifier = Math.max(currentModifier - (event.getRenderPartialTicks() * 2), -0.1);

		//System.out.println(prevZoom + " -> " + zoom);

		event.setFOV(event.getFOV() - currentModifier);

		if((prevZoom != zoom) && (currentModifier < 0 || (event.getFOV() - 1 < zoom.targetFOV && event.getFOV() + 1 > zoom.targetFOV))){
			TelescopeHelper.updatePrev();
		}

		if(currentModifier < 0){
			currentModifier = 0;
			TelescopeHelper.updatePrev();
			Minecraft.getInstance().gameSettings.smoothCamera = prevSmoothCamera == SmoothCameraStage.TRUE ? true : false;
			prevSmoothCamera = SmoothCameraStage.UNKNOWN;
		}
		
		if(zoom != TelescopeZoom.NONE && SmoothCameraStage.UNKNOWN == prevSmoothCamera){
			prevSmoothCamera = Minecraft.getInstance().gameSettings.smoothCamera ? SmoothCameraStage.TRUE : SmoothCameraStage.FALSE;
			Minecraft.getInstance().gameSettings.smoothCamera = true;
		}
	}

	@SubscribeEvent
	public static void GameOverlay(RenderGameOverlayEvent.Post event){
		if(TelescopeHelper.getCurrentZoom() != TelescopeZoom.NONE){
			telescope.draw(Minecraft.getInstance());
		}
	}
	
	@SubscribeEvent
	public static void GameOverlay(RenderHandEvent event){
		if(TelescopeHelper.getCurrentZoom() != TelescopeZoom.NONE){
			event.setCanceled(true);
		}
	}
	
	private static enum SmoothCameraStage{ 
		UNKNOWN,
		FALSE,
		TRUE;
	}
}