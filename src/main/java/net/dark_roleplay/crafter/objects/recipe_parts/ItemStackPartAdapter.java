package net.dark_roleplay.crafter.objects.recipe_parts;

import com.google.gson.JsonObject;
import net.dark_roleplay.crafter.api.recipe_parts.RecipePartAdapter;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nullable;

public class ItemStackPartAdapter extends RecipePartAdapter<ItemStackRecipePart> {
    @Override
    public ItemStackRecipePart read(JsonObject json) {
        boolean hasNbt = json.has("nbt");
        return new ItemStackRecipePart(CraftingHelper.getItemStack(json, hasNbt), !hasNbt);
    }

    @Nullable
    @Override
    public ItemStackRecipePart read(PacketBuffer buffer) {
        return null;
    }

    @Override
    public void write(PacketBuffer buffer, ItemStackRecipePart recipe) {

    }
}
