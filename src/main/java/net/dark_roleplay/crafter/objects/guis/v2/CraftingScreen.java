package net.dark_roleplay.crafter.objects.guis.v2;

import net.dark_roleplay.crafter.objects.guis.crafting_gui.SelectionButton;
import net.dark_roleplay.crafter.objects.guis.lib.DScreen;
import net.dark_roleplay.crafter.objects.guis.lib.Panel;
import net.minecraft.util.text.TranslationTextComponent;

public class CraftingScreen extends DScreen {

    private int guiLeft, guiTop;

    private Panel craftingWindow;
    private Panel selectionBox;

    public CraftingScreen() {
        super(new TranslationTextComponent("gui.craftmans_choice.category_selection.title"));
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - 162) / 2;
        this.guiTop = (this.height - 168) / 2;

        addChild(craftingWindow = new CraftingWindow(this.guiLeft, this.guiTop));

        craftingWindow.addChild(selectionBox = new SelectionBox(this, this.guiLeft + 12, this.guiTop + 57));

        for(int x = 0; x < 3; x++){
//            selectionBox.addChild(new SelectionButton())M
        }
    }
}
