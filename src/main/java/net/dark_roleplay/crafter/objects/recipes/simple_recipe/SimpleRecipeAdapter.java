package net.dark_roleplay.crafter.objects.recipes.simple_recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dark_roleplay.crafter.api.recipe.RecipeAdapter;
import net.dark_roleplay.crafter.api.recipe_parts.IRecipePart;
import net.dark_roleplay.crafter.api.recipe_parts.RecipePartAdapter;
import net.dark_roleplay.crafter.handler.Registries;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SimpleRecipeAdapter extends RecipeAdapter<SimpleRecipe>{
    @Override
    public SimpleRecipe read(ResourceLocation recipeId, JsonObject json) {
        ResourceLocation station = new ResourceLocation(JSONUtils.getString(json, "station", "craftmanschoice:handheld"));
        ResourceLocation group = new ResourceLocation(JSONUtils.getString(json, "group", "craftmanschoice:none"));
        ResourceLocation id = json.has("id") ? new ResourceLocation(JSONUtils.getString(json, "id")) : null;
        int craftingTime = JSONUtils.getInt(json, "time", 0);

        JsonArray outputsJson = JSONUtils.getJsonArray(json, "output", new JsonArray());
        IRecipePart[] outputs = new IRecipePart[outputsJson.size()];

        for(int i = 0; i < outputsJson.size(); i++){
            JsonObject outputJson = outputsJson.get(i).getAsJsonObject();
            RecipePartAdapter adapter = Registries.RECIPE_PART_ADAPTERS.getValue(new ResourceLocation(JSONUtils.getString(outputJson, "type", "craftmanschoice:item")));
            if(adapter != null)
                outputs[i] = adapter.read(outputJson);
        }

        JsonArray inputsJson = JSONUtils.getJsonArray(json, "input", new JsonArray());
        IRecipePart[] inputs = new IRecipePart[inputsJson.size()];

        for(int i = 0; i < inputsJson.size(); i++){
            JsonObject inputJson = inputsJson.get(i).getAsJsonObject();
            RecipePartAdapter adapter = Registries.RECIPE_PART_ADAPTERS.getValue(new ResourceLocation(JSONUtils.getString(inputJson, "type", "craftmanschoice:item")));
            if(adapter != null)
                inputs[i] = adapter.read(inputJson);
        }

        return new SimpleRecipe(id, station, group, outputs, inputs, craftingTime);
    }

    @Nullable
    @Override
    public SimpleRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        return null;
    }

    @Override
    public void write(PacketBuffer buffer, SimpleRecipe recipe) {

    }
}
