package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.capability.DecorContainer;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.util.LazyOptional;

public class DecorListener {

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
			DecorState decor = decorCon.getDecor(pos.getY() + 4096);
			if(decor == null) return;

			stack.push();
			stack.translate(decor.getPosition().getX(), decor.getPosition().getY(), decor.getPosition().getZ());

			renderDispatcher.getBlockModelRenderer().renderModel(
					event.getChunkCache(),
					Minecraft.getInstance().getModelManager().getModel(decor.getDecor().getModelLocation()),
					Blocks.GLASS.getDefaultState(),
					pos,
					stack,
					buffer,
					true,
					event.getRandom(),
					0L,
					OverlayTexture.NO_OVERLAY,
					EmptyModelData.INSTANCE
			);

			stack.pop();
		});
	}
}
