package net.dark_roleplay.projectbrazier.experimental_features.drawbridges;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectBrazier.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterSpecialModelsListener {

	@SubscribeEvent
	public static void onRegisterModels(ModelRegistryEvent event){
		for(ResourceLocation loc : DrawbridgeAnchorTileEntityRenderer.LOCATIONS)
			ModelLoader.addSpecialModel(loc);
	}

}
