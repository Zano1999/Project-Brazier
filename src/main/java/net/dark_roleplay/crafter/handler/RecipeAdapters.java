package net.dark_roleplay.crafter.handler;

import net.dark_roleplay.crafter.api.recipe.IRecipe;
import net.dark_roleplay.crafter.api.recipe.RecipeAdapter;
import net.dark_roleplay.crafter.api.recipe_parts.IRecipePart;
import net.dark_roleplay.crafter.api.recipe_parts.RecipePartAdapter;
import net.dark_roleplay.crafter.objects.recipe_parts.ItemStackPartAdapter;
import net.dark_roleplay.crafter.objects.recipes.simple_recipe.SimpleRecipeAdapter;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeAdapters {

    @SubscribeEvent
    public static void registerRecipeAdapters(RegistryEvent.Register<RecipeAdapter<?>> event) {
        event.getRegistry().register(new SimpleRecipeAdapter().setRegistryName("craftmanschoice:simple_recipe"));
    }

    @SubscribeEvent
    public static void registerRecipePartAdapters(RegistryEvent.Register<RecipePartAdapter<?>> event) {
        event.getRegistry().register(new ItemStackPartAdapter().setRegistryName("craftmanschoice:item"));
    }
}
