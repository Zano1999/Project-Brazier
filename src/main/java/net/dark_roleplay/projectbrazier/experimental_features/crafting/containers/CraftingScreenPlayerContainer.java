package net.dark_roleplay.projectbrazier.experimental_features.crafting.containers;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;

import java.util.function.BiConsumer;

public class CraftingScreenPlayerContainer extends Container {

	private int slotsOffsetX = 154, slotsOffsetY = 84;


	public CraftingScreenPlayerContainer(int windowId, PlayerInventory inv, PacketBuffer buffer){
		this(windowId, inv);
	}

	public CraftingScreenPlayerContainer(int windowId, PlayerInventory inv){
		super(BrazierContainers.CRAFTING_PLAYER_CONTAINER.get(), windowId);

		for(int x = 0; x < 9; x++ )
			this.addSlot(new Slot(inv, x, slotsOffsetX + x * 18, slotsOffsetY + 58));

		executeGrid(9, 27, (x, y) -> {
			this.addSlot(new Slot(inv, x + y * 9 + 9, slotsOffsetX + x * 18, slotsOffsetY + y * 18));
		});
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return true;
	}

	private void executeGrid(int width, int size, BiConsumer<Integer, Integer> method) {
		float widthF = width;
		for(int i = 0; i < size; i++) {
			method.accept(i % width, (int) Math.floor(i / widthF));
		}
	}
}
