package net.dark_roleplay.medieval.objects.guis.generic_container;

import net.dark_roleplay.library.unstable.experimental.guis.modular.ModularGui_Drawer;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GenericContainerGui extends GuiContainer {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRoleplayMedieval.MODID,
			"textures/guis/storage/generic_storage.png");

	public GenericContainerGui(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int posX = (this.width - this.xSize) / 2;
		int posY = (this.height - this.ySize) / 2;

//		ModularGui_Drawer.drawBackground(posX, posY, this.xSize, this.ySize);

		this.mc.getTextureManager().bindTexture(BACKGROUND);
		for (Slot slot : this.inventorySlots.inventorySlots) {
			this.drawTexturedModalRect((posX + slot.xPos) - 1, (posY + slot.yPos) - 1, 0, 238, 18, 18);

		}
	}

}
