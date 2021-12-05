package net.dark_roleplay.projectbrazier.feature_client.model_loaders.quality_model;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.util.OptifineCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ProjectBrazier.MODID)
public class QualityModelSettings {

	public static int MAX_MODEL_QUALITY = 4;
	public static int MODEL_QUALITY = 2;
	public static int TEMP_MODEL_QUALITY = 2;

//	public static final SliderButton MODEL_QUALITY_SETTING = new SliderButton("options.modelQuality", 0, MAX_MODEL_QUALITY, 1.0F,
//			(settings) -> (double) MODEL_QUALITY,
//			(settings, optionValues) -> TEMP_MODEL_QUALITY = Mth.clamp(optionValues.intValue(), 0, MAX_MODEL_QUALITY),
//			(settings, optionValues) -> new TranslatableComponent("options.modelQuality." + TEMP_MODEL_QUALITY));

	@SubscribeEvent
	public static void optionsScreenOpen(ScreenEvent.InitScreenEvent.Post event){
		if(!(event.getScreen() instanceof VideoSettingsScreen)) return;

		if(OptifineCompat.isOFLoaded()) return;

		//TODO FIX this port
		VideoSettingsScreen screen = (VideoSettingsScreen) event.getScreen();
//		screen.list.addBig(MODEL_QUALITY_SETTING);
	}

	@SubscribeEvent
	public static void optionsScreenClose(ScreenOpenEvent event){
		if(TEMP_MODEL_QUALITY != MODEL_QUALITY){
			Minecraft.getInstance().delayTextureReload();
			MODEL_QUALITY = TEMP_MODEL_QUALITY;
		}
	}
}
