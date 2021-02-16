package net.dark_roleplay.projectbrazier.experiments.decals.capability;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.dark_roleplay.projectbrazier.experiments.decals.DecalState;

public class DecalChunk {
	private Int2ObjectMap<DecalState>[] decalChunks;

	private static class DecalSubChunk{
		private Int2ObjectMap<DecalState> decals;

		public DecalSubChunk(){
			this.decals = new Int2ObjectOpenHashMap<>();

		}
	}
}
