package net.dark_roleplay.projectbrazier.experimental_features.crafting.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.screen_lib.NestedWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class OutputDisplayWidget extends NestedWidget {
	private static final ResourceLocation WIDGETS = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/crafting/crafting_widgets.png");

	private ItemStack item;
	private boolean isLarge;
	private int row;

	private int offsetX;
	private int offsetY;

	public OutputDisplayWidget(int x, int y, List<ItemStack> output, int id){
		super(x, y, output.size() <= 3 && id == 0 ? 32 : 16, output.size() <= 3 && id == 0 ? 32 : 16);

		item = output.get(id);
		offsetX = 0;
		if(output.size() == 1){
			offsetY = 17;
			isLarge = true;
		}else if(output.size() <= 3){
			if(id == 0){
				offsetY = 6;
				isLarge = true;
			}else{
				offsetY = 43;
				offsetX = (((id-1) % 2) * 19) - 1;
			}
		}else{
			int rowCount = output.size()/2;
			row = (id/2);
			offsetY = 35 - (rowCount * 10) + row * 18 + (int)Math.ceil(row/2F);
			offsetX = (((id) % 2) * 19) - 1;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		Minecraft.getInstance().getTextureManager().bindTexture(WIDGETS);
		if(isLarge) {
			this.blit(matrixStack, this.posX + offsetX, this.posY + offsetY, 219, 219, 37, 37);

//			GlStateManager.pushMatrix();
//			GlStateManager.translated(this.posX + offsetX + 2, this.posY + offsetY + 2, 0);
//			GlStateManager.scaled(2, 2, 2);
			Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(item, this.posX + offsetX + 10, this.posY + offsetY + 10);
//			GlStateManager.popMatrix();
			Minecraft.getInstance().getItemRenderer().renderItemOverlays(Minecraft.getInstance().fontRenderer, item, this.posX + offsetX + 18, this.posY + offsetY + 18);
		}else {
			this.blit(matrixStack, this.posX + offsetX, this.posY + offsetY, offsetX > 0 ? 236 : 235, row >= 1 ? 199 : 198, offsetX > 0 ? 20 : 21, row >= 1 ? 20 : 21);
			Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(item, this.posX + offsetX + (offsetX > 0 ? 1 : 2), this.posY + offsetY + (row >= 1 ? 1 : 2));
		}
	}
}
