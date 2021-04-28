package net.dark_roleplay.projectbrazier.experimental_features.crafting.recipes;

import net.minecraft.item.ItemStack;

import java.util.List;

public class Recipe {
	private List<ItemStack> inputs;
	private List<ItemStack> outputs;

	public Recipe(List<ItemStack> inputs, List<ItemStack> outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
	}

	public List<ItemStack> getInputs() {
		return inputs;
	}

	public List<ItemStack> getOutputs() {
		return outputs;
	}
}
