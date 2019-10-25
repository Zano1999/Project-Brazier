package net.dark_roleplay.medieval.objects.guis.generic_container;

import net.dark_roleplay.medieval.handler_2.MedievalContainers;
import net.dark_roleplay.medieval.objects.helper.LambdaHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GenericContainer extends Container {

	protected final IWorldPosCallable worldPos;
	
	public GenericContainer(int id, PlayerInventory playerInventory) {
		this(id, playerInventory, IWorldPosCallable.DUMMY);
	}

	public GenericContainer(int id, PlayerInventory playerInventory, final IWorldPosCallable worldPos) {
		super(MedievalContainers.GENERIC_CONTAINER.get(), id);
		this.worldPos = worldPos;
		worldPos.consume((world, pos) -> {
			TileEntity te = world.getTileEntity(pos);
			LazyOptional<IItemHandler> optionalInventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			
			optionalInventory.ifPresent((handler) -> {
				LambdaHelper.executeGrid(9, handler.getSlots(), (x, y) -> {
					this.addSlot(new SlotItemHandler(handler, x + (y * 9), 8 + (x * 18), 8 + (y * 18)));
				});

				LambdaHelper.executeGrid(9, 27, (x, y) -> {
					this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
				});

				for(int x = 0; x < 9; x++ )
					this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
			});
		});
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return (boolean) this.worldPos.<Boolean>applyOrElse((world, pos) -> {
			return pos.distanceSq(player.posX, player.posY, player.posZ, true) < 25;
		}, false);
	}
}
