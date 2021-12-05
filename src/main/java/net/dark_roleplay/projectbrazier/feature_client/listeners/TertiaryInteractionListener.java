package net.dark_roleplay.projectbrazier.feature_client.listeners;

import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.projectbrazier.feature.mechanics.tertiary_interactions.ITertiaryInteractor;
import net.dark_roleplay.projectbrazier.feature.packets.TertiaryInteractionPacket;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.dark_roleplay.projectbrazier.feature_client.registrars.BrazierKeybinds;
import net.dark_roleplay.projectbrazier.util.RenderUtils;
import net.dark_roleplay.projectbrazier.util.screens.TextureList;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.DrawSelectionEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TertiaryInteractionListener extends GuiComponent {

	private static long pressStart;
	private static boolean wasButtonPressed = false;

	private static boolean hadSuccess = false;
	private static BlockHitResult rayTraceResult;
	private static Pair<Vec2, Boolean> tertiaryScreenPos;

	@SubscribeEvent
	public static void renderWorldLastEvent(RenderLevelLastEvent event){
		if(rayTraceResult == null || rayTraceResult.getType() != HitResult.Type.BLOCK) return;

		BlockState state = Minecraft.getInstance().level.getBlockState(rayTraceResult.getBlockPos());
		if(!(state.getBlock() instanceof ITertiaryInteractor)) return;

		ITertiaryInteractor block = (ITertiaryInteractor) state.getBlock();
		if(!block.hasInteraction(Minecraft.getInstance().level, rayTraceResult.getBlockPos(), state, Minecraft.getInstance().player)) return;

		Vec3 hitBlock = new Vec3(rayTraceResult.getBlockPos().getX() + 0.5, rayTraceResult.getBlockPos().getY() + 0.5, rayTraceResult.getBlockPos().getZ() + 0.5);
		tertiaryScreenPos = RenderUtils.worldToScreenSpace(hitBlock, event.getPartialTick(), true);

		if(tertiaryScreenPos.getSecond())
			hadSuccess = true;
	}

	@SubscribeEvent
	public static void renderBlockOverlay(DrawSelectionEvent event){
		if(event.getTarget().getType() != HitResult.Type.BLOCK) return;
		else rayTraceResult = (BlockHitResult) event.getTarget();
	}

	@SubscribeEvent
	public static void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
		if(hadSuccess){
			Level world = Minecraft.getInstance().level;
			BlockPos pos = rayTraceResult.getBlockPos();
			BlockState state = world.getBlockState(pos);
			if(!(state.getBlock() instanceof ITertiaryInteractor)) return;
			ITertiaryInteractor block = (ITertiaryInteractor) state.getBlock();

			Font fontRenderer = Minecraft.getInstance().font;

			Vec2 screenPos = tertiaryScreenPos.getFirst();
			int posX = (int) screenPos.x;
			int posY = (int) screenPos.y;

			drawCenteredString(event.getMatrixStack(), fontRenderer, block.getInteractionTooltip(world, pos, state, Minecraft.getInstance().player), posX, posY - 9, 0xFFFFFFFF);

			int width = fontRenderer.width(BrazierKeybinds.TER_ACTION.getTranslatedKeyMessage()) + 7;
			if(Minecraft.getInstance().level.getGameTime() % 30 > 15) {
				TextureList.KEYBOARD_BUTTON.renderSegmented(event.getMatrixStack(), posX - (width / 2), posY + 1, width, 13);
				fontRenderer.draw(event.getMatrixStack(), BrazierKeybinds.TER_ACTION.getTranslatedKeyMessage(), posX - (width/2) + 4, posY + 3, 0xFFFFFFFF);
			}else {
				TextureList.KEYBOARD_BUTTON_PRESSED.renderSegmented(event.getMatrixStack(), posX - (width / 2), posY + 1, width, 13);
				fontRenderer.draw(event.getMatrixStack(), BrazierKeybinds.TER_ACTION.getTranslatedKeyMessage(), posX - (width/2) + 4, posY + 4, 0xFFFFFFFF);
			}

			if(BrazierKeybinds.TER_ACTION.isDown()){
				if(!wasButtonPressed){
					pressStart = System.currentTimeMillis();
					wasButtonPressed = true;
				}

				long maxDuration = block.getDurationInMS(world, pos, state);
				long msPassed = System.currentTimeMillis() - pressStart;
				float percentage = Math.min((msPassed * 1F) / maxDuration, 1.0F);

				fill(event.getMatrixStack(), posX - 25, posY, posX + 25, posY + 9, 0xA0000000);
				fill(event.getMatrixStack(), posX - 24, posY+1, (int) (posX + Mth.lerp(percentage, -24, 24)), posY + 8, 0xA0FFFFFF);

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
