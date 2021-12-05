package net.dark_roleplay.projectbrazier.experimental_features.crafting.recipes;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {
	private List<ItemStack> inputs = new ArrayList<>();
	private List<ItemStack> outputs = new ArrayList<>();

	public RecipeBuilder addInput(ItemStack input) {
		this.inputs.add(input);
		return this;
	}

	public RecipeBuilder addOutput(ItemStack output) {
		this.outputs.add(output);
		return this;
	}

	public Recipe createRecipe() {
		return new Recipe(inputs, outputs);
	}
}