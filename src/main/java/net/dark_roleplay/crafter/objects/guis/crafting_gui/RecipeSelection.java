package net.dark_roleplay.crafter.objects.guis.crafting_gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.crafter.objects.guis.lib.DScreen;
import net.dark_roleplay.crafter.objects.guis.v2.SelectionBox;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class RecipeSelection extends DScreen {

    public static final ResourceLocation BG = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting2/selection_bg.png");

    private int sizeX = 162, sizeY = 168;
    private int guiLeft, guiTop;

    private float animOffset = 0;

    protected SelectionBox scrollBox = null;

    protected RecipeSelection(boolean animate) {
        super(new TranslationTextComponent("gui.craftmans_choice.recipe_selection.title"));
        this.setSize(161, 168);
        if(animate){
            animOffset = 15F;
        }
    }

    @Override
    protected void init() {
        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;

        addChild(scrollBox = new SelectionBox<SelectionButton>(this, this.guiLeft + 12, this.guiTop + 42));

        this.addButton(new Button(10, 10, 20, 20, "Recipes", button -> {
            Minecraft.getInstance().displayGuiScreen(new RecipeCrafting());
        }));
        this.addButton(new Button(40, 10, 20, 20, "Cat", button -> {
            Minecraft.getInstance().displayGuiScreen(new CategorySelection(true));
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        //Draw Background
        Minecraft.getInstance().textureManager.bindTexture(BG);
        this.blit(this.guiLeft, this.guiTop, 0, 0, sizeX, sizeY);

        //Animations are still running
        if(animOffset > 0) {
            animOffset = Math.max(animOffset - delta * 5, 0);
            scrollBox.setPos(this.guiLeft + 12, this.guiTop + 42 + (int)Math.floor(animOffset));
        }else{//animations are finished

        }

        //Draw components
        super.render(mouseX, mouseY, delta);

        //Helper for discord streaming
        this.fillGradient(mouseX - 1, mouseY - 1, mouseX + 1, mouseY + 1, 0xFFFF0000, 0xFFFF0000);
    }
}
