package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.util.LazyOptional;

public class DecorListener {

	public static void markChunkDirty(Level w, BlockPos pos){
		ClientLevel world = (ClientLevel) w;
		Minecraft.getInstance().levelRenderer.setSectionDirty(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
	}

	public static void bakeChunk(ChunkBakeEvent event){
//		if(true) return;
		PoseStack stack = event.getMatrixstack();
		BlockPos pos = event.getPos();
		BufferBuilder buffer = event.getBuffer(RenderType.cutout());
		BlockRenderDispatcher renderDispatcher = event.getRenderDispatcher();

		ChunkRenderCache chunkCache = event.getChunkCache();
		int chunkX = (pos.getX() >> 4) - chunkCache.centerX;
		int chunkY = (pos.getZ() >> 4) - chunkCache.centerZ;

		LazyOptional<DecorContainer> capability = chunkCache.chunks[chunkX][chunkY].getCapability(DecorRegistrar.DECOR);
		capability.ifPresent(decorCon -> {
			DecorChunk decChunk = decorCon.getDecorChunk(pos.getY() >> 4, false);
			if(decChunk == null) return;

			stack.pushPose();
			for(DecorState decor : decChunk.getDecors()) {
				if (decor == null) return;
				Vec3 decorPos = decor.getPosition();
				stack.translate(decorPos.x(), decorPos.y(), decorPos.z());

				renderDispatcher.getModelRenderer().renderModel(
						event.getChunkCache(),
						Minecraft.getInstance().getModelManager().getModel(decor.getDecor().getModelLocation()),
						Blocks.GLASS.defaultBlockState(),
						pos.offset(decorPos.x(), decorPos.y(), decorPos.z()),
						stack,
						buffer,
						true,
						event.getRandom(),
						0L,
						OverlayTexture.NO_OVERLAY,
						EmptyModelData.INSTANCE
				);

				stack.translate(-decor.getPosition().x(), -decor.getPosition().y(), -decor.getPosition().z());
			}
			stack.popPose();
		});
	}
}
