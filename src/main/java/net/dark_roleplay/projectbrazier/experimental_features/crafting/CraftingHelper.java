package net.dark_roleplay.projectbrazier.experimental_features.crafting;

import net.dark_roleplay.projectbrazier.experimental_features.crafting.containers.CraftingScreenPlayerContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class CraftingHelper implements INamedContainerProvider{

	public static void openCraftingScreen(PlayerEntity player){
		if(player instanceof ServerPlayerEntity)
			NetworkHooks.openGui((ServerPlayerEntity) player, new CraftingHelper());
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("screen.projectbrazier.crafting");
	}

	@Nullable
	@Override
	public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player) {
		return new CraftingScreenPlayerContainer(windowId, inv);
	}
}
