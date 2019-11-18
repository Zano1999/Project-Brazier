package net.dark_roleplay.medieval.handler_2;

import net.dark_roleplay.marg.api.materials.MaterialRequirement;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MedievalModels {

    private static final MaterialRequirement plankMat = new MaterialRequirement("wood", "planks");

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event){
        plankMat.execute(material -> {
            //ModelLoader.addSpecialModel(new ResourceLocation(DarkRoleplayMedieval.MODID, material.getTextProv().searchAndReplace("item/${material}_road_sign")));

            ModelLoader.addSpecialModel(new ResourceLocation(DarkRoleplayMedieval.MODID, material.getTextProv().searchAndReplace("other/simple_${material}_road_sign_left")));
            ModelLoader.addSpecialModel(new ResourceLocation(DarkRoleplayMedieval.MODID, material.getTextProv().searchAndReplace("other/simple_${material}_road_sign_right")));
        });
    }
}
