package net.dark_roleplay.medieval.objects.timbered_clay.variants;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TimberedClayVariant {

    private static int i = 15;

    public static final List<TimberedClayVariant> INSTANCES = new ArrayList<>(16 + 18);

    static{
        for(int i = 0; i < 16; i++){
            TimberedClayEdgeVariant.edges[i] = new TimberedClayEdgeVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "edge"), i);
        }

        VERTICAL = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "vertical"), i++, 1, 0, 0);
        HORIZONTAL = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "horizontal"), i++, 1, 1, 0);
        STRAIGHT_CROSS = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "straight_cross"), i++, 2, 2, 0);
        DIAGONAL_BT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "diagonal_bt"), i++, 1, 0, 1);
        DIAGONAL_TB = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "diagonal_tb"), i++, 1, 1, 1);
        CROSS = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "cross"), i++, 2, 2, 1);
        ARROW_DOWN = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "arrow_down"), i++, 2, 4, 1);
        ARROW_LEFT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "arrow_left"), i++, 2, 5, 1);
        ARROW_RIGHT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "arrow_right"), i++, 2, 6, 1);
        ARROW_TOP = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "arrow_top"), i++, 2, 7, 1);

        DD_LR_L = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_lr_l"), i++, 1, 0, 2);
        DD_LR_R = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_lr_r"), i++, 1, 1, 2);
        DD_RL_L = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_rl_l"), i++, 1, 2, 2);
        DD_RL_R = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_rl_r"), i++, 1, 3, 2);
        DD_BT_B = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_bt_b"), i++, 1, 4, 2);
        DD_BT_T = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_bt_t"), i++, 1, 5, 2);
        DD_TB_T = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_tb_t"), i++, 1, 6, 2);
        DD_TB_B = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_tb_b"), i++, 1, 7, 2);
    }

    public static TimberedClayVariant
        VERTICAL,
        HORIZONTAL,
        STRAIGHT_CROSS,
        DIAGONAL_BT,
        DIAGONAL_TB,
        CROSS,
        ARROW_DOWN,
        ARROW_LEFT,
        ARROW_RIGHT,
        ARROW_TOP,
        DD_LR_L,
        DD_LR_R,
        DD_RL_L,
        DD_RL_R,
        DD_BT_B,
        DD_BT_T,
        DD_TB_T,
        DD_TB_B;

    protected boolean isEdge = false;
    protected int beamCount = 0;
    protected ResourceLocation name = null;
    protected int textureX = 0, textureY = 0;
    protected int id = 0;

    public TimberedClayVariant(ResourceLocation name, int id, int beamCount, int textureX, int textureY){
        this.name = name;
        this.id = id;
        this.beamCount = beamCount;
        this.textureX = textureX;
        this.textureY = textureY;
        INSTANCES.add(id, this);
    }

    public TimberedClayVariant merge(TimberedClayVariant other){
        return other;
    }

    public boolean canMerge(TimberedClayVariant other){
        return false;
    }

    public boolean isEdge(){
        return this.isEdge;
    }

    public int getBeamCount(){
        return this.beamCount;
    }

    public int getTextX(){
        return this.textureX;
    }

    public int getTextY(){
        return this.textureY;
    }
}
