package net.dark_roleplay.medieval.util.blocks;

import com.google.gson.JsonObject;

import net.minecraft.util.IStringSerializable;

public interface IShapeCondition {

	public void init(JsonObject obj);
	
	public static enum ShapeConditions implements IShapeCondition, IStringSerializable{
		PROPERTY("property"){
			//Map<String, String>
		
		
			@Override
			public void init(JsonObject obj) {
				
			}
		};

	    private final String name;
	    
	    private ShapeConditions(String name) {
	    	this.name = name;
	    }
	    
		@Override
		public String getName() {
			return name;
		}
	}
}
