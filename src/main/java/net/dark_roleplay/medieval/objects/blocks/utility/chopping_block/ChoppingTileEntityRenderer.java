package net.dark_roleplay.medieval.objects.blocks.utility.chopping_block;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class ChoppingTileEntityRenderer  extends TileEntityRenderer<ChoppingTileEntity> {

    public ChoppingTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(ChoppingTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
//        GlStateManager.pushMatrix();
//        GlStateManager.translated(x, y + 1, z);
//        GlStateManager.scalef(0.0625F, 0.0625F, 0.0625F);
//        test.render(null, 0, 0, 0, 0, 0, 1);
//        GlStateManager.popMatrix();
    }
}
