package net.dark_roleplay.crafter.handler;

import net.dark_roleplay.crafter.api.recipe.IRecipe;
import net.dark_roleplay.crafter.api.recipe.RecipeAdapter;
import net.dark_roleplay.crafter.api.recipe_parts.IRecipePart;
import net.dark_roleplay.crafter.api.recipe_parts.RecipePartAdapter;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registries {

    public static IForgeRegistry<RecipeAdapter<?>> RECIPE_ADAPTERS;
    public static IForgeRegistry<RecipePartAdapter<?>> RECIPE_PART_ADAPTERS;

    @SubscribeEvent
    public static void registryListener(RegistryEvent.NewRegistry event){
        RECIPE_ADAPTERS = new RegistryBuilder()
            .setName(new ResourceLocation(DarkRoleplayMedieval.MODID, "recipe_adapter"))
            .setType(RecipeAdapter.class)
            .disableSaving()
            .disableSync()
            .create();
        RECIPE_PART_ADAPTERS = new RegistryBuilder()
                .setName(new ResourceLocation(DarkRoleplayMedieval.MODID, "recipe_part_adapter"))
                .setType(RecipePartAdapter.class)
                .disableSaving()
                .disableSync()
                .create();
    }


}
