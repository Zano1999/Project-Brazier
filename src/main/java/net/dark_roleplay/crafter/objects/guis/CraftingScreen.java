package net.dark_roleplay.crafter.objects.guis;

import net.dark_roleplay.crafter.api.recipe.IRecipe;
import net.dark_roleplay.crafter.objects.recipes.simple_recipe.SimpleRecipe;
import net.dark_roleplay.crafter.objects.recipe_parts.ItemStackRecipePart;
import net.dark_roleplay.crafter.objects.reload_listeners.RecipeController;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class CraftingScreen extends Screen {

    public static final ResourceLocation TEX = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting/crafting.png");

    private int sizeX = 320, sizeY = 240;
    private int guiLeft, guiTop;

    public CraftingScreen() {
        super(new TranslationTextComponent(DarkRoleplayMedieval.MODID + ".gui.crafting.title"));
    }

    protected void init() {
        this.guiLeft = (this.width - sizeX) / 2;
        this.guiTop = (this.height - sizeY) / 2;

        Map<ResourceLocation, IRecipe> recipeMap = RecipeController.INSTANCE.getRecipes(new ResourceLocation("craftmanschoice:handheld"));
        if(recipeMap == null) return;
        Collection<IRecipe> recipes = recipeMap.values();


        Iterator<IRecipe> recipeIter = recipes.iterator();
        for(int i = 0; i < 5 && recipeIter.hasNext(); i++){
            this.addButton(new RecipeWidget(guiLeft + 8, guiTop + (i * 37) + 47, recipeIter.next()));
        }
    }

    public void render(int mouseX, int mouseY, float delta) {
        Minecraft.getInstance().textureManager.bindTexture(TEX);
        this.blit(this.guiLeft, this.guiTop, 0, 0, sizeX, sizeY, sizeX, sizeY);

        super.render(mouseX, mouseY, delta);
    }
}
