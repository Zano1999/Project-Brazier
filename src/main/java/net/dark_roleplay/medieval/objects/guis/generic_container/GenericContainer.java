package net.dark_roleplay.medieval.objects.guis.generic_container;

import net.dark_roleplay.medieval.objects.helper.LambdaHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GenericContainer extends Container {

	protected TileEntity te;
	protected int slotCount;

	public GenericContainer(TileEntity te, InventoryPlayer playerInventory) {
		this.te = te;

		LazyOptional<IItemHandler> optionalInventory = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		optionalInventory.ifPresent((handler) -> {
			this.slotCount = handler.getSlots();
			
			LambdaHelper.executeGrid(9, this.slotCount, (x, y) -> {
				this.addSlot(new SlotItemHandler(handler, x + (y * 9), 8 + (x * 18), 8 + (y * 18)));
			});
			
			LambdaHelper.executeGrid(9, 27, (x, y) -> {
				this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			});
			
			for (int x = 0; x < 9; x++) this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
		});
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		Vec3d playerPos = player.getPositionVector();
		return this.te.getPos().distanceSqToCenter(playerPos.x, playerPos.y, playerPos.z) < 25;
	}

}
