package net.dark_roleplay.medieval.objects.guis.fourteen;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class TimberedScreen extends Screen {

    private TimberedArea area;

    public TimberedScreen(TimberedArea area) {
        super(new TranslationTextComponent(DarkRoleplayMedieval.MODID + ".gui.timbered_clay_editor.title"));
        this.area = area;

    }

    @Override
    protected void init() {
        int posX = (this.width - this.area.width * 16) / 2;
        int posY = (this.height - this.area.height * 16) / 2;
        int posY2 = (this.height - 6 * 16) / 2;


        for(int x = 0; x < this.area.width; x++){
            for(int y = 0; y < this.area.height; y++){
                TimberedArea.TimberedClayState state = this.area.area[x][this.area.height - y - 1];
                if(state != null){
                    this.addButton(new DropArea(this, posX + x * 16, posY + y * 16, state));
                }
            }
        }

        int primary = 0, secondary = 0;
        for(TimberedClayVariant type : TimberedClayVariant.INSTANCES){
            if(!type.isEdge()){
                this.addButton(new MoveableWidget(this, posX - 18 - ((primary / 6) * 17), posY2 + ((primary % 6) * 17), type, ""));
                primary++;
            }else{
                this.addButton(new MoveableWidget(this, posX  + (this.area.width * 16) + 2 + ((secondary / 4) * 17), posY2 + ((secondary % 4) * 17), type, ""));
                secondary ++;
            }
        }
    }
}
