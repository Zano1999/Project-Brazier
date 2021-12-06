package net.dark_roleplay.projectbrazier.experimental_features.selective_item_block;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.dark_roleplay.projectbrazier.util.screens.KeybindRenderer;
import net.dark_roleplay.projectbrazier.util.screens.ScreenTexture;
import net.dark_roleplay.projectbrazier.util.screens.ScreenTextureWrapper;
import net.dark_roleplay.projectbrazier.util.screens.TextureList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class SelectiveBlockItemListeners extends GuiComponent {

	private static Map<SelectiveBlockItem, ScreenTexture[][]> SELECTIVE_TEXTURES = new HashMap<>();

	@SubscribeEvent
	public static void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

		GameProfile profile = Minecraft.getInstance().player.getGameProfile();

		SelectiveBlockItem item = SelectiveBlockItem.getHeldSelectiveBlockItem(Minecraft.getInstance().player);
		if(item == null) return;

		int index = item.getCurrentIndex(profile);
		int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

		PoseStack matrixStack = event.getMatrixStack();

		int xOffset = (width - 30);
		int yOffset = (height-70)/2;

		GlStateManager._enableBlend();
		TextureList.SELECTIVE_BLOCK_ITEMS.render(matrixStack, xOffset, yOffset);
		GlStateManager._disableBlend();

		ScreenTexture[][] selectiveTextures = createTexturesForItem(item);//SELECTIVE_TEXTURES.computeIfAbsent(item, SelectiveBlockItemListeners::createTexturesForItem);

		int prevIndex = index == 0 ? item.getMaxIndex() : index - 1;
		int nextIndex = index == item.getMaxIndex() ? 0 : index + 1;

		selectiveTextures[prevIndex][1].render(matrixStack, xOffset + 11, yOffset + 3);
		selectiveTextures[nextIndex][1].render(matrixStack, xOffset + 11, yOffset + 51);
		selectiveTextures[index][0].render(matrixStack, xOffset + 3, yOffset + 23);

		//TODO Render keybinds

		KeybindRenderer.renderKeybind(BrazierKeybinds.SELECTIVE_BLOCK_ITEM_PREV, matrixStack, Minecraft.getInstance().font,
				xOffset + 7 - (KeybindRenderer.getKeybindWidth(BrazierKeybinds.SELECTIVE_BLOCK_ITEM_PREV, Minecraft.getInstance().font)), yOffset + 6, false);
		KeybindRenderer.renderKeybind(BrazierKeybinds.SELECTIVE_BLOCK_ITEM_NEXT, matrixStack, Minecraft.getInstance().font,
				xOffset + 7 - (KeybindRenderer.getKeybindWidth(BrazierKeybinds.SELECTIVE_BLOCK_ITEM_NEXT, Minecraft.getInstance().font)), yOffset + 51, false);

	}

	@SubscribeEvent
	public static void keyInput(InputEvent.KeyInputEvent event){
		if(Minecraft.getInstance().player == null) return;
		SelectiveBlockItem item = SelectiveBlockItem.getHeldSelectiveBlockItem(Minecraft.getInstance().player);
		if(item == null) return;

		int index = 0 - (BrazierKeybinds.SELECTIVE_BLOCK_ITEM_PREV.consumeClick() ? 1 : 0) + (BrazierKeybinds.SELECTIVE_BLOCK_ITEM_NEXT.consumeClick() ? 1 : 0);

		if(index != 0){
			index = changeIndex(item, index);
			BrazierPackets.CHANNEL.sendToServer(new SelectiveBlockItemPacket(index));
		}
	}

	@SubscribeEvent
	public static void mouseScroll(InputEvent.MouseScrollEvent event){
		if(Minecraft.getInstance().player == null) return;
		if(!Minecraft.getInstance().options.keyShift.isDown()) return;

		SelectiveBlockItem item = SelectiveBlockItem.getHeldSelectiveBlockItem(Minecraft.getInstance().player);
		if(item == null) return;

		int index = (int) Math.round(event.getScrollDelta());
		if(index != 0){
			index = changeIndex(item, index);
			BrazierPackets.CHANNEL.sendToServer(new SelectiveBlockItemPacket(index));
			event.setCanceled(true);
		}
	}

	private static int changeIndex(SelectiveBlockItem item, int val){
		int index = item.getCurrentIndex(Minecraft.getInstance().player.getGameProfile());

		if(val < 0)
			if(index == 0)
				index = item.getMaxIndex();
			else index--;
		else if(val > 0)
			if(index == item.getMaxIndex())
				index = 0;
			else index++;

			return index;
	}

	private static ScreenTexture[][] createTexturesForItem(SelectiveBlockItem item){
		ScreenTextureWrapper itemTextureSheet;
		itemTextureSheet = new ScreenTextureWrapper(new ResourceLocation(ProjectBrazier.MODID, "textures/screen/selective_item_blocks/" + item.getRegistryName().getPath() + ".png"), (item.getMaxIndex()+1) * 24, 40);

		ScreenTexture[][] textures = new ScreenTexture[item.getMaxIndex()+1][];

		for(int i = 0; i <= item.getMaxIndex(); i++){
			textures[i] = new ScreenTexture[]{
					itemTextureSheet.createTexture(i*24, 0, (i * 24) + 24, 24),
					itemTextureSheet.createTexture(i*16, 24, (i * 16) + 16, 40)
			};
		}

		return textures;
	}
}
