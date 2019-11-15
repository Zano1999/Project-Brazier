package net.dark_roleplay.crafter.api.icon;

public interface IIcon {
    void render(int posX, int posY, int mouseX, int mouseY, float delta);
    void renderScaled(int posX, int posY, float scale, int mouseX, int mouseY, float delta);
    int getWidth();
    int getHeight();
}
