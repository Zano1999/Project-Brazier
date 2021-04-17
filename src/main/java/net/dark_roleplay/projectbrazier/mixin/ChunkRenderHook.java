package net.dark_roleplay.projectbrazier.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.ChunkBakeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.*;

@Mixin(targets="net/minecraft/client/renderer/chunk/ChunkRenderDispatcher$ChunkRender$RebuildTask")
public class ChunkRenderHook{
//	@Inject(
//			method = "compile(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$CompiledChunk;Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;)Ljava/util/Set;",
//			at = @At(value="INVOKE", target="Lnet/minecraft/client/renderer/chunk/ChunkRenderCache;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", ordinal = 0)
//			, locals = LocalCapture.CAPTURE_FAILEXCEPTION
//	)
//	private void compile(
//			float xIn, float yIn, float zIn,
//			ChunkRenderDispatcher.CompiledChunk compiledChunkIn,
//			RegionRenderCacheBuilder builderIn,
//			CallbackInfoReturnable<Set<TileEntity>> info,
//			//All the locals :raised-hands:
//			int i, BlockPos blockpos, BlockPos blockpos1,
//			VisGraph visgraph, Set set, ChunkRenderCache chunkrendercache,
//			MatrixStack matrixstack, Random random, BlockRendererDispatcher blockrendererdispatcher,
//			Iterator iter, BlockPos blockpos2
//
//	) {
//		BlockState blockstate = chunkrendercache.getBlockState(blockpos2);
//		ChunkBakeEvent event = new ChunkBakeEvent(compiledChunkIn, builderIn, matrixstack, random, blockrendererdispatcher, chunkrendercache);
//
//		//EVENT_CACHE.computeIfAbsent(compiledChunkIn, (key) -> new ChunkBakeEvent(compiledChunkIn, builderIn, matrixstack, random, blockrendererdispatcher, chunkrendercache));
//
//		event.init(blockstate, blockpos2);
//		MinecraftForge.EVENT_BUS.post(event);
//	}

	@Inject(
			method = "compile(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$CompiledChunk;Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;)Ljava/util/Set;",
			at = @At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", ordinal = 0)
			, locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private void compileChunk(
			float xIn, float yIn, float zIn,
			ChunkRenderDispatcher.CompiledChunk compiledChunkIn,
			RegionRenderCacheBuilder builderIn,
			CallbackInfoReturnable<Set<TileEntity>> info,
			//All the locals :raised-hands:
			int i, BlockPos blockpos, BlockPos blockpos1,
			VisGraph visgraph, Set set, ChunkRenderCache chunkrendercache,
			MatrixStack matrixstack, Random random
	) {
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
		ChunkBakeEvent event = new ChunkBakeEvent(compiledChunkIn, builderIn, matrixstack, random, blockrendererdispatcher, chunkrendercache, blockpos);
		MinecraftForge.EVENT_BUS.post(event);
	}
}
