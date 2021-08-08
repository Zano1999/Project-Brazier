package net.dark_roleplay.projectbrazier.experimental_features.brewing.map;

public enum TileType {
	NONE(0, 0),
	EMPTY(0, 0),
	UNEXPLORED(19, 0),
	FLAME(38, 0),
	WATER(57, 0);

	private int u, v;

	TileType(int u, int v) {
		this.u = u;
		this.v = v;
	}

	public int getU() {
		return u;
	}

	public int getV() {
		return v;
	}
}
