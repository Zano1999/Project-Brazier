package net.dark_roleplay.medieval.objects.events;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class HitboxDrawListener {

    //TODO 1.15 Update Fix
    @SubscribeEvent
    public static void drawHitbox(DrawHighlightEvent event){
//        //TODO Re-Implement IN 1.15
//        if(true) return;
//        if(event.getTarget().getType() != RayTraceResult.Type.BLOCK) return;
//
//        BlockPos hitPos = new BlockPos(event.getTarget().getHitVec());
//
//        BlockState state = Minecraft.getInstance().world.getBlockState(hitPos);
//        ItemStack heldItem = Minecraft.getInstance().player.getHeldItemMainhand();
//
//        if(state.getBlock() instanceof RoadSign && heldItem.getItem() instanceof RoadSignItem){
//
//            ResourceLocation modelLoc = RoadSignHelper.INSTANCE.isRight() ? ((RoadSignItem) heldItem.getItem()).getSignModelRight() : ((RoadSignItem) heldItem.getItem()).getSignModelLeft();
//
//            //TODO FIX IN 1.15
//            IBakedModel model =  null;//Minecraft.getInstance().getModelManager().getModel(modelLoc);
//            if(model == null) return;
//
//            RoadSignHelper.INSTANCE.displayRoadSignHud();
//
//            PlayerEntity player = Minecraft.getInstance().player;
//
//
//
//            GL11.glEnable(GL11.GL_BLEND);
//            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            GlStateManager.color4f(1,1,1, 0.5f);
//
//            Tessellator tessellator = Tessellator.getInstance();
//            BufferBuilder buffer = tessellator.getBuffer();
//            Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
//
//            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
//
//            //for(Direction dir : Direction.values()){
//                List<BakedQuad> quads = model.getQuads(null, null, new Random());
//
//                for (BakedQuad quad : quads) {
//                    LightUtil.renderQuadColor(buffer, quad, 0xFFFFFFFF);
//                }
//            //}
//
//            Entity viewEntity = Minecraft.getInstance().getRenderViewEntity();
//            Vec3d view = viewEntity.getEyePosition(event.getPartialTicks());
//
//            GlStateManager.pushMatrix();
//
//            GlStateManager.translated(
//                    hitPos.getX() - view.x + 0.5,
//                    (hitPos.getY() - view.y) + ((int)((event.getTarget().getHitVec().y - hitPos.getY()) * 16)/16f) + 0.03125F,
//                    hitPos.getZ() - view.z + 0.5);
//            GlStateManager.rotatef(-viewEntity.rotationYaw, 0.0F, 1.0F, 0.0F);
//            GlStateManager.translated(-0.5,0,-0.5);
//
//            tessellator.draw();
//
//            GlStateManager.popMatrix();
//        }
    }
}
