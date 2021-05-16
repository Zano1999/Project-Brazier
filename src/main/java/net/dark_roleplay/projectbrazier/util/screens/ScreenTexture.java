package net.dark_roleplay.projectbrazier.util.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;

public class ScreenTexture extends AbstractGui {
	private final ScreenTextureWrapper wrapper;

	private final int width, height;
	private final int x1, y1, x2, y2, totalX, totalY;

	private boolean hasHorizontalSeg;
	private boolean has9Slice;
	private int segX1, segY1, segX2, segY2;
	private int slice1Width, slice2Width, slice3Width;
	private int slice1Height, slice2Height, slice3Height;


	public ScreenTexture(ScreenTextureWrapper wrapper, int x1, int y1, int x2, int y2, int totalX, int totalY) {
		this.wrapper = wrapper;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.totalX = totalX;
		this.totalY = totalY;
		this.width = x2 - x1;
		this.height = y2 - y1;
	}

	public void render(MatrixStack stack, int posX, int posY){
		wrapper.bind();
		this.blit(stack, posX, posY, width, height, x1, y1, x2, y2, totalX, totalY);
	}

	public void renderSegmented(MatrixStack stack, int posX, int posY, int width, int height){
		wrapper.bind();
		int centerMaxX = width - this.slice3Width;
		int centerMaxY = height - this.slice3Height;

//		this.blit(stack, posX - 30, posY - 25, this.width, this.height, this.x1, this.y1, this.x2, this.y2, this.totalX, this.totalY);
		//Draw Corners TL, TR, BL, BR
		this.blit(stack, posX, posY, this.slice1Width, this.slice1Height, this.x1, this.y1, this.slice1Width, this.slice1Height, this.totalX, this.totalY);
		this.blit(stack, posX + width - this.slice3Width, posY, this.slice3Width, this.slice1Height, this.segX2, this.y1, this.slice3Width, this.slice1Height, this.totalX, this.totalY);
		this.blit(stack, posX, posY + height - this.slice3Height, this.slice1Width, this.slice3Height, x1, segY2, this.slice1Width, this.slice3Height, totalX, totalY);
		this.blit(stack, posX + width - this.slice3Width, posY + height - this.slice3Height, this.slice3Width, this.slice3Height, segX2, segY2, this.slice3Width, this.slice3Height, totalX, totalY);

		//Horizontal
		int offsetX = this.slice1Width;
		do{
			int currentWidth = Math.min(this.slice2Width, centerMaxX - offsetX);
			this.blit(stack, posX + offsetX, posY, 									  currentWidth, this.slice1Height, this.segX1, this.y1, currentWidth, this.slice1Height, this.totalX, this.totalY);
			this.blit(stack, posX + offsetX, posY + height - this.slice3Height, currentWidth, this.slice3Height, this.segX1, this.segY2, currentWidth, this.slice3Height, this.totalX, this.totalY);

			//Do Center
			int offsetY = this.slice1Height;
			do{
				int currentHeight = Math.min(this.slice2Height, centerMaxY - offsetY);
				this.blit(stack, posX + offsetX, posY + offsetY, currentWidth, currentHeight, this.segX1, this.segY1, currentWidth, currentHeight, this.totalX, this.totalY);
				offsetY += this.slice2Height;
			}while(offsetY < centerMaxY);

			offsetX += this.slice2Width;
		}while(offsetX < centerMaxX);
		//Vertical
		int offsetY = this.slice1Height;
		do{
			int currentHeight = Math.min(this.slice2Height, centerMaxY - offsetY);
			this.blit(stack, posX, posY + offsetY, 									this.slice1Width, currentHeight, this.x1, this.segY1, this.slice1Width, currentHeight, this.totalX, this.totalY);
			this.blit(stack, posX + width - this.slice3Width, posY + offsetY, this.slice3Width, currentHeight, this.segX2, this.segY1, this.slice3Width, currentHeight, this.totalX, this.totalY);
			offsetY += this.slice2Height;
		}while(offsetY < centerMaxY);
	}

	public void set9Slice(int segX1, int segX2, int segY1, int segY2){
		this.has9Slice = true;
		this.segX1 = segX1;
		this.segX2 = segX2;
		this.segY1 = segY1;
		this.segY2 = segY2;

		this.slice1Width = this.segX1 - this.x1;
		this.slice2Width = this.segX2 - this.segX1;
		this.slice3Width = this.x2 - this.segX2;

		this.slice1Height = this.segY1 - this.y1;
		this.slice2Height = this.segY2 - this.segY1;
		this.slice3Height = this.y2 - this.segY2;
	}
}
