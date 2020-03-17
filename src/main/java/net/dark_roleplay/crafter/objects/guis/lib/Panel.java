package net.dark_roleplay.crafter.objects.guis.lib;

import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;

import java.util.ArrayList;
import java.util.List;

public class Panel extends FocusableGui implements IRenderable {

    protected List<IRenderable> renderChildren = new ArrayList<>();
    protected List<IGuiEventListener> children = new ArrayList<>();

    protected int posX, posY, width, height;

    public Panel(int posX, int posY, int width, int height){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public void renderBG(int mouseX, int mouseY, float delta){}

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBG(mouseX, mouseY, delta);
        for(int i = 0; i < this.renderChildren.size(); ++i)
            this.renderChildren.get(i).render(mouseX, mouseY, delta);
    }

    public <T> void addChild(T widget){
        if(widget instanceof IRenderable)
            this.renderChildren.add((IRenderable) widget);
        if(widget instanceof IGuiEventListener)
            this.children.add((IGuiEventListener) widget);
    }

    @Override
    public List<? extends IGuiEventListener> children() {
        return this.children;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY){
        return (mouseX >= this.posX && mouseX <= this.posX + this.width)&&
               (mouseY >= this.posY && mouseY <= this.posY + this.height);
    }

    public void setPosX(int posX){
        this.setPos(posX, this.posY);
    }

    public void setPosY(int posY){
        this.setPos(this.posX, posY);
    }

    public void setPos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
}
