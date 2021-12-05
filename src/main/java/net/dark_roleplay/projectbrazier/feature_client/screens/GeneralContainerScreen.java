package net.dark_roleplay.projectbrazier.feature_client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.containers.GeneralContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.gui.GuiUtils;

public class GeneralContainerScreen extends AbstractContainerScreen<GeneralContainer> {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/generic_container.png");

	public GeneralContainerScreen(GeneralContainer container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		this.imageHeight = (int) (112 + (Math.ceil(container.getTESlotCount()/9F) * 18));

		//this.titleY = 0;
		this.inventoryLabelY = (int) (19 + (Math.ceil(container.getTESlotCount()/9F) * 18));
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
		int posX = (this.width - this.imageWidth) / 2;
		int posY = (this.height - this.imageHeight) / 2;

		GuiUtils.drawContinuousTexturedBox(matrixStack, BACKGROUND, posX, posY, 0, 0, this.imageWidth, this.imageHeight, 128, 128, 10, 0);

		for (Slot slot : this.getMenu().slots) {
			this.blit(matrixStack, (posX + slot.x) - 1, (posY + slot.y) - 1, 128, 0, 18, 18);

		}
	}
}
