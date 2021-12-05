package net.dark_roleplay.projectbrazier.experimental_features.crafting;

import net.dark_roleplay.projectbrazier.experimental_features.crafting.containers.CraftingScreenPlayerContainer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class CraftingHelper implements MenuProvider {

	public static void openCraftingScreen(Player player){
		if(player instanceof ServerPlayer)
			NetworkHooks.openGui((ServerPlayer) player, new CraftingHelper());
	}

	@Override
	public TextComponent getDisplayName() {
		return new TranslatableComponent("screen.projectbrazier.crafting");
	}

	@Nullable
	@Override
	public Container createMenu(int windowId, Inventory inv, Player player) {
		return new CraftingScreenPlayerContainer(windowId, inv);
	}
}
