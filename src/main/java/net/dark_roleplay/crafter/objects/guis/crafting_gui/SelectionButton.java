package net.dark_roleplay.crafter.objects.guis.crafting_gui;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

import java.util.Optional;

public class SelectionButton extends Button {
    public static final ResourceLocation BG = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting2/selection_components.png");

    public SelectionButton(int posX, int posY, int width, int height, String text, Button.IPressable onPress) {
        super(posX, posY, width, height, text, onPress);
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float mouseZ) {
        Minecraft.getInstance().textureManager.bindTexture(BG);
        blit(this.x, this.y, this.isHovered ? 138 + 34 : 138, 0, this.width, this.height);


        for(
            int x = 0, y = 0, z = 0;
            x < 5 && y < 5 && z < 5;
            x++, z += (x == 5 ? 1 : 0), y += (z == 5 ? 1 : 0), x = (x == 5 ? 0 : x), z = (z == 5 ? 0 : z)
        ){
        }

        super.renderButton(mouseX, mouseY, mouseZ);
    }
}
