package net.dark_roleplay.projectbrazier.experimental_features.crafting.containers;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

import java.util.function.BiConsumer;

public class CraftingScreenPlayerContainer extends Container {

	private int slotsOffsetX = 154, slotsOffsetY = 84;


	public CraftingScreenPlayerContainer(int windowId, Inventory inv, FriendlyByteBuf buffer){
		this(windowId, inv);
	}

	public CraftingScreenPlayerContainer(int windowId, Inventory inv){
		super(BrazierContainers.CRAFTING_PLAYER_CONTAINER.get(), windowId);

		for(int x = 0; x < 9; x++ )
			this.addSlot(new Slot(inv, x, slotsOffsetX + x * 18, slotsOffsetY + 58));

		executeGrid(9, 27, (x, y) -> {
			this.addSlot(new Slot(inv, x + y * 9 + 9, slotsOffsetX + x * 18, slotsOffsetY + y * 18));
		});
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	private void executeGrid(int width, int size, BiConsumer<Integer, Integer> method) {
		float widthF = width;
		for(int i = 0; i < size; i++) {
			method.accept(i % width, (int) Math.floor(i / widthF));
		}
	}
}
