package net.dark_roleplay.medieval.objects.guis;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class RoadSignOverlay extends AbstractGui {

    public void draw(Minecraft mc){
        int width = Minecraft.getInstance().mainWindow.getScaledWidth() / 2;

        ITextComponent tc = new TranslationTextComponent("overlay.drpmedieval.road_sign.a")
                .appendSibling(new KeybindTextComponent("keybind.drpmedieval.interactor"))
                .appendSibling(new TranslationTextComponent("overlay.drpmedieval.road_sign.b"));

        drawCenteredString(Minecraft.getInstance().fontRenderer, tc.getFormattedText(), width, 4, 0xFFFFFFFF);
    }
}
