package net.dark_roleplay.crafter.objects.guis;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;

public class ScrollBar extends AbstractGui implements IGuiEventListener, IRenderable {

    IScrollable scrollable;
    int x, y, x2, y2;

    public ScrollBar(int posX, int posY, int width, int height, IScrollable scrollable){
        this.scrollable = scrollable;
        this.x = posX;
        this.y = posY;
        this.x2 = posX + width;
        this.y2 = posY + height;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount){
        if(amount == 0) return false;
        scrollable.scroll(amount);
        return true;
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        //Scroll Bar
        double scrollBarHeight = 185D / (this.scrollable.getMaxScroll() + 1);
        int scroll = (int) (this.scrollable.getScroll() * (scrollBarHeight < 10 ? 175D + scrollBarHeight  : 185D) / (this.scrollable.getMaxScroll() + 1));

        if(scrollBarHeight < 10)
            scrollBarHeight = 10;

        fill(x, y + scroll, x2, y + scroll + (int) scrollBarHeight, 0xFFFF0000);
    }
}



