package net.dark_roleplay.medieval.objects.guis.overlays;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.medieval.handler.MedievalKeybinds;
import net.dark_roleplay.medieval.util.AdvancedInteractionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class AdvancedInteractionOverlay{

    boolean wasPressed = false;
    long firstPressed = -1;

    public void reset(){
        wasPressed = false;
        firstPressed = -1;
    }

    public boolean drawInworld(Minecraft mc, Vec3d pos, AdvancedInteractionHelper.Interactor interactor){
        boolean result = false;

        long elapsed = 0;
//        if(CrafterKeybinds.BLOCK_INTERACTOR.isKeyDown()){
//            if(!wasPressed){
//                firstPressed = System.currentTimeMillis();
//                wasPressed = true;
//            }else{
//                elapsed = System.currentTimeMillis() - firstPressed;
//            }
//
//            if(elapsed > interactor.getRequiredMS()){
//                wasPressed = false;
//
//                result = true;
//
//                String[] args;
//            }
//        }else{
//            wasPressed = false;
//        }

        ActiveRenderInfo activerenderinfo = mc.gameRenderer.getActiveRenderInfo();
        double d0 = pos.squareDistanceTo(activerenderinfo.getProjectedView().x, activerenderinfo.getProjectedView().y, activerenderinfo.getProjectedView().z);
        if (!(d0 > (double)(5 * 12))) {
            float f = activerenderinfo.getYaw();
            float f1 = activerenderinfo.getPitch();

            Vec3d offset2 = activerenderinfo.getProjectedView().subtract(pos).normalize();
            Vec3d offset = pos.subtract(activerenderinfo.getProjectedView()).add(offset2.x, offset2.y, offset2.z);


            Minecraft.getInstance().gameRenderer.loadShader(new ResourceLocation("minecraft:drpmedieval/shaders/post/invert.json"));
            drawNameplate(mc.fontRenderer, I18n.format(interactor.getTooltip()), (float)offset.x, (float)offset.y, (float)offset.z, elapsed / (interactor.getRequiredMS() * 1F), f, f1);
            Minecraft.getInstance().gameRenderer.stopUseShader();
        }

        return result;
    }

    public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, float progress, float viewerYaw, float viewerPitch) {
//        GlStateManager.activeTexture(GLX.GL_TEXTURE1);
//        GlStateManager.disableTexture();
//        GlStateManager.activeTexture(GLX.GL_TEXTURE0);
//
//        GlStateManager.pushMatrix();
//        GlStateManager.translatef(x, y, z);
//        GlStateManager.normal3f(0.0F, 1.0F, 0.0F);
//        GlStateManager.rotatef(-viewerYaw, 0.0F, 1.0F, 0.0F);
//        GlStateManager.rotatef(viewerPitch, 1.0F, 0.0F, 0.0F);
//        GlStateManager.scalef(-0.0125F, -0.0125F, 0.0125F);
//        GlStateManager.disableLighting();
//        GlStateManager.depthMask(false);
//
//        GlStateManager.disableDepthTest();
//
//        GlStateManager.enableBlend();
//        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//        int i = fontRendererIn.getStringWidth(str) / 2;
//        double j = (i * 2 + 1) * progress;
//        GlStateManager.disableTexture();
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
//        float f = Minecraft.getInstance().gameSettings.func_216840_a(0.25F);
//        float f2 = (int) (Minecraft.getInstance().gameSettings.func_216840_a(0.75F) * 256);
//
//        bufferbuilder.pos((double)(-i - 1), (double)(-1), 0.0D).color(0.0F, 0.0F, 0.0F, f).endVertex();
//        bufferbuilder.pos((double)(-i - 1), (double)(8), 0.0D).color(0.0F, 0.0F, 0.0F, f).endVertex();
//        bufferbuilder.pos((double)(i + 1), (double)(8), 0.0D).color(0.0F, 0.0F, 0.0F, f).endVertex();
//        bufferbuilder.pos((double)(i + 1), (double)(-1), 0.0D).color(0.0F, 0.0F, 0.0F, f).endVertex();
//
//        float r = 35/256, g = 110/256, b = 44/256;
//
//        bufferbuilder.pos((double)(-i - 0.5), (double)(-0.5), 0.0D).color(191, 24, 91, f2).endVertex();
//        bufferbuilder.pos((double)(-i - 0.5), (double)(7.5), 0.0D).color(191, 24, 91, f2).endVertex();
//        bufferbuilder.pos((double)(-i - 0.5 + j), (double)(7.5), 0.0D).color(191, 24, 91, f2).endVertex();
//        bufferbuilder.pos((double)(-i - 0.5 + j), (double)(-0.5), 0.0D).color(191, 24, 91, f2).endVertex();
//        tessellator.draw();
//        GlStateManager.enableTexture();
//
//        fontRendererIn.drawString(str, (float)(-fontRendererIn.getStringWidth(str) / 2), 0, 553648127);
//        GlStateManager.enableDepthTest();
//
//        GlStateManager.depthMask(true);
//        fontRendererIn.drawString(str, (float)(-fontRendererIn.getStringWidth(str) / 2), 0, -1);
//        GlStateManager.enableLighting();
//        GlStateManager.disableBlend();
//        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.popMatrix();
//
//        GlStateManager.activeTexture(GLX.GL_TEXTURE1);
//        GlStateManager.enableTexture();
//        GlStateManager.activeTexture(GLX.GL_TEXTURE0);
    }
}