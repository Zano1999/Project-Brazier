package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.util.Direction;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.pipeline.LightUtil;

public class RoadSignTileEntityRenderer extends TileEntityRenderer<RoadSignTileEntity> {

	@Override
	public void render(RoadSignTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
		List<SignInfo> signs = tileEntity.getSigns();

		GlStateManager.pushMatrix();
		GlStateManager.translated(x + 0.5, y, z + 0.5);
		GlStateManager.color4f(1, 1, 1, 1);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		for (SignInfo sign : signs) {
			IBakedModel model = sign.isPointsLeft()
					? Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(
					"drpmedieval:other/simple_" + sign.getMaterial() + "_road_sign_left"))
					: Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(
					"drpmedieval:other/simple_" + sign.getMaterial() + "_road_sign_right"));
			if(model == null) continue;

			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);

			List<BakedQuad> quads = model.getQuads(null, null, new Random());

			for (BakedQuad quad : quads) {
				LightUtil.renderQuadColor(buffer, quad, 0xFFFFFFFF);
			}

			GlStateManager.pushMatrix();
			GlStateManager.rotatef(sign.getDirection(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translated(-0.5, sign.getHeight() * 0.0625F + 0.03125F, -0.5);

			tessellator.draw();

			GlStateManager.popMatrix();


		}
		GlStateManager.popMatrix();
		for (SignInfo sign : signs) {

			GlStateManager.pushMatrix();
			GlStateManager.translated(x + 0.5, y, z + 0.5);
			GlStateManager.rotatef(sign.getDirection(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translatef(0F, 0F, -0.26F);
			GlStateManager.scalef(-0.025F, -0.025F, 0.025F);
			int i = this.getFontRenderer().getStringWidth(sign.getText()) / 2;
			float verticalShift = -3.5f - sign.getHeight() * 2.5f - 1;

			this.getFontRenderer().drawString(sign.getText(),
					(float) (-this.getFontRenderer().getStringWidth(sign.getText()) / 2), (float) verticalShift, 0);

			GlStateManager.popMatrix();
		}
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
