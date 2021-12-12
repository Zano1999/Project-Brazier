package net.dark_roleplay.projectbrazier.util.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ScreenTextureWrapper {

	private final ResourceLocation textureLocation;
	private final int totalWidth, totalHeight;

	public ScreenTextureWrapper(ResourceLocation textureLocation, int totalWidth, int totalHeight){
		this.textureLocation = textureLocation;
		this.totalWidth = totalWidth;
		this.totalHeight = totalHeight;
	}

	public ScreenTexture createTexture(int uMin, int vMin, int uMax, int vMax){
		return new ScreenTexture(this, uMin, vMin, uMax, vMax, this.totalWidth, this.totalHeight);
	}

	public void bind(){
		RenderSystem.setShaderTexture(0, textureLocation);
		//Minecraft.getInstance().getTextureManager().bindForSetup(textureLocation);
	}
}
