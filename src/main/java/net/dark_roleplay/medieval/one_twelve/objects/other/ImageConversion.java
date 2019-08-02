package net.dark_roleplay.medieval.one_twelve.objects.other;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;

import net.minecraft.nbt.CompoundNBT;

public class ImageConversion {

	public static CompoundNBT imgToNBT(BufferedImage img){
		CompoundNBT imgNBT = new CompoundNBT();

		int[] imageBytes = ((DataBufferInt) img.getData().getDataBuffer()).getData();

		imgNBT.putInt("width", img.getWidth());
		imgNBT.putInt("height", img.getHeight());
		imgNBT.putIntArray("imgBuf", imageBytes);

		return imgNBT;
	}

	public static BufferedImage imgFromNBT(CompoundNBT imgNBT){
		if(!imgNBT.hasUniqueId("width") || !imgNBT.hasUniqueId("height") || !imgNBT.hasUniqueId("imgBuf")){
			return null;
		}

		int width = imgNBT.getInt("width");
		int height = imgNBT.getInt("height");
		int[] imgBuf = imgNBT.getIntArray("imgBuf");

		if(imgBuf == null)
			return null;

		return intArToBuf(width, height, imgBuf);
	}

	public static BufferedImage intArToBuf(int width, int height, int[] imageBuf){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setData(Raster.createRaster(image.getSampleModel(), new DataBufferInt(imageBuf, imageBuf.length), new Point()));
		return image;
	}

	public static int[] bufToIntAr(BufferedImage img){
		return ((DataBufferInt) img.getData().getDataBuffer()).getData();
	}
}
