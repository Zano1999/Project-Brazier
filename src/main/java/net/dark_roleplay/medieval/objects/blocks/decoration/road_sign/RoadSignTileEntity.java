package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.dark_roleplay.medieval.holders.MedievalTileEntities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class RoadSignTileEntity extends TileEntity {
	
	public RoadSignTileEntity() {
		super(MedievalTileEntities.ROAD_SIGN);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
		}

		return super.getCapability(capability, side);
	}

	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);

	}

	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		return super.write(compound);
	}
}
