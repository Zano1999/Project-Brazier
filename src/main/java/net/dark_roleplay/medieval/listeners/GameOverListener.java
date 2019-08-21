package net.dark_roleplay.medieval.listeners;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.holders.MedievalBlocks;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.dark_roleplay.medieval.objects.guis.RoadSignOverlay;
import net.dark_roleplay.medieval.objects.guis.overlays.AdvancedInteractionOverlay;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class GameOverListener {

    public static final RoadSignOverlay roadSignOverlay = new RoadSignOverlay();
    public static final AdvancedInteractionOverlay blockInteractionOverlay = new AdvancedInteractionOverlay();

    @SubscribeEvent
    public static void GameOverlay(RenderGameOverlayEvent.Post event){
        RayTraceResult rayTrace = Minecraft.getInstance().objectMouseOver;
        if(rayTrace.getType() == RayTraceResult.Type.BLOCK){
            BlockState hitState = Minecraft.getInstance().world.getBlockState(new BlockPos(rayTrace.getHitVec()));

            if(hitState.getBlock() == MedievalBlocks.JAIL_LATTICE){
                blockInteractionOverlay.draw(Minecraft.getInstance());
            }else if(RoadSignHelper.INSTANCE.shouldDisplayRoadSignHud()){
                roadSignOverlay.draw(Minecraft.getInstance());
            }
            //rayTrace.getHitVec()
        }


    }
}
