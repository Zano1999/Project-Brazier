package net.dark_roleplay.crafter.api.icon;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class ItemStackIcon implements IIcon {

    private ItemStack stack;

    public ItemStackIcon(ItemStack stack){
        this.stack = stack;
    }

    @Override
    public void render(int posX, int posY, int mouseX, int mouseY, float delta) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        RenderHelper.enableGUIStandardItemLighting();
        renderer.renderItemAndEffectIntoGUI(stack, posX, posY);
        renderer.renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, stack, posX, posY, null);
    }

    @Override
    public void renderScaled(int posX, int posY, float scale, int mouseX, int mouseY, float delta) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(posX, posY, 0);
        GlStateManager.scalef(scale, scale, scale);
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        RenderHelper.enableGUIStandardItemLighting();
        renderer.renderItemAndEffectIntoGUI(stack, 0, 0);
        GlStateManager.popMatrix();
        renderer.renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, stack, (int) (posX + (scale * 16) - 16), (int) (posY + (scale * 16) - 16), null);
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }
}
