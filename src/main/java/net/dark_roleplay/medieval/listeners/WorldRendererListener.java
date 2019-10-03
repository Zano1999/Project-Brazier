package net.dark_roleplay.medieval.listeners;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.dark_roleplay.medieval.objects.guis.overlays.AdvancedInteractionOverlay;
import net.dark_roleplay.medieval.util.AdvancedInteractionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class WorldRendererListener {

    private static BlockPos lastCheckedPos = null;
    private static BlockState lastCheckedState = null;
    private static AdvancedInteractionHelper.Interactor currentInteractor = null;

    //TODO Replace with in world render, not gui
    public static final AdvancedInteractionOverlay blockInteractionOverlay = new AdvancedInteractionOverlay();

    @SubscribeEvent
    public static void worldRenderer(RenderWorldLastEvent event){
        if(Minecraft.getInstance().currentScreen != null) return;
        RayTraceResult rayTrace = Minecraft.getInstance().objectMouseOver;
        if(rayTrace.getType() == RayTraceResult.Type.BLOCK){
            BlockRayTraceResult blockRayTrace = (BlockRayTraceResult) rayTrace;
            BlockState hitState = Minecraft.getInstance().world.getBlockState(blockRayTrace.getPos());

            if(blockRayTrace.getPos().equals(lastCheckedPos) && hitState.equals(lastCheckedState)){
                if(currentInteractor == null) return;

                //No need to querry, interactor, use current one
                if(blockInteractionOverlay.drawInworld(Minecraft.getInstance(), new Vec3d(blockRayTrace.getPos()).add(0.5, 0.5, 0.5), currentInteractor)){
                    currentInteractor.getAction().accept(Minecraft.getInstance().world, blockRayTrace.getPos(), hitState);
                    lastCheckedPos = null;
                    lastCheckedState = null;
                    currentInteractor = null;
                }
            }else{
                lastCheckedPos = blockRayTrace.getPos();
                lastCheckedState = hitState;

                blockInteractionOverlay.reset();
                currentInteractor = AdvancedInteractionHelper.getInteractor(Minecraft.getInstance().world, blockRayTrace.getPos(), hitState);
            }
        }
    }
}
