package net.dark_roleplay.projectbrazier.experimental_features.decals.capability;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.DecorRegistrar;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DecalContainerProvider implements ICapabilitySerializable<CompoundNBT> {
	public static final ResourceLocation NAME = new ResourceLocation(ProjectBrazier.MODID, "decor_container");

	private DecalContainer cap;
	private final LazyOptional<DecalContainer> lazyCap = LazyOptional.of(() -> cap == null ? cap = new DecalContainer() : cap);

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return cap == DecorRegistrar.DECOR ? (LazyOptional<T>) lazyCap : LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		if(cap == null) return new CompoundNBT();
		CompoundNBT compound = new CompoundNBT();
		compound.put("DecalContainer", cap.serializeNBT());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if(nbt.isEmpty()) return;
		if(nbt.contains("DecalContainer")){
			cap = new DecalContainer();
			cap.deserializeNBT(nbt.getList("DecalContainer", Constants.NBT.TAG_COMPOUND));
		}
	}
}
