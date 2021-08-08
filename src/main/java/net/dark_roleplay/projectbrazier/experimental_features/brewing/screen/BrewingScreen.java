package net.dark_roleplay.projectbrazier.experimental_features.brewing.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.experimental_features.brewing.HexPos;
import net.dark_roleplay.projectbrazier.experimental_features.brewing.map.HexMap;
import net.dark_roleplay.projectbrazier.experimental_features.brewing.map.HexTile;
import net.dark_roleplay.projectbrazier.experimental_features.brewing.map.TileType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class BrewingScreen extends Screen {

	private static final ResourceLocation PAPER_BACKGROUND = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/aged_paper.png");
	private static final ResourceLocation MAP_FEATURES = new ResourceLocation(ProjectBrazier.MODID, "textures/screen/brewing_map_features.png");

	private static final int texWidth = 76, texHeight = 84;
	private static final int hexWidth = 19, hexHeight = 21;

	private HexMap map = new HexMap(50);

	int mapPixelOffsetX = 0, mapPixelOffsetY = 0;

	public BrewingScreen() {
		super(new StringTextComponent("test"));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);

		renderBackgroundTiled(matrixStack);

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableBlend();

		//render Grid & map
		renderFeatures(matrixStack);

		int centerX = this.width/2;
		int centerY = this.height/2;

		//Hovered Hex
		HexPos pos = HexPos.pixelToHex(mouseX-centerX - (hexWidth/2), mouseY-centerY- (hexHeight/2), 10);
		drawHexFeature(matrixStack, centerX, centerY, 0, 21, pos);


		RenderSystem.disableBlend();
	}

	private void renderBackgroundTiled(MatrixStack matrixStack){
		Minecraft.getInstance().getTextureManager().bindTexture(PAPER_BACKGROUND);
		int centerX = this.width/2;
		int centerY = this.height/2;
		blit(matrixStack, 0, 0, centerX - 128, centerY - 128, this.width, this.height, 128, 128);
	}

	private void renderFeatures(MatrixStack matrixStack){
		Minecraft.getInstance().getTextureManager().bindTexture(MAP_FEATURES);
		int centerX = this.width/2;
		int centerY = this.height/2;

		drawHexMap(matrixStack, this.map, centerX, centerY);

		HexPos potion = new HexPos(0, 0);
		//drawHexFeature(matrixStack, centerX, centerY, 0, 42, potion);
		//drawHexFeature(matrixStack, centerX, centerY, 19, 42, potion);
	}

	private void drawHexMap(MatrixStack matrixStack, HexMap map, int centerX, int centerY){
		map.getTiles().stream()
				.filter(tile -> tile.getType() != TileType.NONE)
				.forEach(tile -> drawHexTile(matrixStack, tile, centerX, centerY));
	}

	private void drawHexTile(MatrixStack matrixStack, HexTile tile, int centerX, int centerY){
		HexPos pos = tile.getPosition();
		drawHexFeature(matrixStack, centerX, centerY, tile.getType().getU(), tile.getType().getV(), pos);
	}

	private void drawHexFeature(MatrixStack matrixStack, int centerOffsetX, int centerOffsetY, int u, int v, HexPos pos){
		blit(matrixStack, pos.getPosX() * (hexWidth-1) + centerOffsetX + (pos.getPosZ() *(hexWidth/2)) , pos.getPosZ()*((hexHeight+2)*2/3) + centerOffsetY, u, v, hexWidth, hexHeight, texWidth, texHeight);
	}
}