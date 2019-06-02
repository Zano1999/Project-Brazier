package net.dark_roleplay.medieval.objects.enums.properties;

import net.minecraft.util.IStringSerializable;

public enum BrazierAddons implements IStringSerializable{
	
	NONE("none"),
	IGNITER("igniter");
	
	private String name;
	
	private BrazierAddons(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}