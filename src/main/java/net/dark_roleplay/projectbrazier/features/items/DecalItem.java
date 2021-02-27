package net.dark_roleplay.projectbrazier.features.items;

import net.dark_roleplay.projectbrazier.experiments.decals.Decal;
import net.minecraft.item.Item;

public class DecalItem extends Item {

	private final Decal decal;

	public DecalItem(Decal decal, Properties properties) {
		super(properties);
		this.decal = decal;
	}

	public Decal getDecal(){
		return this.decal;
	}

	public String getTranslationKey() {
		return this.getDecal().getTranslationKey();
	}

}
