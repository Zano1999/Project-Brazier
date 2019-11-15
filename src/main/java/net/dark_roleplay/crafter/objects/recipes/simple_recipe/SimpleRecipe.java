package net.dark_roleplay.crafter.objects.recipes.simple_recipe;

import net.dark_roleplay.crafter.api.recipe.IRecipe;
import net.dark_roleplay.crafter.api.recipe_parts.IRecipePart;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class SimpleRecipe implements IRecipe {

    //TODO Add propper grouping and names
    private IRecipePart primaryOutput;
    private IRecipePart[] secondaryOutputs = new IRecipePart[0];
    private IRecipePart[] ingredients;

    private ResourceLocation id = null;
    private ResourceLocation station = null;
    private ResourceLocation group = null;
    private int craftingTime = 0;

    public SimpleRecipe(ResourceLocation id, ResourceLocation station, ResourceLocation group, IRecipePart[] output, IRecipePart[] ingredients, int craftingTime){
        this.primaryOutput = output[0];
        if(output.length > 1){
            secondaryOutputs = Arrays.copyOfRange(output, 1, output.length);
        }
        this.ingredients = ingredients;
        this.group = group;
        this.id = id;
        this.craftingTime = craftingTime;
        this.station = station == null ? new ResourceLocation("craftmanschoice:handheld") : station;
    }

    @Override
    public ResourceLocation getStation() {
        return this.station;
    }

    @Override
    public ResourceLocation getGroup() {
        return this.group;
    }

    @Override
    public ResourceLocation getId() {
        return this.id == null ? primaryOutput.getId() : this.id;
    }

    @Override
    @Nonnull
    public IRecipePart getPrimaryOut() {
        return primaryOutput;
    }

    @Override
    @Nonnull
    public IRecipePart[] getSecondrayOut() { return secondaryOutputs; }

    @Override
    @Nonnull
    public IRecipePart[] getInput() {
        return ingredients;
    }
}
