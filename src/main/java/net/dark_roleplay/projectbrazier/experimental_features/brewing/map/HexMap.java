package net.dark_roleplay.projectbrazier.experimental_features.brewing.map;

import net.dark_roleplay.projectbrazier.experimental_features.brewing.HexPos;

import java.util.Collection;
import java.util.HashSet;

public class HexMap {
	private Collection<HexTile> tiles = new HashSet<>();

	public HexMap(int radius){
		for(int q =  -radius; q <= radius; q++){
			int r1 = Math.max(-radius, -q-radius);
			int r2 = Math.min(radius, -q+radius);
			for(int r =r1; r<=r2; r++) {
				HexTile tile = new HexTile(new HexPos(q, r, -q - r));
				tiles.add(tile);

				int exploredDistance = 5;

				if(r > exploredDistance || q > exploredDistance || -q-r > exploredDistance || r < -exploredDistance || q < -exploredDistance || -q-r < -exploredDistance)
					tile.setType(TileType.UNEXPLORED);
			}
		}

		for(HexTile tile : tiles){
			if(tile.getType() == TileType.UNEXPLORED) continue;
			double rand = Math.random();
			if(rand > 0.9)
				tile.setType(TileType.NONE);
			else if(rand > 0.85)
				tile.setType(TileType.FLAME);
			else if(rand > 0.7)
				tile.setType(TileType.WATER);
		}
	}

	public Collection<HexTile> getTiles() {
		return tiles;
	}
}
