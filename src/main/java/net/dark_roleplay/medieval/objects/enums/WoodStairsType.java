package net.dark_roleplay.medieval.objects.enums;

import net.minecraft.util.IStringSerializable;

public enum WoodStairsType implements IStringSerializable {
    SINGLE("single"),
    DOUBLE("double");

    private final String name;

    private WoodStairsType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
