package net.dark_roleplay.medieval.objects.guis.overlays;

import net.dark_roleplay.medieval.handler.KeybindHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AdvancedInteractionOverlay extends AbstractGui {

    boolean wasPressed = false;
    long firstPressed = 0;
    int requiredMs = 1000;

    public void draw(Minecraft mc){

        requiredMs = 650;
        int width = Minecraft.getInstance().mainWindow.getScaledWidth() / 2;

        fill(width - 50, 10, width + 50, 20, 0xFFFF0000);
        if(KeybindHandler.BLOCK_INTERACTOR.isKeyDown()){
            long elapsed = 0;
            if(!wasPressed){
                firstPressed = System.currentTimeMillis();
                wasPressed = true;
            }else{
                elapsed = System.currentTimeMillis() - firstPressed;
            }
            fill(width - 50, 10, (int) (width - 50 + (Math.min(100, Math.ceil(100 * (elapsed/(requiredMs * 1d)))))), 20, 0xFF00FF00);

            if(elapsed > requiredMs){
                System.out.println("Debug");
                wasPressed = false;
                //TODO Open selection GUI
            }
        }else{
            wasPressed = false;
        }
    }
}