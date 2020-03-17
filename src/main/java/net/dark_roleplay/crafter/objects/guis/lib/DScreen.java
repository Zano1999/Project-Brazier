package net.dark_roleplay.crafter.objects.guis.lib;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class DScreen extends Screen {

    protected List<IRenderable> renderChildren = new ArrayList<>();

    protected DScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    protected void init() {
        this.renderChildren.clear();
    }


    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();

        super.render(mouseX, mouseY, delta);
        for(int i = 0; i < this.renderChildren.size(); ++i)
            this.renderChildren.get(i).render(mouseX, mouseY, delta);
    }

    protected <T> void addChild(T widget){
        if(widget instanceof IRenderable)
            this.renderChildren.add((IRenderable) widget);
        if(widget instanceof IGuiEventListener)
            this.children.add((IGuiEventListener) widget);
    }
}
