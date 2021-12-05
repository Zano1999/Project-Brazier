package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.core.BlockPos;
import net.minecraftforge.eventbus.api.Event;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class ChunkBakeEvent extends Event {

	private final ChunkRenderDispatcher.CompiledChunk compiledChunks;
	private final RegionRenderCacheBuilder bufferProvider;
	private final MatrixStack matrixstack;
	private final Random random;
	private final BlockRendererDispatcher renderDispatcher;
	private final ChunkRenderCache chunkCache;
	private final BlockPos pos;

	public ChunkBakeEvent(ChunkRenderDispatcher.CompiledChunk compiledChunks, RegionRenderCacheBuilder bufferProvider, MatrixStack matrixstack, Random random, BlockRendererDispatcher renderDispatcher, ChunkRenderCache chunkCache, BlockPos pos) {
		this.compiledChunks = compiledChunks;
		this.bufferProvider = bufferProvider;
		this.matrixstack = matrixstack;
		this.random = random;
		this.renderDispatcher = renderDispatcher;
		this.chunkCache = chunkCache;
		this.pos = pos;
	}

	private void enableRenderType(BufferBuilder buffer, RenderType type){
		if(compiledChunks.hasLayer.add(type))
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

		compiledChunks.isCompletelyEmpty = false;
		compiledChunks.hasBlocks.add(type);
	}

	public BufferBuilder getBuffer(RenderType type){
		BufferBuilder buffer = bufferProvider.builder(type);
		enableRenderType(buffer, type);
		return buffer;
	}

	public MatrixStack getMatrixstack() {
		return matrixstack;
	}

	public ChunkRenderCache getChunkCache() {
		return chunkCache;
	}

	public Random getRandom() {
		return random;
	}

	public BlockRendererDispatcher getRenderDispatcher() {
		return renderDispatcher;
	}

	public BlockPos getPos() {
		return pos;
	}
}
