package net.dark_roleplay.projectbrazier.experimental_features.roofs;

import net.minecraft.util.IStringSerializable;

public enum RoofType implements IStringSerializable {
	FULL("full", true),
	HALF_TOP("half_top", true),
	HALF_BOTTOM("half_bottom", true),
	HALF_FRONT("half_front", true),
	HALF_BACK("half_back",true),
	FLAT_TOP("flat_top", false),
	FLAT_BOTTOM("flat_bottom", false);

	private final String typeName;
	private final boolean generateCorners;

	RoofType(String typeName, boolean generateCorners) {
		this.typeName = typeName;
		this.generateCorners = generateCorners;
	}

	public String getTypeName() {
		return typeName;
	}

	public boolean doesGenerateCorners() {
		return generateCorners;
	}

	@Override
	public String getString() {
		return typeName;
	}
}
