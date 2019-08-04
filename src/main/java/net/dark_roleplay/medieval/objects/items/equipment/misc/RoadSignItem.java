package net.dark_roleplay.medieval.objects.items.equipment.misc;

import net.dark_roleplay.marg.api.materials.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class RoadSignItem extends Item {

    private ResourceLocation signModelLeft;
    private ResourceLocation signModelRight;
    private Material material;

    public RoadSignItem(Properties properties, Material material, ResourceLocation signModelLeft, ResourceLocation signModelRight) {
        super(properties);
        this.signModelLeft = signModelLeft;
        this.signModelRight = signModelRight;
        this.material = material;
    }

    public ResourceLocation getSignModelLeft() {
        return signModelLeft;
    }

    public ResourceLocation getSignModelRight() {
        return signModelRight;
    }

    public Material getMaterial() {
        return material;
    }
}
