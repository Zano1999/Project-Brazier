package net.dark_roleplay.medieval.objects.enums;

import net.minecraft.util.IStringSerializable;

public enum PlatformHeight implements IStringSerializable{

	FULL("full"),
	HALF("half");

    private final String name;
    
    private PlatformHeight(String name) {
    	this.name = name;
    }
    
	@Override
	public String getName() {
		return name;
	}
	
}
