package net.dark_roleplay.medieval.listeners;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.holders.MedievalBlocks;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.dark_roleplay.medieval.objects.guis.RoadSignOverlay;
import net.dark_roleplay.medieval.objects.guis.overlays.AdvancedInteractionOverlay;
import net.dark_roleplay.medieval.util.AdvancedInteractionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class GameOverListener {

    public static final RoadSignOverlay roadSignOverlay = new RoadSignOverlay();

    @SubscribeEvent
    public static void GameOverlay(RenderGameOverlayEvent.Post event){
        if(RoadSignHelper.INSTANCE.shouldDisplayRoadSignHud()){
            roadSignOverlay.draw(Minecraft.getInstance());
        }
    }
}
