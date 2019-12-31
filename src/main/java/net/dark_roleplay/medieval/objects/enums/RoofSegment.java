package net.dark_roleplay.medieval.objects.enums;

import net.minecraft.util.IStringSerializable;

public enum RoofSegment implements IStringSerializable {

    STRAIGHT("straight", true),
    STRAIGHT_LEFT("straight_left", false),
    STREIGHT_RIGHT("straight_right", false),
    INNER_CORNER("inner_corner", true),
    OUTER_CORNER("outer_corner", true);

    private final String name;
    private boolean isNormalType;

    private RoofSegment(String name, boolean isNormalType) {
        this.name = name;
        this.isNormalType = isNormalType;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isNormalType(){
        return isNormalType;
    }

}
