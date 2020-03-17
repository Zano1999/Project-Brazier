package net.dark_roleplay.crafter.objects.guis.v2;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.crafter.objects.guis.lib.Panel;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CraftingWindow extends Panel {

    public static final ResourceLocation BG = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting2/selection_bg.png");

    public CraftingWindow(int posX, int posY){
        super(posX, posY, 162, 168);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        Minecraft.getInstance().textureManager.bindTexture(BG);
        this.blit(this.posX, this.posY, 0, 0, this.width, this.height);

        super.render(mouseX, mouseY, delta);
    }
}
