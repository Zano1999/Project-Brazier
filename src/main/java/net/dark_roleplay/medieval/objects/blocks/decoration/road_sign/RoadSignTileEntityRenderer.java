package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.dark_roleplay.medieval.objects.helper.ModelsCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.pipeline.LightUtil;

public class RoadSignTileEntityRenderer extends TileEntityRenderer<RoadSignTileEntity> {

	@Override
	public void render(RoadSignTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        this.setLightmapDisabled(true);
		IBakedModel model = ModelsCache.INSTANCE.getBakedModel(new ResourceLocation("drpmedieval:other/road_sign_left"));
		
		SignInfo[] signs = new SignInfo[] { new SignInfo("Test", 0), new SignInfo("Bottom Test", 45) };



		GlStateManager.pushMatrix();

		GlStateManager.translated(x + 0.5, y, z + 0.5);

		for (int j = 0; j < 2; j++) {
			SignInfo sign = signs[j];
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
			
			List<BakedQuad> quads = model.getQuads(null, null, new Random());
			
			for(BakedQuad quad : quads) {
				LightUtil.renderQuadColorSlow(buffer, quad, 0xFFFFFFFF);
			}
			
			GlStateManager.rotatef(sign.getDirection(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translated(-0.5, + 1.75 - j * 0.5, -0.5);

			tessellator.draw();
			

			GlStateManager.translated(+0.5, - 1.75 + j * 0.5, +0.5);
			GlStateManager.rotatef(-sign.getDirection(), 0.0F, 1.0F, 0.0F);
		}
		GlStateManager.popMatrix();
		
		for (int j = 0; j < 2; j++) {

			SignInfo sign = signs[j];
			GlStateManager.pushMatrix();
			GlStateManager.translated(x + 0.5, y, z + 0.5);
			GlStateManager.normal3f(0.0F, 1.0F, 0.0F);
			GlStateManager.rotatef(sign.getDirection(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translatef(0F, 0F, -0.219F);
			GlStateManager.scalef(-0.025F, -0.025F, 0.025F);
			
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			
			int i = this.getFontRenderer().getStringWidth(sign.getText()) / 2;
			int verticalShift = -40 -34 + j * 20;

		    GlStateManager.disableLighting();
			this.getFontRenderer().drawString(sign.getText(), (float) (-this.getFontRenderer().getStringWidth(sign.getText()) / 2), (float) verticalShift, 0);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();

			//this.getFontRenderer().drawString(sign.getText(), 0, 0, 0xFFFFFFFF);
		}
        this.setLightmapDisabled(false);
	}

}
