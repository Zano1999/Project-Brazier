package net.dark_roleplay.projectbrazier.experimental_features.roofs;

import net.dark_roleplay.projectbrazier.feature.registrars.IFancyNamer;
import net.minecraft.util.StringRepresentable;

public enum RoofType implements StringRepresentable, IFancyNamer {
	NORMAL("normal", "%s", true),
	SHALLOW_TOP("shallow_top", "shallow_%s_top", true),
	SHALLOW_BOTTOM("shallow_bottom", "shallow_%s_bottom", true),
	STEEP_TOP("steep_top", "steep_%s_top",true),
	STEEP_BOTTOM("steep_bottom", "steep_%s_bottom", true),
	FLAT_TOP("flat_top", "flat_%s_top", false),
	FLAT_BOTTOM("flat_bottom", "flat_%s_bottom", false);

	private final String typeName;
	private final String registryName;
	private final boolean generateCorners;

	RoofType(String typeName, String registryName, boolean generateCorners) {
		this.typeName = typeName;
		this.registryName = registryName;
		this.generateCorners = generateCorners;
	}

	public String processFancyName(String input){
		return String.format(this.registryName, input);
	}

	public String getTypeName() {
		return typeName;
	}

	public boolean doesGenerateCorners() {
		return generateCorners;
	}

	@Override
	public String getSerializedName() {
		return typeName;
	}
}
