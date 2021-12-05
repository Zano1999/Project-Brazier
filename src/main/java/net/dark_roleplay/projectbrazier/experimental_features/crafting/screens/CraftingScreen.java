package net.dark_roleplay.projectbrazier.experimental_features.crafting.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.containers.CraftingScreenPlayerContainer;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.recipes.RecipeBuilder;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.widgets.RecipeOutputWidget;
import net.dark_roleplay.projectbrazier.experimental_features.screen_lib.NestableContainerScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class CraftingScreen extends NestableContainerScreen<CraftingScreenPlayerContainer> {

	private static final ResourceLocation BACKGROUND = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/crafting/crafting_background.png");
	private static final ResourceLocation WIDGETS = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/crafting/crafting_widgets.png");

	public CraftingScreen(CraftingScreenPlayerContainer screenContainer, Inventory inv, TextComponent title) {
		super(screenContainer, inv, title);
		this.imageWidth = 322;
		this.imageHeight = 166;
	}

	@Override
	protected void init() {
		super.init();
		boolean hasWorkstation = true;

		RecipeOutputWidget recipeDisplay;

		if(hasWorkstation){
			this.addChild(recipeDisplay = new RecipeOutputWidget(this.leftPos + 153, this.topPos + 7));
			//TODO Add 2 station buttons
		}else{
			this.addChild(recipeDisplay = new RecipeOutputWidget(this.leftPos + 153 + 11, this.topPos + 7));
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
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {


		this.minecraft.getTextureManager().bind(BACKGROUND);
		this.blit(matrixStack, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, 0, 0, 322, 166, 322, 166);

		this.minecraft.getTextureManager().bind(WIDGETS);
		for (Slot slot : this.getMenu().slots) {
			this.blit(matrixStack, (this.leftPos + slot.x) - 1, (this.topPos + slot.y) - 1, 166, 0, 18, 18);
		}

		super.renderBg(matrixStack, partialTicks, x, y);
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {

	}
}