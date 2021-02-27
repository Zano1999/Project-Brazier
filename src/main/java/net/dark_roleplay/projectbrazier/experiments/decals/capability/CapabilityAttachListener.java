package net.dark_roleplay.projectbrazier.experiments.decals.capability;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
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
		event.addCapability(DecalCapabilityKey, new DecalChunkProvider(0, event.getObject().getHeight()));
	}

	public static class DecalChunkProvider implements ICapabilitySerializable<CompoundNBT> {
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
		public CompoundNBT serializeNBT() {
			if (cap == null) return new CompoundNBT();
			return (CompoundNBT) DECAL_CAPABILITY.writeNBT(cap, null);
		}

		@Override
		public void deserializeNBT(CompoundNBT nbt) {
			if (nbt.isEmpty()) return;
			cap = DECAL_CAPABILITY.getDefaultInstance();
			DECAL_CAPABILITY.readNBT(cap, null, nbt);
		}
	}
}
