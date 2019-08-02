package net.dark_roleplay.medieval.objects.guis;

import com.mojang.blaze3d.platform.GlStateManager;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class TelescopeOverlay extends AbstractGui {
	
	private static final ResourceLocation SCOPE = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/guis/overlays/telescope.png");
	
	private static final int BLACK = 0xFF000000;
	
	public void draw(Minecraft mc){
        int width = Minecraft.getInstance().mainWindow.getScaledWidth();
        int height = Minecraft.getInstance().mainWindow.getScaledHeight();
        
        mc.getTextureManager().bindTexture(TelescopeOverlay.SCOPE);

        GlStateManager.enableBlend();      
        GlStateManager.disableAlphaTest();

    	GlStateManager.color4f(1F, 1F, 1F, 1F);
        if(width >= height){
        	int xPos = width - height;
        	AbstractGui.blit(xPos / 2, 0, 0, 0, height, height, height, height);
            AbstractGui.fill(0, 0, xPos / 2, height, TelescopeOverlay.BLACK);
            AbstractGui.fill(width - (xPos/2) - 1, 0, width, height, TelescopeOverlay.BLACK);
        }else{
        	int yPos = height - width;
        	AbstractGui.blit(0, yPos / 2, 0, 0, width, width, width, width);
            AbstractGui.fill(0, 0, width, yPos/2, TelescopeOverlay.BLACK);
            AbstractGui.fill(0, height - (yPos/2) - 1, width, height, TelescopeOverlay.BLACK);
        }
    }
}
