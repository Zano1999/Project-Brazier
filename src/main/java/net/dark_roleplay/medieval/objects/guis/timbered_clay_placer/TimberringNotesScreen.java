package net.dark_roleplay.medieval.objects.guis.timbered_clay_placer;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler.MedievalNetworking;
import net.dark_roleplay.medieval.networking.timbering.TimberingNotesPlacementPacket;
import net.dark_roleplay.medieval.objects.guis.fourteen.DropArea;
import net.dark_roleplay.medieval.objects.guis.fourteen.MoveableWidget;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberedClayState;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberingData;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Direction;
import static net.minecraft.util.Direction.Axis;
import net.minecraft.util.text.TranslationTextComponent;

public class TimberringNotesScreen extends Screen {

    private TimberingData data;

    private int posX, posY;

    public TimberringNotesScreen(TimberingData data) {
        super(new TranslationTextComponent(DarkRoleplayMedieval.MODID + ".gui.timbered_clay_editor.title"));
        this.data = data;
        this.data.compileArea(Minecraft.getInstance().player.getEntityWorld());
    }

    @Override
    protected void init() {
        int horizontalSize = this.data.getAxis() == Direction.Axis.X ? this.data.getSelection().getWidth() : this.data.getSelection().getLength();

        posX = (this.width - horizontalSize * 16) / 2;
        posY = (this.height - this.data.getSelection().getHeight() * 16) / 2;
        int posY2 = (this.height - 6 * 16) / 2;


        for(int x = 0; x < horizontalSize; x++){
            for(int y = this.data.getSelection().getHeight()-1; y >= 0; y--){
                TimberedClayState state = this.data.getArea()[x][y];
                if(state != null && state != TimberedClayState.EMPTY){
                    this.addButton(new DropArea(this, posX + x * 16, posY + y * 16, state));
                }
            }
        }

        int primary = 0, secondary = 0;
        for(TimberedClayVariant type : TimberedClayVariant.INSTANCES){
            if(!type.isEdge() && type != TimberedClayVariant.CLEAN){
                this.addButton(new MoveableWidget(this, posX - 18 - ((primary / 6) * 17), posY2 + ((primary % 6) * 17), type, ""));
                primary++;
            }else if(type != TimberedClayVariant.CLEAN){
                this.addButton(new MoveableWidget(this, posX  + (horizontalSize * 16) + 2 + ((secondary / 4) * 17), posY2 + ((secondary % 4) * 17), type, ""));
                secondary ++;
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        int horizontalSize = this.data.getAxis() == Axis.X ? this.data.getSelection().getWidth() : this.data.getSelection().getLength();

        int posYA, posYB, posXA, posXB;

        if(this.data.getPosA().getY() > this.data.getPosB().getY()){
            posYA = this.posY;
            posYB = this.posY + (this.data.getSelection().getHeight() - 1) * 16;
        }else{
            posYB = this.posY;
            posYA = this.posY + (this.data.getSelection().getHeight() - 1) * 16;
        }

        if(this.data.getPosA().getY() > this.data.getPosB().getY()){
            posXA = this.posX;
            posXB = this.posX + (horizontalSize - 1) * 16;
        }else{
            posXB = this.posX;
            posXA = this.posX + (horizontalSize - 1) * 16;
        }

        fill(posXA - 1, posYA - 1, posXA + 17, posYA + 17, 0xFFFF0000);
        fill(posXB - 1, posYB - 1, posXB + 17, posYB + 17, 0xFF00FF00);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        super.onClose();
        MedievalNetworking.CHANNEL.sendToServer(new TimberingNotesPlacementPacket(this.data));
    }

}
