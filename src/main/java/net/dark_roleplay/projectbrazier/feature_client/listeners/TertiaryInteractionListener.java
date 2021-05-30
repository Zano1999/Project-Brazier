package net.dark_roleplay.projectbrazier.feature_client.listeners;

import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.dark_roleplay.projectbrazier.feature.packets.TertiaryInteractionPacket;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.dark_roleplay.projectbrazier.util.screens.ScreenTexture;
import net.dark_roleplay.projectbrazier.util.screens.ScreenTextureWrapper;
import net.dark_roleplay.projectbrazier.util.screens.TextureList;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TertiaryInteractionListener extends AbstractGui {

	private static long pressStart;
	private static boolean wasButtonPressed = false;

	private static boolean hadSuccess = false;
	private static BlockRayTraceResult rayTraceResult;
	private static Pair<Vector2f, Boolean> tertiaryScreenPos;

	@SubscribeEvent
	public static void renderWorldLastEvent(RenderWorldLastEvent event){
		if(rayTraceResult == null || rayTraceResult.getType() != RayTraceResult.Type.BLOCK) return;

		BlockState state = Minecraft.getInstance().world.getBlockState(rayTraceResult.getPos());
		if(!(state.getBlock() instanceof ITertiaryInteractor)) return;

		ITertiaryInteractor block = (ITertiaryInteractor) state.getBlock();
		if(!block.hasInteraction(Minecraft.getInstance().world, rayTraceResult.getPos(), state, Minecraft.getInstance().player)) return;

		Vector3d hitBlock = new Vector3d(rayTraceResult.getPos().getX() + 0.5, rayTraceResult.getPos().getY() + 0.5, rayTraceResult.getPos().getZ() + 0.5);
		tertiaryScreenPos = RenderUtils.worldToScreenSpace(hitBlock, event.getPartialTicks(), true);

		if(tertiaryScreenPos.getSecond())
			hadSuccess = true;
	}

	@SubscribeEvent
	public static void renderBlockOverlay(DrawHighlightEvent event){
		if(event.getTarget().getType() != RayTraceResult.Type.BLOCK) return;
		else rayTraceResult = (BlockRayTraceResult) event.getTarget();
	}

	@SubscribeEvent
	public static void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
		if(hadSuccess){
			World world = Minecraft.getInstance().world;
			BlockPos pos = rayTraceResult.getPos();
			BlockState state = world.getBlockState(pos);
			if(!(state.getBlock() instanceof ITertiaryInteractor)) return;
			ITertiaryInteractor block = (ITertiaryInteractor) state.getBlock();

			FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

			Vector2f screenPos = tertiaryScreenPos.getFirst();
			int posX = (int) screenPos.x;
			int posY = (int) screenPos.y;

			drawCenteredString(event.getMatrixStack(), fontRenderer, block.getInteractionTooltip(world, pos, state, Minecraft.getInstance().player), posX, posY - 9, 0xFFFFFFFF);

			int width = fontRenderer.getStringPropertyWidth(BrazierKeybinds.TER_ACTION.func_238171_j_()) + 7;
			if(Minecraft.getInstance().world.getGameTime() % 30 > 15) {
				TextureList.KEYBOARD_BUTTON.renderSegmented(event.getMatrixStack(), posX - (width / 2), posY + 1, width, 13);
				fontRenderer.func_243248_b(event.getMatrixStack(), BrazierKeybinds.TER_ACTION.func_238171_j_(), posX - (width/2) + 4, posY + 3, 0xFFFFFFFF);
			}else {
				TextureList.KEYBOARD_BUTTON_PRESSED.renderSegmented(event.getMatrixStack(), posX - (width / 2), posY + 1, width, 13);
				fontRenderer.func_243248_b(event.getMatrixStack(), BrazierKeybinds.TER_ACTION.func_238171_j_(), posX - (width/2) + 4, posY + 4, 0xFFFFFFFF);
			}

			if(BrazierKeybinds.TER_ACTION.isKeyDown()){
				if(!wasButtonPressed){
					pressStart = System.currentTimeMillis();
					wasButtonPressed = true;
				}

				long maxDuration = block.getDurationInMS(world, pos, state);
				long msPassed = System.currentTimeMillis() - pressStart;
				float percentage = Math.min((msPassed * 1F) / maxDuration, 1.0F);

				fill(event.getMatrixStack(), posX - 25, posY, posX + 25, posY + 9, 0xA0000000);
				fill(event.getMatrixStack(), posX - 24, posY+1, (int) (posX + MathHelper.lerp(percentage, -24, 24)), posY + 8, 0xA0FFFFFF);

				if(msPassed >= maxDuration){
					BrazierPackets.CHANNEL.sendToServer(new TertiaryInteractionPacket(pos));
					wasButtonPressed = false;
				}
			}else{
				wasButtonPressed = false;
			}
		}else{
			wasButtonPressed = false;
		}
		hadSuccess = false;
		rayTraceResult = null;
	}
}
