package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.packets.DecorBreakPacket;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.packets.DecorInitSyncPacket;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierPackets;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import org.apache.commons.lang3.tuple.Triple;

public class DecorListener {

	public static void markChunkDirty(World w, BlockPos pos){
		ClientWorld world = (ClientWorld) w;
		Minecraft.getInstance().worldRenderer.markForRerender(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
	}

	public static void clientTick(TickEvent.ClientTickEvent event){
		if(event.phase == TickEvent.Phase.START && Minecraft.getInstance().gameSettings.keyBindAttack.isKeyDown()){
			//Only consume keybind if hit decor
			World world = Minecraft.getInstance().world;
			PlayerEntity player = Minecraft.getInstance().player;

			if(!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof IDecorator) {

				Triple<DecorChunk, DecorState, Vector3d> hitState = RayTraceHelper.rayTraceForDecor(world, player);

				if (hitState != null) {
					BrazierPackets.CHANNEL.sendToServer(
							new DecorBreakPacket(
									world.getDimensionKey().getRegistryName(),
									hitState.getLeft(),
									new BlockPos(0, 0, 0)
							));
					hitState.getLeft().removeDecor(hitState.getMiddle());
					DecorListener.markChunkDirty(world, new BlockPos(hitState.getRight()));
					world.playSound(player, new BlockPos(hitState.getRight()), SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 1, 1);
					while (Minecraft.getInstance().gameSettings.keyBindAttack.isPressed()) {}


				}
			}
		}
	}

	public static void bakeChunk(ChunkBakeEvent event){
//		if(true) return;
		MatrixStack stack = event.getMatrixstack();
		BlockPos pos = event.getPos();
		BufferBuilder buffer = event.getBuffer(RenderType.getCutout());
		BlockRendererDispatcher renderDispatcher = event.getRenderDispatcher();

		ChunkRenderCache chunkCache = event.getChunkCache();
		int chunkX = (pos.getX() >> 4) - chunkCache.chunkStartX;
		int chunkY = (pos.getZ() >> 4) - chunkCache.chunkStartZ;

		LazyOptional<DecorContainer> capability = chunkCache.chunks[chunkX][chunkY].getCapability(DecorRegistrar.DECOR);
		capability.ifPresent(decorCon -> {
			DecorChunk decChunk = decorCon.getDecorChunk(pos.getY() >> 4, false);
			if(decChunk == null) return;

			stack.push();
			for(DecorState decor : decChunk.getDecors()) {
				if (decor == null) return;
				Vector3d decorPos = decor.getChunkRelativePos();
				stack.translate(decorPos.getX(), decorPos.getY(), decorPos.getZ());

				renderDispatcher.getBlockModelRenderer().renderModel(
						event.getChunkCache(),
						Minecraft.getInstance().getModelManager().getModel(decor.getDecor().getModelLocation()),
						Blocks.GLASS.getDefaultState(),
						pos.add(decorPos.getX(), decorPos.getY(), decorPos.getZ()),
						stack,
						buffer,
						true,
						event.getRandom(),
						0L,
						OverlayTexture.NO_OVERLAY,
						EmptyModelData.INSTANCE
				);

				stack.translate(-decor.getChunkRelativePos().getX(), -decor.getChunkRelativePos().getY(), -decor.getChunkRelativePos().getZ());
			}
			stack.pop();
		});
	}
}
