package net.dark_roleplay.projectbrazier.feature_client.model_loaders.quality_model;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.util.OptifineCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ProjectBrazier.MODID)
public class QualityModelSettings {

	public static int MAX_MODEL_QUALITY = 4;
	public static int MODEL_QUALITY = 2;
	public static int TEMP_MODEL_QUALITY = 2;

	public static final SliderPercentageOption MODEL_QUALITY_SETTING = new SliderPercentageOption("options.modelQuality", 0, MAX_MODEL_QUALITY, 1.0F,
			(settings) -> (double) MODEL_QUALITY,
			(settings, optionValues) -> TEMP_MODEL_QUALITY = MathHelper.clamp(optionValues.intValue(), 0, MAX_MODEL_QUALITY),
			(settings, optionValues) -> new TranslationTextComponent("options.modelQuality." + TEMP_MODEL_QUALITY));

	@SubscribeEvent
	public static void optionsScreenOpen(GuiScreenEvent.InitGuiEvent.Post event){
		if(!(event.getGui() instanceof VideoSettingsScreen)) return;

		if(OptifineCompat.isOFLoaded()) return;

		VideoSettingsScreen screen = (VideoSettingsScreen) event.getGui();
		screen.list.addBig(MODEL_QUALITY_SETTING);
	}

	@SubscribeEvent
	public static void optionsScreenClose(GuiOpenEvent event){
		if(TEMP_MODEL_QUALITY != MODEL_QUALITY){
			Minecraft.getInstance().delayTextureReload();
			MODEL_QUALITY = TEMP_MODEL_QUALITY;
		}
	}
}
