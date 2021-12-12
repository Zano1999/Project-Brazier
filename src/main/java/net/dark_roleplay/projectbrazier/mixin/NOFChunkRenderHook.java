package net.dark_roleplay.projectbrazier.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.*;

@Mixin(targets="net/minecraft/client/renderer/chunk/ChunkRenderDispatcher$RenderChunk$RebuildTask")
public class NOFChunkRenderHook {

	@Inject(
			method = "compile(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$CompiledChunk;Lnet/minecraft/client/renderer/ChunkBufferBuilderPack;)Ljava/util/Set;",
			at = @At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;getBlockRenderer()Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;", ordinal = 0),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private void compileChunk(
			float xIn, float yIn, float zIn,
			ChunkRenderDispatcher.CompiledChunk compiledChunkIn,
			ChunkBufferBuilderPack builderIn,
			CallbackInfoReturnable<Set<BlockEntity>> info,
			//All the locals :raised-hands:
			int i, BlockPos blockpos, BlockPos blockpos1,
			VisGraph visgraph, Set set, RenderChunkRegion chunkrendercache,
			PoseStack matrixstack, Random random
	) {
		BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
		//ChunkBakeEvent event = new ChunkBakeEvent(compiledChunkIn, builderIn, matrixstack, random, blockrendererdispatcher, chunkrendercache, blockpos);
		//MinecraftForge.EVENT_BUS.post(event);
	}
}
