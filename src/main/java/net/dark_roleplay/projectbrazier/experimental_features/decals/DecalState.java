package net.dark_roleplay.projectbrazier.experimental_features.decals;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.state.Property;
import net.minecraft.state.StateHolder;

public class DecalState extends StateHolder<Decal, DecalState> {

//	public static final Codec<DecalState> CODEC = codec(DecalRegistry.REGISTRY, Decal::getDefaultState).stable();


	protected DecalState(Decal decal, ImmutableMap<Property<?>, Comparable<?>> properties, MapCodec<DecalState> codec) {
		super(decal, properties, codec);
	}
}
