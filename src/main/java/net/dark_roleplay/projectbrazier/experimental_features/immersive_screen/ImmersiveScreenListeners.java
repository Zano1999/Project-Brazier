package net.dark_roleplay.projectbrazier.experimental_features.immersive_screen;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ProjectBrazier.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ImmersiveScreenListeners {

	@SubscribeEvent
	public static void renderImmersiveScreen(RenderWorldLastEvent event){
		ImmersiveScreen scr = getScreen();
		if(scr == null) return;
		scr.renderInWorld(event.getContext(), event.getMatrixStack(), event.getPartialTicks());
	}

	@SubscribeEvent
	public static void adjustCameraPosition(EntityViewRenderEvent.CameraSetup event){
		ImmersiveScreen scr = getScreen();
		if(scr == null) return;

		Vector3d newCameraPos = scr.getCameraPos();
		Vector3f cameraRotation = scr.getCameraRotation();

//		event.getInfo().setPosition(newCameraPos.getX(), newCameraPos.getY(), newCameraPos.getZ());
//		event.setYaw(cameraRotation.getX());
//		event.setPitch(cameraRotation.getY());
//		event.setRoll(cameraRotation.getZ());
	}

	private static ImmersiveScreen getScreen(){
		Screen scr = Minecraft.getInstance().screen;
		if(scr == null || !(scr instanceof ImmersiveScreen)) return null;
		return (ImmersiveScreen) scr;
	}
}
