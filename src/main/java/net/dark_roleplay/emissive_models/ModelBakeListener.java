package net.dark_roleplay.emissive_models;

import net.dark_roleplay.emissive_models.EmissiveBakedModel;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelBakeListener {

    private static Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> {
        assert location != null;
        return Minecraft.getInstance().getTextureMap().getAtlasSprite(location.toString());
    };

    @SubscribeEvent
    public static void bakeModels(ModelBakeEvent event){

//        IBakedModel emissiveModel = event.getModelManager().getModel(new ModelResourceLocation("minecraft:oak_sapling#stage=0"));
//        IBakedModel normalModel = event.getModelManager().getModel(new ModelResourceLocation("minecraft:oak_pressure_plate#powered=false"));
//
//        IBakedModel combined = new EmissiveBakedModel(emissiveModel, normalModel);
//        event.getModelRegistry().put(new ModelResourceLocation("minecraft:oak_planks"), combined);

    }
}
