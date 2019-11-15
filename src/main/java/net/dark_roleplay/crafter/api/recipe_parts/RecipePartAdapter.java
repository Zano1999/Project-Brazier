package net.dark_roleplay.crafter.api.recipe_parts;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class RecipePartAdapter<T extends IRecipePart> extends ForgeRegistryEntry<RecipePartAdapter<?>> {

    public abstract T read(JsonObject json);

    @javax.annotation.Nullable
    public abstract T read(PacketBuffer buffer);

    public abstract void write(PacketBuffer buffer, T recipe);
}