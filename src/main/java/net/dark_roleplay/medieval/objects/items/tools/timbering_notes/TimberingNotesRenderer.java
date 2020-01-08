package net.dark_roleplay.medieval.objects.items.tools.timbering_notes;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class TimberingNotesRenderer extends AbstractGui {

    public static TimberingNotesRenderer INSTANCE = new TimberingNotesRenderer();

    public void renderHud(ItemStack stack){
        CompoundNBT tag = stack.getOrCreateTag();
        boolean editB = tag.getBoolean("EditPosB");

        int topX = (int) Math.ceil(Minecraft.getInstance().mainWindow.getScaledWidth() / 2F) - 7;
        int topY = (int) Math.ceil(Minecraft.getInstance().mainWindow.getScaledHeight() / 2F) - 7;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.depthMask(false);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(1, 1, 1, 0);

        simpleFill(topX, topY, topX + 1, topY + 13);
        simpleFill(topX + 12, topY, topX + 13, topY + 13);
        simpleFill(topX + 1, topY, topX + 12, topY + 1);
        simpleFill(topX + 1, topY + 12, topX + 12, topY + 13);

        if(editB)
            simpleFill(topX + 1, topY + 8, topX + 5, topY + 12);
        else
            simpleFill(topX + 8, topY + 1, topX + 12, topY + 5);

        GL11.glLineWidth(1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableTexture();

    }

    public void renderWorld(ItemStack stack){
        CompoundNBT tag = stack.getOrCreateTag();

        TimberingData data = new TimberingData();
        data.deserializeNBT(tag.getCompound("TimberingData"));
        if(data.getPosA() == null || data.getPosB() == null) return;


        ActiveRenderInfo activerenderinfo =  Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        Vec3d posA = new Vec3d(data.getPosA()).subtract(activerenderinfo.getProjectedView());
        Vec3d posB = new Vec3d(data.getPosB()).subtract(activerenderinfo.getProjectedView());

        Vec3d minPos = new Vec3d(Math.min(posA.getX(), posB.getX()), Math.min(posA.getY(), posB.getY()), Math.min(posA.getZ(), posB.getZ()));
        Vec3d maxPos = new Vec3d(Math.max(posA.getX(), posB.getX()), Math.max(posA.getY(), posB.getY()), Math.max(posA.getZ(), posB.getZ()));

        //minPos = minPos.subtract(activerenderinfo.getProjectedView());
        //maxPos = maxPos.subtract(activerenderinfo.getProjectedView());

        GlStateManager.disableTexture();
        GL11.glLineWidth(2.0F);

        renderBox(minPos, maxPos.add(1, 1, 1), 0, 0, 1, 1);
        renderBox(posA, posA.add(1, 1, 1), 1, 0, 0, 1);
        renderBox(posB, posB.add(1, 1, 1), 0, 1, 0, 1);

        GL11.glLineWidth(1.0F);
        GlStateManager.enableTexture();
    }

    private void renderBox(Vec3d minPos, Vec3d maxPos, float r, float g, float b, float a){
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);

        bufferbuilder.pos(minPos.getX(), minPos.getY(), minPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(maxPos.getX(), minPos.getY(), minPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(maxPos.getX(), minPos.getY(), maxPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(minPos.getX(), minPos.getY(), maxPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(minPos.getX(), minPos.getY(), minPos.getZ()).color(r, g, b, a).endVertex();

        bufferbuilder.pos(minPos.getX(), maxPos.getY(), minPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(maxPos.getX(), maxPos.getY(), minPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(maxPos.getX(), maxPos.getY(), maxPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(minPos.getX(), maxPos.getY(), maxPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(minPos.getX(), maxPos.getY(), minPos.getZ()).color(r, g, b, a).endVertex();


        bufferbuilder.pos(maxPos.getX(), maxPos.getY(), minPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(maxPos.getX(), minPos.getY(), minPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(maxPos.getX(), minPos.getY(), maxPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(maxPos.getX(), maxPos.getY(), maxPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(minPos.getX(), maxPos.getY(), maxPos.getZ()).color(r, g, b, a).endVertex();
        bufferbuilder.pos(minPos.getX(), minPos.getY(), maxPos.getZ()).color(r, g, b, a).endVertex();

        tessellator.draw();
    }



    public static void simpleFill(int x1, int y1, int x2, int y2) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)x1, (double)y2, 0.0D).endVertex();
        bufferbuilder.pos((double)x2, (double)y2, 0.0D).endVertex();
        bufferbuilder.pos((double)x2, (double)y1, 0.0D).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, 0.0D).endVertex();
        tessellator.draw();
    }
}
