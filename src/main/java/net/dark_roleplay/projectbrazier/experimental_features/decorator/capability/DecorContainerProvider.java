package net.dark_roleplay.projectbrazier.experimental_features.decorator.capability;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DecorContainerProvider implements ICapabilitySerializable<CompoundTag> {
	public static final ResourceLocation NAME = new ResourceLocation(ProjectBrazier.MODID, "decor_container");

	private DecorContainer cap;
	private final LazyOptional<DecorContainer> lazyCap = LazyOptional.of(() -> cap == null ? cap = new DecorContainer() : cap);

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return cap == DecorRegistrar.DECOR ? (LazyOptional<T>) lazyCap : LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if(cap == null) return new CompoundTag();
		CompoundTag compound = new CompoundTag();
		compound.put("decorContainer", cap.serializeNBT());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if(nbt.isEmpty()) return;
		if(nbt.contains("decorContainer")){
			cap = new DecorContainer();
			cap.deserializeNBT(nbt.getList("decorContainer", Constants.NBT.TAG_COMPOUND));
		}
	}
}
