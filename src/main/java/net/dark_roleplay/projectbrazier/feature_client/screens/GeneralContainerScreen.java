package net.dark_roleplay.projectbrazier.feature_client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.containers.GeneralContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class GeneralContainerScreen extends ContainerScreen<GeneralContainer> {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/generic_container.png");

	public GeneralContainerScreen(GeneralContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		this.ySize = (int) (112 + (Math.ceil(container.getTESlotCount()/9F) * 18));

		//this.titleY = 0;
		this.playerInventoryTitleY = (int) (19 + (Math.ceil(container.getTESlotCount()/9F) * 18));
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

		GuiUtils.drawContinuousTexturedBox(matrixStack, BACKGROUND, posX, posY, 0, 0, this.xSize, this.ySize, 128, 128, 10, 0);

		for (Slot slot : this.getContainer().inventorySlots) {
			this.blit(matrixStack, (posX + slot.xPos) - 1, (posY + slot.yPos) - 1, 128, 0, 18, 18);

		}
	}
}
