package net.dark_roleplay.projectbrazier.features.screens;

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

public class SpyglassOverlay extends AbstractGui implements IRenderable {

	public static final SpyglassOverlay INSTANCE = new SpyglassOverlay();

	private static final ResourceLocation SPYGLASS_OVERLAY = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/spyglass_overlay.png");

	@Override
	public void render(int mouseX, int mouseY, float delta) {
		MainWindow window = Minecraft.getInstance().getMainWindow();
		int width = window.getScaledWidth();
		int height = window.getScaledHeight();

		Minecraft.getInstance().getTextureManager().bindTexture(SPYGLASS_OVERLAY);

		int halfWidth = 0;
		int halfHeight = 0;
		int size = 0;

		if (width > height) {
			size = height;
			halfWidth = (width - height) / 2 + 1;
			fill(0, 0, halfWidth, height, 0xFF000000);
			fill(width - halfWidth, 0, width, height, 0xFF000000);
		} else if (width < height) {
			size = width;
			halfHeight = (height - width) / 2;
			fill(0, 0, width, halfHeight, 0xFF000000);
			fill(0, height - halfHeight, width, height, 0xFF000000);
		}

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		blit(halfWidth, halfHeight, size, size, 0, 0, 256, 256, 256, 256);

		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void blitBlend(int p_blit_0_, int p_blit_1_, int p_blit_2_, int p_blit_3_, float p_blit_4_, float p_blit_5_, int p_blit_6_, int p_blit_7_, int p_blit_8_, int p_blit_9_) {
		innerBlitBlend(p_blit_0_, p_blit_0_ + p_blit_2_, p_blit_1_, p_blit_1_ + p_blit_3_, 0, p_blit_6_, p_blit_7_, p_blit_4_, p_blit_5_, p_blit_8_, p_blit_9_);
	}

	private static void innerBlitBlend(int p_innerBlit_0_, int p_innerBlit_1_, int p_innerBlit_2_, int p_innerBlit_3_, int p_innerBlit_4_, int p_innerBlit_5_, int p_innerBlit_6_, float p_innerBlit_7_, float p_innerBlit_8_, int p_innerBlit_9_, int p_innerBlit_10_) {
		innerBlit(p_innerBlit_0_, p_innerBlit_1_, p_innerBlit_2_, p_innerBlit_3_, p_innerBlit_4_, (p_innerBlit_7_ + 0.0F) / (float)p_innerBlit_9_, (p_innerBlit_7_ + (float)p_innerBlit_5_) / (float)p_innerBlit_9_, (p_innerBlit_8_ + 0.0F) / (float)p_innerBlit_10_, (p_innerBlit_8_ + (float)p_innerBlit_6_) / (float)p_innerBlit_10_);
	}

	protected static void innerBlitBlend(int p_innerBlit_0_, int p_innerBlit_1_, int p_innerBlit_2_, int p_innerBlit_3_, int p_innerBlit_4_, float p_innerBlit_5_, float p_innerBlit_6_, float p_innerBlit_7_, float p_innerBlit_8_) {
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) p_innerBlit_0_, (double) p_innerBlit_3_, (double) p_innerBlit_4_).tex(p_innerBlit_5_, p_innerBlit_8_).endVertex();
		bufferbuilder.pos((double) p_innerBlit_1_, (double) p_innerBlit_3_, (double) p_innerBlit_4_).tex(p_innerBlit_6_, p_innerBlit_8_).endVertex();
		bufferbuilder.pos((double) p_innerBlit_1_, (double) p_innerBlit_2_, (double) p_innerBlit_4_).tex(p_innerBlit_6_, p_innerBlit_7_).endVertex();
		bufferbuilder.pos((double) p_innerBlit_0_, (double) p_innerBlit_2_, (double) p_innerBlit_4_).tex(p_innerBlit_5_, p_innerBlit_7_).endVertex();
		bufferbuilder.finishDrawing();
		WorldVertexBufferUploader.draw(bufferbuilder);
	}
}
