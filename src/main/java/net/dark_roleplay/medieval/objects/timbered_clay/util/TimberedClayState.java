package net.dark_roleplay.medieval.objects.timbered_clay.util;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.building.timbered_clay.TimberedClay;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayEdgeVariant;
import net.dark_roleplay.medieval.objects.timbered_clay.variants.TimberedClayVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

public class TimberedClayState {

    public static final TimberedClayState EMPTY = TimberedClayState.create();

    private TimberedClayVariant primary;
    private TimberedClayVariant secondary;

    public static TimberedClayState create(){
        return new TimberedClayState(TimberedClayVariant.CLEAN, TimberedClayEdgeVariant.edges[0]);
    }

    public static TimberedClayState of(TimberedClayVariant primary, TimberedClayVariant secondary){
        return new TimberedClayState(primary, secondary);
    }

    private TimberedClayState(TimberedClayVariant primary, TimberedClayVariant secondary){
        this.primary = primary;
        this.secondary = secondary;
    }

    public void addType(TimberedClayVariant type){
        if(type.isEdge()){
            this.secondary = type;
        }else{
            this.primary = type;
        }
    }

    public TimberedClayVariant getPrimary(){
        return this.primary;
    }

    public boolean hasPrimary(){
        return this.primary != TimberedClayVariant.CLEAN;
    }

    public TimberedClayVariant getSecondary(){
        return this.secondary;
    }

    public boolean hasSecondary(){
        return this.secondary != TimberedClayEdgeVariant.edges[0];
    }

    public void clear(){
        this.primary = TimberedClayVariant.CLEAN;
        this.secondary = TimberedClayEdgeVariant.edges[0];
    }

    public static final ResourceLocation TYPES = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/timbered_clay/types.png");
    public static void setupTexture(){
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(TYPES);
    }

    public BlockState toBlockState(String material){
        int mask = secondary == null ? 0 : secondary.getID();

        Block b = ForgeRegistries.BLOCKS.getValue(
                primary == TimberedClayVariant.CLEAN ?
                        mask == 0 ? new ResourceLocation(DarkRoleplayMedieval.MODID, "clean_timbered_clay") :
                        new ResourceLocation(DarkRoleplayMedieval.MODID, "clean_" + material + "_timbered_clay") :
                        new ResourceLocation(DarkRoleplayMedieval.MODID, material + "_" + this.primary.getName().getPath() + "_timbered_clay"));

        BlockState state = b.getDefaultState();

        if(!(primary == TimberedClayVariant.CLEAN  && mask == 0))
        state = state
                .with(TimberedClay.BOTTOM,(mask & 0x1) == 1)
                .with(TimberedClay.LEFT,(mask & 0x2) == 2)
                .with(TimberedClay.TOP,(mask & 0x4) == 4)
                .with(TimberedClay.RIGHT,(mask & 0x8) == 8);

        return state;
    }
}
