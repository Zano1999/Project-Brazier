package net.dark_roleplay.medieval.objects.enums.properties;

import net.minecraft.util.IStringSerializable;

public enum BrazierState implements IStringSerializable{
	EMPTY("empty"),
	COAL("coal"),
	BURNING("burning");
	
	private String name;
	
	private BrazierState(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}