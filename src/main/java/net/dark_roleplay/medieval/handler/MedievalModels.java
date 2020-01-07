package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.marg.api.materials.BaseMaterialCondition;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MedievalModels {

    private static final IMaterialCondition plankMat = new BaseMaterialCondition("wood", "planks");

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event){

        plankMat.forEach(material -> {
            //ModelLoader.addSpecialModel(new ResourceLocation(DarkRoleplayMedieval.MODID, material.getTextProv().searchAndReplace("item/${material}_road_sign")));

            ModelLoader.addSpecialModel(new ResourceLocation(DarkRoleplayMedieval.MODID, material.getTextProvider().apply("other/simple_${material}_arrow_sign_left")));
            ModelLoader.addSpecialModel(new ResourceLocation(DarkRoleplayMedieval.MODID, material.getTextProvider().apply("other/simple_${material}_arrow_sign_right")));
        });
    }
}
