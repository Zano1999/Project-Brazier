package net.dark_roleplay.medieval.handler;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.color_handlers.SingleColorHandler;
import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MedievalColors {

    @SubscribeEvent
    public static void regColors(ColorHandlerEvent.Block event){
        for(Map.Entry<DyeColor, RegistryObject<Block>> entry: MedievalBlocks.COLORED_SHINGLE_ROOFS.entrySet()){
            event.getBlockColors().register(new SingleColorHandler(entry.getKey().getColorValue()), entry.getValue().get());
        }
    }

    @SubscribeEvent
    public static void regColors(ColorHandlerEvent.Item event){
        for(Map.Entry<DyeColor, RegistryObject<Item>> entry: MedievalItems.COLORED_SHINGLE_ROOFS.entrySet()){
            event.getItemColors().register(new SingleColorHandler(entry.getKey().getColorValue()), entry.getValue().get());
        }
    }
}
