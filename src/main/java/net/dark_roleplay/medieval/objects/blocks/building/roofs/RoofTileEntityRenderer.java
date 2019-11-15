package net.dark_roleplay.medieval.objects.blocks.building.roofs;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.lwjgl.opengl.GL11;

public class RoofTileEntityRenderer extends TileEntityRenderer<RoofTileEntity> {

    @Override
    public void render(RoofTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        if(tileEntity == null) return;
        RoofModelHelper.RoofModel model = tileEntity.getModel();
        if(model == null) return;

        GlStateManager.pushMatrix();
        GlStateManager.translated(x - tileEntity.getPos().getX(), y - tileEntity.getPos().getY(), z - tileEntity.getPos().getZ());
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.disableLighting();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntity.getWorld(), model, tileEntity.getBlockState(), tileEntity.getPos(), buffer, true, tileEntity.getWorld().getRandom(), tileEntity.getWorld().getSeed(), EmptyModelData.INSTANCE);

        tessellator.draw();
        GlStateManager.popMatrix();
    }
}
