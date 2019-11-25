package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

import java.util.List;

public class RoadSignTileEntityRenderer extends TileEntityRenderer<RoadSignTileEntity> {

	@Override
	public void render(RoadSignTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
		List<SignInfo> signs = tileEntity.getSigns();

		GlStateManager.pushMatrix();
		GlStateManager.translated(x + 0.5, y + 0.5, z + 0.5);
		GlStateManager.color4f(1, 1, 1, 1);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

		ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
		for (SignInfo sign : signs) {
			GlStateManager.pushMatrix();
			if(!sign.isPointsLeft())
				GlStateManager.scalef(-1, 1, -1);
			GlStateManager.rotatef(sign.getDirection(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translated(0, sign.getHeight() * 0.0625F + 0.03125F, sign.isPointsLeft() ?  0 : 0.375F);

			itemRender.renderItem(sign.getSignItem(), ItemCameraTransforms.TransformType.NONE);

			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
		for (SignInfo sign : signs) {

			float i = this.getFontRenderer().getStringWidth(sign.getText());

			//0.0625F scales the font so one pixel of font equals 1 pixel in world.
			//(1/7*5) is required to scale the 7 pixel heigh font to fit on a sign.
			float allowedHeight = 4 * Math.min(1f, 25/i);
			float pixelFactor = 0.0625F * ((1F/7)*allowedHeight);

			float verticalShift = 0.03125F + (0.0625F * sign.getHeight()); //Align text edge with center
			verticalShift += pixelFactor * 3.5f; //center text vertically


			GlStateManager.pushMatrix();
			GlStateManager.translated(x + 0.5, y + verticalShift, z + 0.5);
			GlStateManager.rotatef(sign.getDirection(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translatef(0F, 0F, -0.26F);
			GlStateManager.scalef(-pixelFactor, -pixelFactor, pixelFactor);

			this.getFontRenderer().drawString(sign.getText(),
					(float) (-i / 2), 0, 0);

			GlStateManager.popMatrix();
		}
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
