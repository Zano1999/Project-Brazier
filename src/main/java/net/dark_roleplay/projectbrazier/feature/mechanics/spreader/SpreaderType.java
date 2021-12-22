package net.dark_roleplay.projectbrazier.feature.mechanics.spreader;

import java.util.HashMap;
import java.util.Map;

public final class SpreaderType {
	private static final Map<String, SpreaderType> TYPES = new HashMap<>();

	public static final SpreaderType GRASS = getType("grass");
	public static final SpreaderType MYCELIUM = getType("mycelium");
	public static final SpreaderType CRIMSON = getType("crimson");
	public static final SpreaderType WARPED = getType("warped");
	public static final SpreaderType REVERT = getType("revert");

	public static SpreaderType getType(String name){
		return TYPES.computeIfAbsent(name, key -> new SpreaderType(key));
	}

	private String name;

	private SpreaderType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
