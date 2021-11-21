package net.dark_roleplay.projectbrazier.feature_client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class SpyglassOverlay extends AbstractGui implements IRenderable {

	public static final SpyglassOverlay INSTANCE = new SpyglassOverlay();

	private static final ResourceLocation SPYGLASS_OVERLAY = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/spyglass_overlay.png");

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
		MainWindow window = Minecraft.getInstance().getWindow();
		int width = window.getGuiScaledWidth();
		int height = window.getGuiScaledHeight();

		Minecraft.getInstance().getTextureManager().bind(SPYGLASS_OVERLAY);

		int halfWidth = 0;
		int halfHeight = 0;
		int size = 0;

		if (width > height) {
			size = height;
			halfWidth = (width - height) / 2 + 1;
			fill(matrix, 0, 0, halfWidth, height, 0xFF000000);
			fill(matrix, width - halfWidth, 0, width, height, 0xFF000000);
		} else if (width < height) {
			size = width;
			halfHeight = (height - width) / 2;
			fill(matrix, 0, 0, width, halfHeight, 0xFF000000);
			fill(matrix, 0, height - halfHeight, width, height, 0xFF000000);
		}

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		blit(matrix, halfWidth, halfHeight, size, size, 0, 0, 256, 256, 256, 256);

		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void blitBlend(MatrixStack matrix, int p_blit_0_, int p_blit_1_, int p_blit_2_, int p_blit_3_, float p_blit_4_, float p_blit_5_, int p_blit_6_, int p_blit_7_, int p_blit_8_, int p_blit_9_) {
		innerBlitBlend(matrix, p_blit_0_, p_blit_0_ + p_blit_2_, p_blit_1_, p_blit_1_ + p_blit_3_, 0, p_blit_6_, p_blit_7_, p_blit_4_, p_blit_5_, p_blit_8_, p_blit_9_);
	}

	private static void innerBlitBlend(MatrixStack matrix, int p_innerBlit_0_, int p_innerBlit_1_, int p_innerBlit_2_, int p_innerBlit_3_, int p_innerBlit_4_, int p_innerBlit_5_, int p_innerBlit_6_, float p_innerBlit_7_, float p_innerBlit_8_, int p_innerBlit_9_, int p_innerBlit_10_) {
		innerBlitBlend(matrix, p_innerBlit_0_, p_innerBlit_1_, p_innerBlit_2_, p_innerBlit_3_, p_innerBlit_4_, (p_innerBlit_7_ + 0.0F) / (float)p_innerBlit_9_, (p_innerBlit_7_ + (float)p_innerBlit_5_) / (float)p_innerBlit_9_, (p_innerBlit_8_ + 0.0F) / (float)p_innerBlit_10_, (p_innerBlit_8_ + (float)p_innerBlit_6_) / (float)p_innerBlit_10_);
	}

	protected static void innerBlitBlend(MatrixStack matrix, int p_innerBlit_0_, int p_innerBlit_1_, int p_innerBlit_2_, int p_innerBlit_3_, int p_innerBlit_4_, float p_innerBlit_5_, float p_innerBlit_6_, float p_innerBlit_7_, float p_innerBlit_8_) {
		Matrix4f matr = matrix.last().pose();
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex(matr, (float) p_innerBlit_0_, (float) p_innerBlit_3_, (float) p_innerBlit_4_).uv(p_innerBlit_5_, p_innerBlit_8_).endVertex();
		bufferbuilder.vertex(matr, (float) p_innerBlit_1_, (float) p_innerBlit_3_, (float) p_innerBlit_4_).uv(p_innerBlit_6_, p_innerBlit_8_).endVertex();
		bufferbuilder.vertex(matr, (float) p_innerBlit_1_, (float) p_innerBlit_2_, (float) p_innerBlit_4_).uv(p_innerBlit_6_, p_innerBlit_7_).endVertex();
		bufferbuilder.vertex(matr, (float) p_innerBlit_0_, (float) p_innerBlit_2_, (float) p_innerBlit_4_).uv(p_innerBlit_5_, p_innerBlit_7_).endVertex();
		bufferbuilder.end();
		WorldVertexBufferUploader.end(bufferbuilder);
	}
}
