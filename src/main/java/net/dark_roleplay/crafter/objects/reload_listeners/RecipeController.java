package net.dark_roleplay.crafter.objects.reload_listeners;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.*;
import net.dark_roleplay.crafter.api.recipe.IRecipe;
import net.dark_roleplay.crafter.api.recipe.RecipeAdapter;
import net.dark_roleplay.crafter.handler.Registries;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public final class RecipeController extends JsonReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public static RecipeController INSTANCE = new RecipeController();

    private static final Logger LOGGER = LogManager.getLogger();
    private Map<ResourceLocation, Map<ResourceLocation, IRecipe>> recipes = ImmutableMap.of();

    private RecipeController() { super(GSON, "craftmanschoice/recipes"); }

    @Override
    protected void apply(Map<ResourceLocation, JsonObject> splashList, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        Map<ResourceLocation, ImmutableMap.Builder<ResourceLocation, IRecipe>> map = Maps.newHashMap();

        for(Map.Entry<ResourceLocation, JsonObject> entry : splashList.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();

            try {
                if (!net.minecraftforge.common.crafting.CraftingHelper.processConditions(entry.getValue(), "conditions")) {
                    LOGGER.info("Skipping loading recipe {} as it's conditions were not met", resourcelocation);
                    continue;
                }
                IRecipe irecipe = deserializeRecipe(resourcelocation, entry.getValue());
                if (irecipe == null) {
                    LOGGER.info("Skipping loading recipe {} as it's serializer returned null", resourcelocation);
                    continue;
                }
                map.computeIfAbsent(irecipe.getStation(), (p_223391_0_) -> {
                    return ImmutableMap.builder();
                }).put(resourcelocation, irecipe);
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                LOGGER.error("Parsing error loading recipe {}", resourcelocation, jsonparseexception);
            }
        }

        this.recipes = map.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, (p_223400_0_) -> {
            return p_223400_0_.getValue().build();
        }));
        LOGGER.info("Loaded {} recipes", (int)map.size());
    }

    public Map<ResourceLocation, IRecipe> getRecipes(ResourceLocation station){
        return recipes.get(station);
    }

    public static IRecipe deserializeRecipe(ResourceLocation recipeId, JsonObject json) {
        String s = JSONUtils.getString(json, "type", "craftmanschoice:simple_recipe");
        RecipeAdapter adapter = Registries.RECIPE_ADAPTERS.getValue(new ResourceLocation(s));
        if(adapter == null)
            throw new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
        return adapter.read(recipeId, json);
    }

}
