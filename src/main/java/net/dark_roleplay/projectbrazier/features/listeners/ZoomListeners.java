package net.dark_roleplay.projectbrazier.features.listeners;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.features.items.ZoomItem;
import net.dark_roleplay.projectbrazier.features.screens.SpyglassOverlay;
import net.dark_roleplay.projectbrazier.handler.MedievalKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT)
public class ZoomListeners {

	private static int prevZoom = 0;
	private static int zoom = 0;

	private static double prevFOV = 0;
	private static double targetFOV = 0;
	private static long deltaTimeEnd = 0;

	private static boolean initSmoothCamera = false;

	@SubscribeEvent
	public static void mouseScroll(InputEvent.MouseScrollEvent event){
		ZoomItem zoomItem = getHeldZoomItem();
		if(zoomItem != null && activateZoom()){
			double scroll = event.getScrollDelta();
			if(scroll > 0){
				increaseZoom(zoomItem);
			}else if(scroll < 0){
				decreaseZoom(zoomItem);
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void updateFov(EntityViewRenderEvent.FOVModifier event) {
		ZoomItem zoomItem = getHeldZoomItem();
		if(zoomItem != null && (zoom > 0 || deltaTimeEnd > System.currentTimeMillis())){
			event.setFOV(getNewFOV());
		}else if(zoom > 0){
			resetZoom();
			zoom = 0;
		}
	}

	@SubscribeEvent
	public static void GameOverlay(RenderGameOverlayEvent.Pre event){
		if(event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
		if(zoom > 0){
			SpyglassOverlay.INSTANCE.render(0, 0, 0);
		}
	}

	@SubscribeEvent
	public static void GameOverlay(RenderHandEvent event){
		if(zoom > 0){
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void keyInput(InputEvent.KeyInputEvent event){
		if(MedievalKeybinds.INC_ZOOM_ALT.getKeyConflictContext().isActive() && MedievalKeybinds.INC_ZOOM_ALT.isPressed()){
			ZoomItem zoomItem = getHeldZoomItem();
			if(zoomItem != null){
				increaseZoom(zoomItem);
			}
		}

		if(MedievalKeybinds.DEC_ZOOM_ALT.getKeyConflictContext().isActive() && MedievalKeybinds.DEC_ZOOM_ALT.isPressed()){
			ZoomItem zoomItem = getHeldZoomItem();
			if(zoomItem != null){
				decreaseZoom(zoomItem);
			}
		}
	}

	private static boolean activateZoom(){
		return Minecraft.getInstance().player.isCrouching() || MedievalKeybinds.ACTIVATE_ZOOM_ALT.isKeyDown();
	}

	public static void increaseZoom(ZoomItem zoomItem){
		int maxZoom = zoomItem.getZoomCount();
		if(zoom < maxZoom) {
			if(zoom == 0) setupZoom();
			prevZoom = zoom;
			zoom++;
			updateLerpHelpers(zoomItem);
		}
	}

	public static void decreaseZoom(ZoomItem zoomItem){
		if(zoom != 0){
			prevZoom = zoom;
			zoom--;
			if(zoom <= 0){
				resetZoom();
			}
			updateLerpHelpers(zoomItem);
		}
	}

	private static double getNewFOV(){
		if(deltaTimeEnd > System.currentTimeMillis()){
			return MathHelper.lerp(1F - ((deltaTimeEnd - System.currentTimeMillis()) / 250F), prevFOV, targetFOV);
		}else{
			return targetFOV;
		}
	}

	private static void updateLerpHelpers(ZoomItem zoomItem){
		if(prevZoom != 0) {
			if(deltaTimeEnd > System.currentTimeMillis()) {
				float delta = 1F - ((deltaTimeEnd - System.currentTimeMillis()) / 250F);
				prevFOV = MathHelper.lerp(delta, prevFOV, targetFOV);
			}else{
				prevFOV = targetFOV;
			}
		}else{
			prevFOV = Minecraft.getInstance().gameSettings.fov;
		}
		if(zoom > 0) targetFOV = zoomItem.getZoomFOVs()[zoom - 1];
		else targetFOV = Minecraft.getInstance().gameSettings.fov;
		deltaTimeEnd = System.currentTimeMillis() + 250;
	}

	private static ZoomItem getHeldZoomItem(){
		ItemStack stack = Minecraft.getInstance().player.getHeldItemMainhand();
		if(stack.getItem() instanceof ZoomItem)
			return (ZoomItem) stack.getItem();
		return null;
	}

	private static void setupZoom(){
		initSmoothCamera = Minecraft.getInstance().gameSettings.smoothCamera;
		Minecraft.getInstance().gameSettings.smoothCamera = true;
	}

	private static void resetZoom(){
		Minecraft.getInstance().gameSettings.smoothCamera = initSmoothCamera;
	}
}