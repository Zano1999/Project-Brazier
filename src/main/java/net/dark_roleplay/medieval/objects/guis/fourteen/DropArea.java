package net.dark_roleplay.medieval.objects.guis.fourteen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberedClayState;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;

public class DropArea extends Widget {

    private Screen screen;
    private int mouseOffsetX = 0, mouseOffsetY = 0;
    private TimberedClayState state;

    public DropArea(Screen screen, int posX, int posY, TimberedClayState state) {
        super(posX, posY, 16, 16, "");
        this.screen = screen;
        this.state = state;
    }

    public void setPosition(int xIn, int yIn) {
        this.x = xIn;
        this.y = yIn;
    }

    public void addType(TimberedClayVariant type){
        state.addType(type);
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
//        GlStateManager.enableBlend();
//        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        if(this.isHovered() && screen.isDragging() && screen.getFocused() instanceof MoveableWidget){
//            fill(this.x, this.y, this.x + this.width, this.y + this.height, 0xFF00FF00);
//        }else{
//            fill(this.x, this.y, this.x + this.width, this.y + this.height, 0xFFAAAA88);
//        }
//
//        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//
//        this.state.setupTexture();
//        blit(this.x, this.y,16 * this.state.getPrimary().getTextX(), 16 * this.state.getPrimary().getTextY(), 16, 16, 128,128);
//        blit(this.x, this.y,16 * this.state.getSecondary().getTextX(), 16 * this.state.getSecondary().getTextY(), 16, 16, 128,128);
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_) {
        this.state.clear();
    }

    @Override
    protected boolean isValidClickButton(int mouseButton) {
        return mouseButton == 1;
    }


    //Disable Event Handlers
    @Override
    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
        return false;
    }

    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
        return false;
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        return false;
    }

    @Override
    public boolean changeFocus(boolean p_changeFocus_1_) {
        return false;
    }

    @Override
    public boolean isMouseOver(double p_isMouseOver_1_, double p_isMouseOver_3_) {
        return false;
    }
}
