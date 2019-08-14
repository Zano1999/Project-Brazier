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
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
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

    private static ResourceLocation inputEmpty = new ResourceLocation("drpmedieval:block/timbered_clay/templates/shape");
    private static ResourceLocation inputFull = new ResourceLocation("drpmedieval:block/timbered_clay/templates/full");


    @SubscribeEvent
    public static void textureStitching(TextureStitchEvent.Pre event){
        if(event.getMap().getBasePath().equals("textures")){

            MaterialRequirements planks = new MaterialRequirements(Constants.MAT_WOOD, "planks");
            event.addSprite(new ResourceLocation("drpmedieval:blocks/timbered_clay/timbered_clay_empty"));

            planks.execute(material -> {
                for(int i = 1; i <= 15; i++){
                    event.addSprite(new ResourceLocation(material.getNamed("drpmedieval:block/timbered_clay/borders/%wood%_") + i));
                }
                for(TimberedClayEnums.TimberedClayType type : TimberedClayEnums.TimberedClayType.values()) {
                    if (type == TimberedClayEnums.TimberedClayType.CLEAN) continue;
                    event.addSprite(new ResourceLocation(material.getNamed("drpmedieval:block/timbered_clay/shapes/%wood%_") + type.getName()));
                }
            });
        }
    }

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

            for(TimberedClayEnums.TimberedClayType type : TimberedClayEnums.TimberedClayType.values()) {
                if (type == TimberedClayEnums.TimberedClayType.CLEAN) continue;

                ResourceLocation name = new ResourceLocation("drpmedieval", String.format("%s_%s_timbered_clay", material.getName(), type.getName()));
                for(int i = 0; i <= 15; i++){

                    try {
                        IModel<?> timberedClayShape = ModelLoaderRegistry.getModel(inputEmpty);
                        IModel<?> timberedClayFull = ModelLoaderRegistry.getModel(inputFull);

                        IModel<?> unbaked = i == 0 ? timberedClayShape : timberedClayFull;

                        ModelResourceLocation outputLoc = new ModelResourceLocation(name, String.format("axis=x,bottom=%s,left=%s,right=%s,top=%s", (i & 4) == 4, (i & 8) == 8, (i & 2) == 2, (i & 1) == 1));
                        ModelResourceLocation outputLocB = new ModelResourceLocation(name, String.format("axis=z,bottom=%s,left=%s,right=%s,top=%s", (i & 4) == 4, (i & 8) == 8, (i & 2) == 2, (i & 1) == 1));

                        ImmutableMap.Builder<String, String> retextures = ImmutableMap.builder();

                        retextures.put("shape", material.getNamed("drpmedieval:block/timbered_clay/shapes/%wood%_") + type.getName());

                        retextures.put("border", material.getNamed("drpmedieval:block/timbered_clay/borders/%wood%_") + i);
                        if((i & 1) == 1)
                            retextures.put("top", material.getNamed("drpmedieval:block/timbered_clay/borders/%wood%_5"));
                        if((i & 2) == 2)
                            retextures.put("right", material.getNamed("drpmedieval:block/timbered_clay/borders/%wood%_10"));
                        if((i & 4) == 4)
                            retextures.put("bottom", material.getNamed("drpmedieval:block/timbered_clay/borders/%wood%_5"));
                        if((i & 8) == 8)
                            retextures.put("left", material.getNamed("drpmedieval:block/timbered_clay/borders/%wood%_10"));

                        IBakedModel retexturedTimberedClay = unbaked.retexture(retextures.build()).bake(event.getModelLoader(), textureGetter, new BasicState(unbaked.getDefaultState(), false), DefaultVertexFormats.ITEM);
                        IBakedModel retexturedTimberedClayB = unbaked.retexture(retextures.build()).bake(event.getModelLoader(), textureGetter, new BasicState(TRSRTransformation.from(ModelRotation.X0_Y90), false), DefaultVertexFormats.ITEM);

                        event.getModelRegistry().put(outputLoc, retexturedTimberedClay);
                        event.getModelRegistry().put(outputLocB, retexturedTimberedClayB);
                    } catch(Exception e){
                        //LOGGER.error("Error loading road sign Left model", e);
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
