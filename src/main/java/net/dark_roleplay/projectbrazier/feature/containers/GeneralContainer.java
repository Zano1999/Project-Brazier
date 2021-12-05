package net.dark_roleplay.projectbrazier.feature.containers;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.function.BiConsumer;

public class GeneralContainer extends Container {

	protected final BlockPos worldPos;
	private int[] inventories;
	private int teSlots;

	public GeneralContainer(int windowId, Inventory playerInventory, FriendlyByteBuf extraData) {
		this(windowId, playerInventory, playerInventory.player.getCommandSenderWorld(), extraData.readBlockPos());
	}

	public GeneralContainer(int windowId, Inventory playerInventory, Level world, BlockPos pos) {
		super(BrazierContainers.GENERAL_CONTAINER.get(), windowId);
		this.worldPos = pos;
		BlockEntity te = world.getBlockEntity(pos);
		LazyOptional<IItemHandler> optionalInventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

		optionalInventory.ifPresent((handler) -> {
			this.teSlots = handler.getSlots();
			inventories =  new int[]{9, 27, handler.getSlots() };

			executeGrid(9, handler.getSlots(), (x, y) -> {
				this.addSlot(new SlotItemHandler(handler, x + (y * 9), 8 + (x * 18), 17 + (y * 18)));
			});
		});

		int yOffset = (int) (30 + (Math.ceil(teSlots/9F) * 18));

		for(int x = 0; x < 9; x++ )
			this.addSlot(new Slot(playerInventory, x, 8 + x * 18, yOffset + 56));

		executeGrid(9, 27, (x, y) -> {
			this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, yOffset + y * 18));
		});

		if(inventories == null)
			inventories =  new int[]{9, 27};

	}

	public int getTESlotCount(){
		return this.teSlots;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.worldPos.closerThan(player.position(), 5);
	}

	//TODO Properly Implement Shift Clicking
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack result =  ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if (slot == null || !slot.hasItem()) return result;
		ItemStack stack = slot.getItem();
		result = stack.copy();

		int inventoryID = 0;
		int helper;
		for(helper = inventories[0]; inventoryID + 1 < inventories.length && index >= helper; inventoryID++, helper += inventories[inventoryID]);

		//TRY to insert into container first
		if(inventoryID != 2)
			this.moveItemStackTo(stack, 36, 36 + inventories[2], false);

		boolean merged = false;
		for(int i = 0, j = 0; i < inventories.length && !stack.isEmpty(); j += inventories[i], i++){
			if(i == inventoryID) continue;
			merged |= this.moveItemStackTo(stack, j, j + inventories[i], false);
		}
		if(!merged) return ItemStack.EMPTY;

		slot.onQuickCraft(stack, result);

		if(stack.isEmpty())
			slot.set(ItemStack.EMPTY);
		else slot.setChanged();

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
