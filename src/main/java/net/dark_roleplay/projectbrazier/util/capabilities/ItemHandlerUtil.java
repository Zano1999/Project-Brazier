package net.dark_roleplay.projectbrazier.util.capabilities;

import net.dark_roleplay.projectbrazier.util.CapabilityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerUtil {

	public static void dropContainerItems(Level world, BlockPos pos) {
		BlockEntity tileEntity = world.getBlockEntity(pos);
		if(tileEntity == null) return;

		LazyOptional<IItemHandler> inventory = CapabilityUtil.getInventory(world, pos);
		inventory.ifPresent(inv -> {
			for(int i = 0; i < inv.getSlots(); i++)
				InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inv.getStackInSlot(i));
		});
	}
}
