package net.dark_roleplay.projectbrazier.util.capabilities;

import net.dark_roleplay.projectbrazier.util.CapabilityUtil;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerUtil {

	public static void dropContainerItems(World world, BlockPos pos) {
		TileEntity tileEntity = world.getBlockEntity(pos);
		if(tileEntity == null) return;

		LazyOptional<IItemHandler> inventory = CapabilityUtil.getInventory(world, pos);
		inventory.ifPresent(inv -> {
			for(int i = 0; i < inv.getSlots(); i++)
				InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inv.getStackInSlot(i));
		});
	}
}
