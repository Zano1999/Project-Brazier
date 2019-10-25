package net.dark_roleplay.medieval.objects.enums;

import net.minecraft.util.IStringSerializable;

public enum RoofSegment implements IStringSerializable {

    STRAIGHT("straight"),
    INNER_CORNER("inner_corner"),
    OUTER_CORNER("outer_corner");

    private final String name;

    private RoofSegment(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
