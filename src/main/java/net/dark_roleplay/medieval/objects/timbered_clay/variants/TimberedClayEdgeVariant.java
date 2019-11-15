package net.dark_roleplay.medieval.objects.timbered_clay.variants;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.util.ResourceLocation;

public class TimberedClayEdgeVariant extends TimberedClayVariant {

    public static TimberedClayEdgeVariant[] edges = new TimberedClayEdgeVariant[16];

    static{
    }

    public TimberedClayEdgeVariant(ResourceLocation name, int id) {
        super(name, id, Integer.bitCount(id), 0 + id/4, 4 + id % 4);
        this.isEdge = true;
    }

    @Override
    public TimberedClayVariant merge(TimberedClayVariant other){
        if(!(other instanceof TimberedClayEdgeVariant)) return other;
        return edges[this.id | other.id];
    }

    @Override
    public boolean canMerge(TimberedClayVariant other){
        return other instanceof TimberedClayEdgeVariant && (this.id | other.id) != this.id;
    }
}
