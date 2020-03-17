package net.dark_roleplay.crafter.objects.guis.v2;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.crafter.objects.guis.lib.Panel;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SelectionBox<T extends Widget> extends Panel {

    public static final ResourceLocation COMPS = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting2/selection_components.png");

    protected Screen parentScreen;

    public SelectionBox(Screen parentScreen, int posX, int posY){
        super(posX, posY, 138, 104);
        this.parentScreen = parentScreen;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        MainWindow mainWindow = Minecraft.getInstance().mainWindow;
        double scale = mainWindow.getGuiScaleFactor();

        Minecraft.getInstance().textureManager.bindTexture(COMPS);
        this.blit(this.posX, this.posY, 0, 0, this.width, this.height);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(
            (int)(this.posX + 1  * scale),
            (int)((parentScreen.height - this.posY - 1) * scale),
            (int)((this.width - 2) * scale),
            (int)((this.height - 2) * scale)
        );

        super.render(mouseX, mouseY, delta);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
