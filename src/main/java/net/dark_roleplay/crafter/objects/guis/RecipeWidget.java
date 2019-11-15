package net.dark_roleplay.crafter.objects.guis;

import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.crafter.api.recipe.IRecipe;
import net.dark_roleplay.crafter.api.recipe_parts.IRecipePart;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler_2.MedievalItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class RecipeWidget extends Widget {

    public static final ResourceLocation TEX = new ResourceLocation(DarkRoleplayMedieval.MODID, "textures/gui/crafting/recipe_icon.png");

    private IRecipe recipe;

    public RecipeWidget(int posX, int posY, IRecipe recipe) {
        super(posX, posY, 125, 37, "");
        this.recipe = recipe;
    }

    public void renderButton(int mouseX, int mouseY, float delta) {
        Minecraft.getInstance().textureManager.bindTexture(TEX);
        this.blit(this.x, this.y, 0,this.isHovered() ? 37 : 0, 125, 37, 256, 256);

        RenderHelper.disableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();


        //Render Primary Output
        recipe.getPrimaryOut().getIcon().renderScaled(this.x + 2, this.y + 2,2, mouseX - this.x, mouseY - this.y, delta);

        //Render Secondary Outputs
        IRecipePart[] secondaryOutputs = recipe.getSecondrayOut();
        for(int i = 0; i <  2 && i < secondaryOutputs.length; i++){
            IRecipePart out = secondaryOutputs[i];
            out.getIcon().render(this.x + 36, this.y + 2 + (i * 17), mouseX, mouseY, delta);
        }

        //Render Ingredients
        IRecipePart[] ingredients = recipe.getInput();
        for(int i = 0; i <  7 && i < ingredients.length; i++){
            IRecipePart in = ingredients[i];
            in.getIcon().render(this.x + 56 + ((i%4) * 17), this.y + 2 + ((i/4) * 17), mouseX, mouseY, delta);
        }

        RenderHelper.enableStandardItemLighting();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
