package net.dark_roleplay.crafter.objects.guis.crafting_gui;

import net.dark_roleplay.crafter.objects.guis.lib.DScreen;
import net.dark_roleplay.crafter.objects.guis.v2.SelectionBox;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class CategorySelection extends DScreen {

    public static final ResourceLocation BG = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting2/selection_bg.png");
    public static final ResourceLocation COMPS = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting2/selection_components.png");

    protected SelectionBox scrollBox = null;

    private int sizeX = 162, sizeY = 168;
    private int guiLeft, guiTop;

    private float animOffset = 0;

    public CategorySelection(boolean animate) {
        super(new TranslationTextComponent("gui.craftmans_choice.category_selection.title"));
        this.setSize(161, 168);
        if(animate){
            animOffset = -15F;
        }
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;

        addChild(scrollBox = new SelectionBox<SelectionButton>(this, this.guiLeft + 12, this.guiTop + 57));

        this.addButton(new Button(10, 10, 20, 20, "Recipes", button -> {
            Minecraft.getInstance().displayGuiScreen(new RecipeSelection(true));
        }));
    }


    @Override
    public void render(int mouseX, int mouseY, float delta) {

        //Draw Background

        //Animate Components
        if(animOffset < 0) {
            animOffset = Math.min(animOffset + delta * 5, 0);
            scrollBox.setPos(this.guiLeft + 12, this.guiTop + 57 + (int)Math.floor(animOffset));
        }

        //Draw components
        super.render(mouseX, mouseY, delta);

        //Helper for discord streaming
        this.fillGradient(mouseX - 1, mouseY - 1, mouseX + 1, mouseY + 1, 0xFFFF0000, 0xFFFF0000);
    }
}
