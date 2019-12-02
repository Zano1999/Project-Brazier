package net.dark_roleplay.crafter.api.recipe_crafter;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import java.util.HashSet;
import java.util.Set;

public abstract class Crafter {

    private Set<Entity> entities = new HashSet<>();
    private Set<TileEntity> tileEntities = new HashSet<>();

    public Crafter(){}

    public Crafter(Entity entity){
        this.entities.add(entity);
    }

    public Crafter(TileEntity tileEntity){
        this.tileEntities.add(tileEntity);
    }

    public final void addEntity(Entity entity){
        this.entities.add(entity);
    }

    public final void addTileEntities(TileEntity te){
        this.tileEntities.add(te);
    }

    protected Set<Entity> getEntities(){
        return this.entities;
    }

    protected Set<TileEntity> getTileEntities(){
        return this.tileEntities;
    }

    public abstract boolean mayCraft();

    public abstract void craft();

}
