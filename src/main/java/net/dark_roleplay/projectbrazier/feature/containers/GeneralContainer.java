package net.dark_roleplay.projectbrazier.feature.containers;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.function.BiConsumer;

public class GeneralContainer extends Container {

	protected final BlockPos worldPos;
	private int[] inventories;

	public GeneralContainer(int windowId, PlayerInventory playerInventory, PacketBuffer extraData) {
		this(windowId, playerInventory, playerInventory.player.getEntityWorld(), extraData.readBlockPos());
	}

	public GeneralContainer(int windowId, PlayerInventory playerInventory, World world, BlockPos pos) {
		super(BrazierContainers.GENERAL_CONTAINER.get(), windowId);
		this.worldPos = pos;
		TileEntity te = world.getTileEntity(pos);
		LazyOptional<IItemHandler> optionalInventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

		for(int x = 0; x < 9; x++ )
			this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));

		executeGrid(9, 27, (x, y) -> {
			this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
		});

		optionalInventory.ifPresent((handler) -> {
			inventories =  new int[]{9, 27, handler.getSlots() };

			executeGrid(9, handler.getSlots(), (x, y) -> {
				this.addSlot(new SlotItemHandler(handler, x + (y * 9), 8 + (x * 18), 17 + (y * 18)));
			});
		});

		if(inventories == null)
			inventories =  new int[]{9, 27};

	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return this.worldPos.withinDistance(player.getPositionVec(), 5);
	}

	//TODO Properly Implement Shift Clicking
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack result =  ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot == null || !slot.getHasStack()) return result;
		ItemStack stack = slot.getStack();
		result = stack.copy();

		int inventoryID = 0;
		int helper;
		for(helper = inventories[0]; inventoryID + 1 < inventories.length && index >= helper; inventoryID++, helper += inventories[inventoryID]);

		//TRY to insert into container first
		if(inventoryID != 2)
			this.mergeItemStack(stack, 36, 36 + inventories[2], false);

		boolean merged = false;
		for(int i = 0, j = 0; i < inventories.length && !stack.isEmpty(); j += inventories[i], i++){
			if(i == inventoryID) continue;
			merged |= this.mergeItemStack(stack, j, j + inventories[i], false);
		}
		if(!merged) return ItemStack.EMPTY;

		slot.onSlotChange(stack, result);

		if(stack.isEmpty())
			slot.putStack(ItemStack.EMPTY);
		else slot.onSlotChanged();

		if(stack.getCount() == result.getCount())
			return ItemStack.EMPTY;

		slot.onTake(player, stack);

		return result;
	}

	private void executeGrid(int width, int size, BiConsumer<Integer, Integer> method) {
		float widthF = width;
		for(int i = 0; i < size; i++) {
			method.accept(i % width, (int) Math.floor(i / widthF));
		}
	}
}
