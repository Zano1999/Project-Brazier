package net.dark_roleplay.crafter.api.recipe;

import net.dark_roleplay.crafter.api.recipe_parts.IRecipePart;
import net.dark_roleplay.crafter.objects.guis.RecipeWidget;
import net.minecraft.util.ResourceLocation;

public interface IRecipe {

    RecipeWidget getListWidget();

    ResourceLocation getStation();
    ResourceLocation getGroup();
    ResourceLocation getId();
    IRecipePart getPrimaryOut();
    IRecipePart[] getSecondrayOut();
    IRecipePart[] getInput();
}
