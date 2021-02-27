package net.dark_roleplay.projectbrazier.experiments.decals;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.dark_roleplay.projectbrazier.experiments.decals.capability.DecalRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.state.StateHolder;
import net.minecraft.util.registry.Registry;

public class DecalState extends StateHolder<Decal, DecalState> {

//	public static final Codec<DecalState> CODEC = func_235897_a_(DecalRegistry.REGISTRY, Decal::getDefaultState).stable();


	protected DecalState(Decal decal, ImmutableMap<Property<?>, Comparable<?>> properties, MapCodec<DecalState> codec) {
		super(decal, properties, codec);
	}
}
