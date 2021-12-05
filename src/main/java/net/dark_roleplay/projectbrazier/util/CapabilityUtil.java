package net.dark_roleplay.projectbrazier.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CapabilityUtil {

	public static LazyOptional<IItemHandler> getInventory(Level world, BlockPos pos){
		BlockEntity te = world.getBlockEntity(pos);
		return te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
	}
}
