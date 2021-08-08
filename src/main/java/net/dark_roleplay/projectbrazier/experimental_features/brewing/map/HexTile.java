package net.dark_roleplay.projectbrazier.experimental_features.brewing.map;

import net.dark_roleplay.projectbrazier.experimental_features.brewing.HexPos;

public class HexTile {
	private HexPos position;
	private TileType type = TileType.EMPTY;

	public HexTile(HexPos position) {
		this.position = position;
	}

	public HexPos getPosition() {
		return position;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
}
