package net.dark_roleplay.medieval.objects.events;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSign;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignHelper;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntityRenderer;
import net.dark_roleplay.medieval.objects.items.equipment.misc.RoadSignItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;


@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class HitboxDrawListener {

    @SubscribeEvent
    public static void drawHitbox(DrawBlockHighlightEvent event){
        if(event.getTarget().getType() != RayTraceResult.Type.BLOCK) return;

        BlockPos hitPos = new BlockPos(event.getTarget().getHitVec());

        BlockState state = Minecraft.getInstance().world.getBlockState(hitPos);
        ItemStack heldItem = Minecraft.getInstance().player.getHeldItemMainhand();

        if(state.getBlock() instanceof RoadSign && heldItem.getItem() instanceof RoadSignItem){

            ResourceLocation modelLoc = RoadSignHelper.INSTANCE.isRight() ? ((RoadSignItem) heldItem.getItem()).getSignModelRight() : ((RoadSignItem) heldItem.getItem()).getSignModelLeft();

            IBakedModel model =  RoadSignTileEntityRenderer.bakedCache.get(modelLoc);
            if(model == null) return;

            RoadSignHelper.INSTANCE.displayRoadSignHud();

            PlayerEntity player = Minecraft.getInstance().player;

            List<BakedQuad> quads = model.getQuads(null, null, new Random());


            GL11.glEnable(GL11.GL_BLEND);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color4f(1,1,1, 0.5f);

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
            for (BakedQuad quad : quads) {
                LightUtil.renderQuadColor(buffer, quad, 0xFFFFFFFF);
            }

            Entity viewEntity = Minecraft.getInstance().getRenderViewEntity();
            Vec3d view = viewEntity.getEyePosition(event.getPartialTicks());

            GlStateManager.pushMatrix();

            GlStateManager.translated(
                    hitPos.getX() - view.x + 0.5,
                    (hitPos.getY() - view.y) + ((int)((event.getTarget().getHitVec().y - hitPos.getY()) * 16)/16f) + 0.03125F,
                    hitPos.getZ() - view.z + 0.5);
            GlStateManager.rotatef(-viewEntity.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.translated(-0.5,0,-0.5);

            tessellator.draw();

            GlStateManager.popMatrix();
        }
    }
}
