package net.dark_roleplay.medieval.objects.items.equipment.misc;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class RoadSignItem extends Item {

    private ResourceLocation signModelLeft;
    private ResourceLocation signModelRight;

    public RoadSignItem(Properties properties, ResourceLocation signModelLeft, ResourceLocation signModelRight) {
        super(properties);
        this.signModelLeft = signModelLeft;
        this.signModelRight = signModelRight;
    }

    public ResourceLocation getSignModelLeft() {
        return signModelLeft;
    }

    public ResourceLocation getSignModelRight() {
        return signModelRight;
    }
}
