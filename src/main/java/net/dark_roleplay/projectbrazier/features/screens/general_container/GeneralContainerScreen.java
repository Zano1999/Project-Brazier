package net.dark_roleplay.projectbrazier.features.screens.general_container;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GeneralContainerScreen extends ContainerScreen<GeneralContainer> {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/general_storage.png");

	public GeneralContainerScreen(GeneralContainer inventorySlotsIn, PlayerInventory playerInventory, ITextComponent title) {
		super(inventorySlotsIn, playerInventory, title);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		int posX = (this.width - this.xSize) / 2;
		int posY = (this.height - this.ySize) / 2;

//		ModularGui_Drawer.drawBackground(posX, posY, this.xSize, this.ySize);

		this.minecraft.getTextureManager().bindTexture(BACKGROUND);
		for (Slot slot : this.getContainer().inventorySlots) {
			this.blit(matrixStack, (posX + slot.xPos) - 1, (posY + slot.yPos) - 1, 0, 238, 18, 18);

		}
	}
}
