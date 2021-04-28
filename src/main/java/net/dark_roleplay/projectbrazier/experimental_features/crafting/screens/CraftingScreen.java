package net.dark_roleplay.projectbrazier.experimental_features.crafting.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.containers.CraftingScreenPlayerContainer;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.recipes.Recipe;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.recipes.RecipeBuilder;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.widgets.RecipeOutputWidget;
import net.dark_roleplay.projectbrazier.experimental_features.screen_lib.NestableContainerScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CraftingScreen extends NestableContainerScreen<CraftingScreenPlayerContainer> {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/crafting/crafting_background.png");
	private static final ResourceLocation WIDGETS = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/crafting/crafting_widgets.png");

	public CraftingScreen(CraftingScreenPlayerContainer screenContainer, PlayerInventory inv, ITextComponent title) {
		super(screenContainer, inv, title);
		this.xSize = 322;
		this.ySize = 166;
	}

	@Override
	protected void init() {
		super.init();
		boolean hasWorkstation = true;

		RecipeOutputWidget recipeDisplay;

		if(hasWorkstation){
			this.addChild(recipeDisplay = new RecipeOutputWidget(this.guiLeft + 153, this.guiTop + 7));
			//TODO Add 2 station buttons
		}else{
			this.addChild(recipeDisplay = new RecipeOutputWidget(this.guiLeft + 153 + 11, this.guiTop + 7));
		}

//		recipeDisplay.setRecipe(new RecipeBuilder()
//				.addInput(new ItemStack(Items.DIRT, 1))
//				.addOutput(new ItemStack(Items.DIAMOND, 1))
//				.createRecipe());

		recipeDisplay.setRecipe(new RecipeBuilder()
				.addInput(new ItemStack(Items.DIRT, 1))
				.addOutput(new ItemStack(Items.DIAMOND, 1))
				.addOutput(new ItemStack(Items.NETHER_STAR, 2))
				.addOutput(new ItemStack(Items.APPLE, 3))
				.createRecipe());
//
//		recipeDisplay.setRecipe(new RecipeBuilder()
//				.addInput(new ItemStack(Items.DIRT, 1))
//				.addOutput(new ItemStack(Items.DIAMOND, 1))
//				.addOutput(new ItemStack(Items.NETHER_STAR, 2))
//				.addOutput(new ItemStack(Items.APPLE, 3))
//				.addOutput(new ItemStack(Items.ITEM_FRAME, 4))
//				.addOutput(new ItemStack(Items.TRIDENT, 5))
//				.addOutput(new ItemStack(Items.PUMPKIN, 6))
//				.addOutput(new ItemStack(Items.TRIDENT, 5))
//				.addOutput(new ItemStack(Items.PUMPKIN, 6))
//				.addOutput(new ItemStack(Items.TRIDENT, 5))
//				.addOutput(new ItemStack(Items.PUMPKIN, 6))
//				.addOutput(new ItemStack(Items.TRIDENT, 5))
//				.addOutput(new ItemStack(Items.PUMPKIN, 6))
//				.createRecipe());
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {


		this.minecraft.getTextureManager().bindTexture(BACKGROUND);
		this.blit(matrixStack, this.guiLeft, this.guiTop, this.xSize, this.ySize, 0, 0, 322, 166, 322, 166);

		this.minecraft.getTextureManager().bindTexture(WIDGETS);
		for (Slot slot : this.getContainer().inventorySlots) {
			this.blit(matrixStack, (this.guiLeft + slot.xPos) - 1, (this.guiTop + slot.yPos) - 1, 166, 0, 18, 18);
		}

		super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, x, y);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {

	}
}