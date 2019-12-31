package net.dark_roleplay.medieval.listeners;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.building.roofs.hacks.GeneratedRoofModel;
import net.dark_roleplay.medieval.objects.blocks.building.roofs.hacks.StraightRoofModelGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    public static void bakeModels(ModelBakeEvent event){
        StraightRoofModelGenerator generator = new StraightRoofModelGenerator(16, 16, 6, new Vec3d(0, 16, 0));


        event.getModelRegistry().put(new ModelResourceLocation("drpmedieval:oak_shingle_roof_rim#facing=north,placement=bottom,variant=normal"), new GeneratedRoofModel(generator.getBottomRim()));
        event.getModelRegistry().put(new ModelResourceLocation("drpmedieval:oak_shingle_roof_rim#facing=north,placement=left,variant=normal"), new GeneratedRoofModel(generator.getLeftRim()));
        event.getModelRegistry().put(new ModelResourceLocation("drpmedieval:oak_shingle_roof_rim#facing=north,placement=right,variant=normal"), new GeneratedRoofModel(generator.getRightRim()));
        event.getModelRegistry().put(new ModelResourceLocation("drpmedieval:oak_shingle_roof_rim#facing=north,placement=bottom_left,variant=normal"), new GeneratedRoofModel(generator.getLeftBottomRim()));
        event.getModelRegistry().put(new ModelResourceLocation("drpmedieval:oak_shingle_roof_rim#facing=north,placement=bottom_right,variant=normal"), new GeneratedRoofModel(generator.getRightBottomRim()));


        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:iron_block#"), new GeneratedRoofModel(generator.getFull()));
        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:gold_block#"), new GeneratedRoofModel(generator.getLeftRim()));
        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:emerald_block#"), new GeneratedRoofModel(generator.getRightRim()));
        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:quartz_block#"), new GeneratedRoofModel(generator.getBottomRim()));
        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:diamond_block#"), new GeneratedRoofModel(generator.getLeftBottomRim()));
        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:coal_block#"), new GeneratedRoofModel(generator.getRightBottomRim()));


        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:gold_block#"), new GeneratedRoofModel(16, 8, 5, new Vec3d(0, 16, 0)));
        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:quartz_block#"), new GeneratedRoofModel(16, 8, 5, new Vec3d(0, 8, 0)));
        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:emerald_block#"), new GeneratedRoofModel(8, 16, 5, new Vec3d(0, 16, 0)));
        //event.getModelRegistry().put(new ModelResourceLocation("minecraft:diorite#"), new GeneratedRoofModel(8, 16, 5, new Vec3d(0, 16, 8)));
    }

//    @SubscribeEvent
//    public static void textureStitching(TextureStitchEvent.Pre event){
//        if(event.getMap().getBasePath().equals("textures")){
//
//            MaterialRequirement planks = new MaterialRequirement("wood", "planks");
//            event.addSprite(new ResourceLocation("drpmedieval:blocks/timbered_clay/timbered_clay_empty"));
//
//            planks.execute(material -> {
//                for(int i = 1; i <= 15; i++){
//                    event.addSprite(new ResourceLocation(material.getTextProv().searchAndReplace("drpmedieval:block/timbered_clay/borders/${material}_") + i));
//                }
//                for(TimberedClayEnums.TimberedClayType type : TimberedClayEnums.TimberedClayType.values()) {
//                    if (type == TimberedClayEnums.TimberedClayType.CLEAN) continue;
//                    event.addSprite(new ResourceLocation(material.getTextProv().searchAndReplace("drpmedieval:block/timbered_clay/shapes/${material}_") + type.getName()));
//                }
//            });
//        }
//    }
}
