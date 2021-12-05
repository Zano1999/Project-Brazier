package net.dark_roleplay.projectbrazier.experimental_features.decals.capability;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityAttachListener {

	private static final ResourceLocation DecalCapabilityKey = new ResourceLocation(ProjectBrazier.MODID, "decals");

//	@CapabilityInject(DecalChunk.class)
	static Capability<DecalChunk> DECAL_CAPABILITY = null;

	/**
	 * Registered by {@link ProjectBrazier#ProjectBrazier() Constructor}
	 */
	public static void attachChunkCapability(AttachCapabilitiesEvent<Chunk> event){
		//TODO 1.17 update this vor negative world heights
		event.addCapability(DecalCapabilityKey, new DecalChunkProvider(0, event.getObject().getMaxBuildHeight()));
	}

	public static class DecalChunkProvider implements ICapabilitySerializable<CompoundTag> {
		//TODO Invalidate cap on chunk unload
		private DecalChunk cap;
		private final LazyOptional<DecalChunk> lazyCap;

		public DecalChunkProvider(int worldMin, int worldMax){
			lazyCap = LazyOptional.of(() -> cap == null ? cap = new DecalChunk(worldMin, worldMax) : cap);
		}

		@Nonnull
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
			if (capability == DECAL_CAPABILITY) {
				return lazyCap.cast();
			}
			return LazyOptional.empty();
		}

		@Override
		public CompoundTag serializeNBT() {
			if (cap == null) return new CompoundTag();
			return (CompoundTag) DECAL_CAPABILITY.writeNBT(cap, null);
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			if (nbt.isEmpty()) return;
			cap = DECAL_CAPABILITY.getDefaultInstance();
			DECAL_CAPABILITY.readNBT(cap, null, nbt);
		}
	}
}
