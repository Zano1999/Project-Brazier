package net.dark_roleplay.crafter.objects.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RecipeListWidget extends FocusableGui implements IRenderable, IScrollable{

    private final Minecraft client;

    protected int x, y, x2, y2;
    protected int width, height;

    int maxScroll = 0, currentScroll = 0;

    protected int maxVisibleElements = 5;
    private RecipeWidget[] visibleWidgets = new RecipeWidget[maxVisibleElements];

    protected List<RecipeWidget> children = new ArrayList<>();

    public RecipeListWidget(int posX, int posY, int width, int height){
        this.client = Minecraft.getInstance();
        this.x = posX;
        this.y = posY;
        this.width = width;
        this.height = height;
        this.x2 = posX + width;
        this.y2 = posY + height;
    }

    public void compileList(){
        this.maxScroll = this.children.size() - this.maxVisibleElements;
    }

    public void addWidget(RecipeWidget widget){
        this.children.add(widget);
    }

    private void collectVisibleWidgets() {
        for(int i = 0; i < visibleWidgets.length; i++) visibleWidgets[i] = null;
        for (int i = currentScroll; i < currentScroll + this.maxVisibleElements && i < this.children.size(); i++) {
            int j = i - currentScroll;
            visibleWidgets[j] = this.children.get(i);
            visibleWidgets[j].setPos(this.x, this.y + j * 37);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        collectVisibleWidgets();
        double scale = client.mainWindow.getGuiScaleFactor();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int)(this.x  * scale), (int)(client.mainWindow.getFramebufferHeight() - (this.y2 * scale)), (int)(this.width * scale), (int)(this.height * scale));

        for(int i = 0; i < visibleWidgets.length; i++){
            RecipeWidget widget = visibleWidgets[i];
            if(widget == null) continue;
            widget.render(mouseX, mouseY, delta);
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY){
        return ((mouseX >= this.x && mouseX <= this.x2) || mouseX >= this.x2 + 3 && mouseX <= this.x2 + 15)&&
                mouseY >= this.y && mouseY <= this.y2;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        for(int i = 0; i < visibleWidgets.length; i++) {
            RecipeWidget widget = visibleWidgets[i];
            if (widget == null) continue;
            if (widget.mouseClicked(mouseX, mouseY, mouseButton)) {
                this.setFocused(widget);
                if (mouseButton == 0) {
                    this.setDragging(true);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<? extends IGuiEventListener> children() {
        return children;
    }

    //Handle scrolling
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount){
        if(amount == 0) return false;
        this.scroll(amount);
        return true;
    }

    @Override
    public int getMaxScroll() {
        return this.maxScroll;
    }

    @Override
    public int setScroll(int newScroll) {
        return this.currentScroll = newScroll;
    }

    @Override
    public int getScroll() {
        return this.currentScroll;
    }
}
