package net.dark_roleplay.crafter.api.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class RecipeAdapter<T extends IRecipe> extends ForgeRegistryEntry<RecipeAdapter<?>>{

    public abstract T read(ResourceLocation recipeId, JsonObject json);

    @javax.annotation.Nullable
    public abstract T read(ResourceLocation recipeId, PacketBuffer buffer);

    public abstract void write(PacketBuffer buffer, T recipe);
}
