package net.dark_roleplay.tertiary_interactor.listeners;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler.MedievalKeybinds;
import net.dark_roleplay.tertiary_interactor.RunningTertiaryInteraction;
import net.dark_roleplay.tertiary_interactor.TertiaryInteraction;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class PlayerClientTickListener {

    private static BlockPos lastCheckedPos = null;
    private static RunningTertiaryInteraction runningInteraction = null;

    @SubscribeEvent
    public static void tickClient(TickEvent.PlayerTickEvent event) {

        if (event.side == LogicalSide.SERVER) return;

//        if (MedievalKeybinds.BLOCK_INTERACTOR.isKeyDown()) {
//            BlockRayTraceResult rayTrace = (BlockRayTraceResult) Minecraft.getInstance().objectMouseOver;
//            if (Minecraft.getInstance().objectMouseOver.getType() != RayTraceResult.Type.BLOCK) return; //Not looking at a block
//
//            if (runningInteraction == null) {
//                if (lastCheckedPos != null && lastCheckedPos.equals(rayTrace.getPos())) return; //Already checked and Failed
//                if(lastCheckedPos != null) lastCheckedPos = null;
//
//                TertiaryInteraction interaction = TertiaryInteraction.getInteraction(Minecraft.getInstance().world, rayTrace, Minecraft.getInstance().player);
//
//                if (interaction == null) {
//                    //No valid Interaction found
//                    lastCheckedPos = rayTrace.getPos();
//                    return;
//                }
//                //Not pressed before, do checks and send packets
//
//            }else if(runningInteraction.isValid(rayTrace.getPos(), Minecraft.getInstance().world.getBlockState(rayTrace.getPos()))){
//                runningInteraction.tick();
//            }else{
//                runningInteraction = null;
//                lastCheckedPos = null;
//            }
//        } else {
//            if (runningInteraction != null){
//                runningInteraction = null;
//                lastCheckedPos = null;
//            }
//        }
    }
}
