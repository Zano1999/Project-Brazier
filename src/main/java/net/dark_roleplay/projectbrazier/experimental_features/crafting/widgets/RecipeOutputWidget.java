package net.dark_roleplay.projectbrazier.experimental_features.crafting.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.crafting.recipes.Recipe;
import net.dark_roleplay.projectbrazier.experimental_features.screen_lib.NestedWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RecipeOutputWidget extends NestedWidget {
	private static final ResourceLocation WIDGETS = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/crafting/crafting_widgets.png");

	private Recipe recipe;
	private List<NestedWidget> recipeSpecificWidgets = new ArrayList<>();

	public RecipeOutputWidget(int x, int y) {
		super(x, y, 138, 72);

//		int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, int yDiffTextIn, ResourceLocation resourceLocationIn, Button.IPressable onPressIn
		this.addChild(new ImageButton(this.posX + 67, this.posY + 36, 24, 24, 0, 184, 24, WIDGETS, button -> {}));
	}

	public void setRecipe(Recipe recipe){
		this.recipe = recipe;

		for(NestedWidget widget : recipeSpecificWidgets)
			this.removeChild(widget);

		recipeSpecificWidgets.clear();

		List<ItemStack> outputs = recipe.getOutputs();
		for(int i = 0; i < outputs.size(); i++){
			recipeSpecificWidgets.add(new OutputDisplayWidget(this.posX + 96, this.posY + 1, outputs, i));
		}

		for(NestedWidget widget : recipeSpecificWidgets)
			this.addChild(widget);
	}

	public Recipe getRecipe() {
		return recipe;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		Minecraft.getInstance().getTextureManager().bindTexture(WIDGETS);

		this.blit(matrixStack, this.posX, this.posY, 0, 16, 138, 72);


		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
}
