package net.dark_roleplay.crafter.objects.guis.crafting_gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class RecipeCrafting extends Screen {

    public static final ResourceLocation BG = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting2/recipe_crafting.png");

    private int sizeX = 162, sizeY = 168;
    private int guiLeft, guiTop;

    protected RecipeCrafting() {
        super(new TranslationTextComponent("gui.craftmans_choice.recipe_crafting.title"));
        this.setSize(161, 168);
    }

    @Override
    protected void init() {
        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;
        this.addButton(new Button(10, 10, 20, 20, "Recipes", button -> {
            Minecraft.getInstance().displayGuiScreen(new RecipeSelection(false));
        }));
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        Minecraft.getInstance().textureManager.bindTexture(BG);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.blit(this.guiLeft, this.guiTop, 0, 0, sizeX, sizeY, 256, 256);

        super.render(mouseX, mouseY, delta);
    }
}
