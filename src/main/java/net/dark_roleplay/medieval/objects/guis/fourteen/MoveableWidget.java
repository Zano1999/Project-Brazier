package net.dark_roleplay.medieval.objects.guis.fourteen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;

public class MoveableWidget extends Widget {

    private Screen screen;
    private int mouseOffsetX = 0, mouseOffsetY = 0;
    private int initialX = 0, initialY = 0;
    private TimberedClayVariant type;

    public MoveableWidget(Screen screen, int posX, int posY, TimberedClayVariant type, String msg) {
        super(posX, posY, 16, 16, msg);
        this.screen = screen;
        this.type = type;
    }

    public void onClick(double x, double y) {
        this.initialX = this.x;
        this.initialY = this.y;
        this.mouseOffsetX = (int) (x - this.x);
        this.mouseOffsetY = (int) (y - this.y);
    }

    public void onRelease(double x, double y) {
        for(IGuiEventListener listener : screen.children()){
            if(!(listener instanceof DropArea)) continue;
            DropArea area = (DropArea) listener;
            if(area.isHovered()){
                area.addType(this.type);
            }
        }
        this.x = initialX;
        this.y = initialY;
    }

    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        setPosition((int) Math.min(this.screen.width - this.width, Math.max(0, mouseX - mouseOffsetX)), (int) Math.min(this.screen.height - this.height, Math.max(0, mouseY - mouseOffsetY)));
    }

    public void setPosition(int xIn, int yIn) {
        this.x = xIn;
        this.y = yIn;
    }

    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableDepthTest();
        TimberedArea.TimberedClayState.setupTexture();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(this.x, this.y,16 * type.getTextX(), 16 * type.getTextY(), 16, 16, 128,128);
        //fill(this.x, this.y, this.x + this.width, this.y + this.height, isPrimary ? 0xFFFF0000 : 0xFFFFFF00);
        GlStateManager.enableDepthTest();
    }
}
