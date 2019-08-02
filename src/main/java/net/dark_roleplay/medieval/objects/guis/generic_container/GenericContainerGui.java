package net.dark_roleplay.medieval.objects.guis.generic_container;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class GenericContainerGui<T extends Container> extends ContainerScreen<T> {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRoleplayMedieval.MODID,
			"textures/guis/storage/generic_storage.png");

	public GenericContainerGui(T inventorySlotsIn) {
		super(inventorySlotsIn, (PlayerInventory) null, new TranslationTextComponent("drpmedieval.gui.title.generic_storage"));
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int posX = (this.width - this.xSize) / 2;
		int posY = (this.height - this.ySize) / 2;

//		ModularGui_Drawer.drawBackground(posX, posY, this.xSize, this.ySize);

		this.minecraft.getTextureManager().bindTexture(BACKGROUND);
		for (Slot slot : this.getContainer().inventorySlots) {
			this.blit((posX + slot.xPos) - 1, (posY + slot.yPos) - 1, 0, 238, 18, 18);

		}
	}

}
