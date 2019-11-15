package net.dark_roleplay.crafter.api.recipe;

import net.dark_roleplay.crafter.api.recipe_parts.IRecipePart;
import net.minecraft.util.ResourceLocation;

public interface IRecipe {

    ResourceLocation getStation();
    ResourceLocation getGroup();
    ResourceLocation getId();
    IRecipePart getPrimaryOut();
    IRecipePart[] getSecondrayOut();
    IRecipePart[] getInput();
}
