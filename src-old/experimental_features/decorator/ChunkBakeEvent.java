package net.dark_roleplay.projectbrazier.experimental_features.decorator;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraftforge.eventbus.api.Event;

import java.util.Random;

public class ChunkBakeEvent extends Event {

	private final ChunkRenderDispatcher.CompiledChunk compiledChunks;
	private final ChunkBufferBuilderPack bufferProvider;
	private final PoseStack matrixstack;
	private final Random random;
	private final BlockRenderDispatcher renderDispatcher;
	private final RenderChunkRegion chunkCache;
	private final BlockPos pos;

	public ChunkBakeEvent(ChunkRenderDispatcher.CompiledChunk compiledChunks, ChunkBufferBuilderPack bufferProvider, PoseStack matrixstack, Random random, BlockRenderDispatcher renderDispatcher, RenderChunkRegion chunkCache, BlockPos pos) {
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
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);

		compiledChunks.isCompletelyEmpty = false;
		compiledChunks.hasBlocks.add(type);
	}

	public BufferBuilder getBuffer(RenderType type){
		BufferBuilder buffer = bufferProvider.builder(type);
		enableRenderType(buffer, type);
		return buffer;
	}

	public PoseStack getMatrixstack() {
		return matrixstack;
	}

	public RenderChunkRegion getChunkCache() {
		return chunkCache;
	}

	public Random getRandom() {
		return random;
	}

	public BlockRenderDispatcher getRenderDispatcher() {
		return renderDispatcher;
	}

	public BlockPos getPos() {
		return pos;
	}
}
