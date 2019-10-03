package net.dark_roleplay.medieval.objects.blocks.utility.chopping_block;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.bedrock_entities.Skeleton;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

public class ChoppingTileEntityRenderer  extends TileEntityRenderer<ChoppingTileEntity> {

    private static final ModelTest test = new ModelTest();

    @Override
    public void render(ChoppingTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y + 1, z);
        GlStateManager.scalef(0.0625F, 0.0625F, 0.0625F);
        test.render(null, 0, 0, 0, 0, 0, 1);
        GlStateManager.popMatrix();
    }
}
