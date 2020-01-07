package net.dark_roleplay.medieval.objects.items.equipment.misc;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class RoadSignItem extends Item {

    private ResourceLocation signModelLeft;
    private ResourceLocation signModelRight;
    private IMaterial material;

    public RoadSignItem(Properties properties, IMaterial material, String modelLoc) {
        super(properties);
        this.signModelLeft = new ResourceLocation(String.format(material.getTextProvider().apply(modelLoc), "left"));
        this.signModelRight = new ResourceLocation(String.format(material.getTextProvider().apply(modelLoc), "right"));
        this.material = material;
    }

    public ResourceLocation getSignModelLeft() {
        return signModelLeft;
    }

    public ResourceLocation getSignModelRight() {
        return signModelRight;
    }

    public IMaterial getMaterial() {
        return material;
    }
}
