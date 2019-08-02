package net.dark_roleplay.medieval.objects.events;

import com.google.common.collect.ImmutableMap;
import net.dark_roleplay.marg.api.Constants;
import net.dark_roleplay.marg.api.MaterialRequirements;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntityRenderer;
import net.dark_roleplay.medieval.objects.enums.TimberedClayEnums;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;


@EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelBakeListener {

    private static Logger LOGGER = LogManager.getLogger();

    private static Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> {
        assert location != null;
        return Minecraft.getInstance().getTextureMap().getAtlasSprite(location.toString());
    };

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event){
        MaterialRequirements planks = new MaterialRequirements(Constants.MAT_WOOD, "planks");

        planks.execute(material -> {
            try {
                IModel<?> roadSignLeft = ModelLoaderRegistry.getModel(new ResourceLocation(material.getNamed("drpmedieval:other/road_signs/%wood%_road_sign_left.obj")));

                RoadSignTileEntityRenderer.bakedCache.put(
                        new ResourceLocation(material.getNamed("drpmedieval:%wood%_road_sign_left.obj")),
                        roadSignLeft.bake(event.getModelLoader(), textureGetter, new BasicState(roadSignLeft.getDefaultState(), false), DefaultVertexFormats.ITEM));
            }
            catch(Exception e){
                //LOGGER.error("Error loading road sign Left model", e);
            }

            try {
                IModel<?> roadSignRight = ModelLoaderRegistry.getModel(new ResourceLocation(material.getNamed("drpmedieval:other/road_signs/%wood%_road_sign_right.obj")));

                RoadSignTileEntityRenderer.bakedCache.put(
                        new ResourceLocation(material.getNamed("drpmedieval:%wood%_road_sign_right.obj")),
                        roadSignRight.bake(event.getModelLoader(), textureGetter, new BasicState(roadSignRight.getDefaultState(), false), DefaultVertexFormats.ITEM));
            }
            catch(Exception e){
                //LOGGER.error("Error loading road sign right model", e);
            }
            /*for(TimberedClayEnums.TimberedClayType type : TimberedClayEnums.TimberedClayType.values()) {
                if (type == TimberedClayEnums.TimberedClayType.CLEAN) continue;



                ResourceLocation name = new ResourceLocation("drpmedieval", String.format("%s_%s_timbered_clay", material.getName(), type.getName()));
                for(int i = 1; i <= 15; i++){
                    ModelResourceLocation modelLoc = new ModelResourceLocation(name, String.format("top=%s,right=%s,bottom=%s,left=%s", (i & 1) == 1, (i & 2) == 2, (i & 4) == 4, (i & 8) == 8));

                    IModel<?> unbakedModel = ModelLoaderRegistry.getModelOrLogError(modelLoc, "Error loading fence model");

                    ImmutableMap.Builder<String, String> retextures = ImmutableMap.builder();

                    retextures.put("border", "block/timbered_clay/borders/%wood%_" + i);
                    if((i & 1) == 1)
                        retextures.put("top", "block/timbered_clay/borders/%wood%_5");
                    if((i & 2) == 2)
                        retextures.put("right", "block/timbered_clay/borders/%wood%_10");
                    if((i & 4) == 4)
                        retextures.put("bottom", "block/timbered_clay/borders/%wood%_5");
                    if((i & 8) == 8)
                        retextures.put("left", "block/timbered_clay/borders/%wood%_10");

                    IBakedModel fenceResult = unbakedModel.retexture(retextures.build()).bake(event.getModelLoader(), textureGetter, new BasicState(unbakedModel.getDefaultState(), false), DefaultVertexFormats.ITEM);
                    event.getModelRegistry().put(modelLoc, fenceResult);
                }
            }*/
        });


    }
}
