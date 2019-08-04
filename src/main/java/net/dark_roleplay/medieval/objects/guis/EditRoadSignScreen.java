package net.dark_roleplay.medieval.objects.guis;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntity;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.SignInfo;
import net.dark_roleplay.medieval.objects.packets.RoadSignEditSignPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class EditRoadSignScreen extends Screen {

    private RoadSignTileEntity te;
    private SignInfo teSign;
    private int signID = 0;
    private TextInputUtil textInputUtil;

    public EditRoadSignScreen(RoadSignTileEntity te, int signID) {
        super(new TranslationTextComponent("gui.drpmedieval.edit_road_sign"));
        this.te = te;
        this.signID = signID;
        this.teSign = te.getSigns().get(signID);
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.addButton(new Button(this.width/2 - 100, this.height / 4 + 120, 200, 20, I18n.format("gui.done"), (button) ->{
            this.finishEditing();
        }));

        this.textInputUtil = new TextInputUtil(Minecraft.getInstance(), () -> teSign.getText(), (newText) -> {
            teSign.setText(newText);
        }, 38);
    }


    private void finishEditing() {
        this.te.sendChangesToServer(signID);
        this.minecraft.displayGuiScreen(null);
    }

    @Override
    public void removed() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public void onClose() {
        this.finishEditing();
    }


    @Override
    public boolean charTyped(char character, int keyID) {
        return textInputUtil.func_216894_a(character);
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return this.textInputUtil.func_216897_a(p_keyPressed_1_) ? true : super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();

        this.drawCenteredString(this.font, teSign.getText(), this.width / 2, this.height/2 - 6, 0xFFFFFFFF);

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }
}
