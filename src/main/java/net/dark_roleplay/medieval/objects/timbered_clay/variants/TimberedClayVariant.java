package net.dark_roleplay.medieval.objects.timbered_clay.variants;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TimberedClayVariant {

    private static int i = 15;

    public static final List<TimberedClayVariant> INSTANCES = new ArrayList<>(16 + 24);

    static{
        for(int i = 0; i < 16; i++){
            TimberedClayEdgeVariant.edges[i] = new TimberedClayEdgeVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "edge"), i);
        }

        CLEAN = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "clean"), i++, 0, 7, 0);
        VERTICAL = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "vertical"), i++, 1, 0, 0);
        HORIZONTAL = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "horizontal"), i++, 1, 1, 0);
        STRAIGHT_CROSS = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "straight_cross"), i++, 2, 2, 0);
        DIAGONAL_BT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "diagonal_bt"), i++, 1, 1, 1);
        DIAGONAL_TB = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "diagonal_tb"), i++, 1, 0, 1);
        CROSS = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "cross"), i++, 2, 2, 1);
        ARROW_DOWN = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "arrow_down"), i++, 2, 4, 1);
        ARROW_LEFT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "arrow_left"), i++, 2, 6, 1);
        ARROW_RIGHT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "arrow_right"), i++, 2, 5, 1);
        ARROW_TOP = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "arrow_top"), i++, 2, 7, 1);
        BIRD_FOOT_DOWN = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "bird_foot_down"), i++, 3, 3, 0);
        BIRD_FOOT_LEFT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "bird_foot_left"), i++, 3, 5, 0);
        BIRD_FOOT_RIGHT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "bird_foot_right"), i++, 3, 4, 0);
        BIRD_FOOT_TOP = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "bird_foot_top"), i++, 3, 6, 0);

        DD_L_LR = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_l_lr"), i++, 1, 0, 2);
        DD_R_LR = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_r_lr"), i++, 1, 1, 2);
        DD_L_RL = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_l_rl"), i++, 1, 2, 2);
        DD_R_RL = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_r_rl"), i++, 1, 3, 2);
        DD_B_BT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_b_bt"), i++, 1, 4, 2);
        DD_T_BT = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_t_bt"), i++, 1, 5, 2);
        DD_T_TB = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_t_tb"), i++, 1, 6, 2);
        DD_B_TB = new TimberedClayVariant(new ResourceLocation(DarkRoleplayMedieval.MODID, "double_diagonal_b_tb"), i++, 1, 7, 2);
    }

    public static TimberedClayVariant
        CLEAN,
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
        BIRD_FOOT_DOWN,
        BIRD_FOOT_LEFT,
        BIRD_FOOT_RIGHT,
        BIRD_FOOT_TOP,
        DD_L_LR,
        DD_R_LR,
        DD_L_RL,
        DD_R_RL,
        DD_B_BT,
        DD_T_BT,
        DD_T_TB,
        DD_B_TB;

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

    public int getID(){
        return this.id;
    }

    public ResourceLocation getName(){
        return this.name;
    }
}
