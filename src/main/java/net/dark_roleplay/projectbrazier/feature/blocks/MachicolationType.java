package net.dark_roleplay.projectbrazier.feature.blocks;

import net.minecraft.util.IStringSerializable;

public enum MachicolationType implements IStringSerializable {
	STRAIGHT("straight"),
	INNER_CORNER("inner_corner"),
	OUTER_CORNER("outer_corner");

	private final String name;

	MachicolationType(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
