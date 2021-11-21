package net.dark_roleplay.projectbrazier.feature_client.listeners;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.items.SpyglassItem;
import net.dark_roleplay.projectbrazier.feature_client.screens.SpyglassOverlay;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.test.TestCommand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT)
public class SpyglassListeners {

	private static int prevZoom = 0;
	private static int zoom = 0;

	private static double prevFOV = 0;
	private static double targetFOV = 0;
	private static long deltaTimeEnd = 0;

	private static boolean initSmoothCamera = false;

	private static boolean isZoomActive = false;

	@SubscribeEvent
	public static void mouseScroll(InputEvent.MouseScrollEvent event){
		SpyglassItem spyglassItem = getHeldZoomItem();
		if(isZoomActive()){
			double scroll = event.getScrollDelta();
			if(scroll > 0){
				increaseZoom(spyglassItem);
			}else if(scroll < 0){
				decreaseZoom(spyglassItem);
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void updateFov(EntityViewRenderEvent.FOVModifier event) {
		SpyglassItem spyglassItem = getHeldZoomItem();
		if(spyglassItem != null && (isZoomActive() || deltaTimeEnd > System.currentTimeMillis())){
			event.setFOV(getNewFOV());
		}else if(zoom > 0){
			resetZoom();
			zoom = 0;
		}
	}

	@SubscribeEvent
	public static void GameOverlay(RenderGameOverlayEvent.Pre event){
		if(event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
		if(isZoomActive() || deltaTimeEnd > System.currentTimeMillis()){
			SpyglassOverlay.INSTANCE.render(event.getMatrixStack(), 0, 0, 0);
		}
	}

	@SubscribeEvent
	public static void GameOverlay(RenderHandEvent event){
		if(isZoomActive()){
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void keyInput(InputEvent.KeyInputEvent event){
		if(BrazierKeybinds.INC_ZOOM_ALT.getKeyConflictContext().isActive() && BrazierKeybinds.INC_ZOOM_ALT.consumeClick()){
			SpyglassItem spyglassItem = getHeldZoomItem();
			if(spyglassItem != null){
				increaseZoom(spyglassItem);
			}
		}

		if(BrazierKeybinds.DEC_ZOOM_ALT.getKeyConflictContext().isActive() && BrazierKeybinds.DEC_ZOOM_ALT.consumeClick()){
			SpyglassItem spyglassItem = getHeldZoomItem();
			if(spyglassItem != null){
				decreaseZoom(spyglassItem);
			}
		}
	}

	public static boolean isZoomActive(){
		return isZoomActive = isZoomActive && getHeldZoomItem() != null;
	}

	public static void toogleZoom(){
		SpyglassItem spyglass;
		if((spyglass = getHeldZoomItem()) != null) {
			if(isZoomActive){
				isZoomActive = false;
				prevZoom = zoom;
				zoom = 0;
				updateLerpHelpers(spyglass);
				resetZoom();
			}else{
				isZoomActive = true;
				increaseZoom(spyglass);
			}
		}
	}

	public static void increaseZoom(SpyglassItem spyglassItem){
		int maxZoom = spyglassItem.getZoomCount();
		if(zoom < maxZoom) {
			if(zoom == 0) setupZoom();
			prevZoom = zoom;
			zoom++;
			updateLerpHelpers(spyglassItem);
		}
	}

	public static void decreaseZoom(SpyglassItem spyglassItem){
		if(zoom != 0){
			prevZoom = zoom;
			zoom--;
			if(zoom <= 0){
				resetZoom();
			}
			updateLerpHelpers(spyglassItem);
		}
	}

	private static double getNewFOV(){
		if(deltaTimeEnd > System.currentTimeMillis()){
			return MathHelper.lerp(1F - ((deltaTimeEnd - System.currentTimeMillis()) / 250F), prevFOV, targetFOV);
		}else{
			return targetFOV;
		}
	}

	private static void updateLerpHelpers(SpyglassItem spyglassItem){
		if(prevZoom != 0) {
			if(deltaTimeEnd > System.currentTimeMillis()) {
				float delta = 1F - ((deltaTimeEnd - System.currentTimeMillis()) / 250F);
				prevFOV = MathHelper.lerp(delta, prevFOV, targetFOV);
			}else{
				prevFOV = targetFOV;
			}
		}else{
			prevFOV = Minecraft.getInstance().options.fov;
		}
		if(zoom > 0) targetFOV = spyglassItem.getZoomFOVs()[zoom - 1];
		else targetFOV = Minecraft.getInstance().options.fov;
		deltaTimeEnd = System.currentTimeMillis() + 250;
	}

	private static SpyglassItem getHeldZoomItem(){
		ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
		if(stack.getItem() instanceof SpyglassItem)
			return (SpyglassItem) stack.getItem();
		stack = Minecraft.getInstance().player.getOffhandItem();
		if(stack.getItem() instanceof SpyglassItem)
			return (SpyglassItem) stack.getItem();

		return null;
	}

	private static void setupZoom(){
		initSmoothCamera = Minecraft.getInstance().options.smoothCamera;
		Minecraft.getInstance().options.smoothCamera = true;
	}

	private static void resetZoom(){
		Minecraft.getInstance().options.smoothCamera = initSmoothCamera;
	}
}