package net.dark_roleplay.projectbrazier.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Optional;

public class CapabilityUtil {

	public static LazyOptional<IItemHandler> getInventory(World world, BlockPos pos){
		TileEntity te = world.getTileEntity(pos);
		return te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
	}
}
