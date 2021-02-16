package net.dark_roleplay.projectbrazier.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

@Mixin(targets="net/minecraft/client/renderer/chunk/ChunkRenderDispatcher$ChunkRender$RebuildTask")
public class ChunkRenderHook{

	@Inject(
			method = "compile(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$CompiledChunk;Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;)Ljava/util/Set;",
			at = @At(value="INVOKE", target="Lnet/minecraft/client/renderer/chunk/ChunkRenderCache;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", ordinal = 0)
			, locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private void compile(
			float xIn, float yIn, float zIn,
			ChunkRenderDispatcher.CompiledChunk compiledChunkIn,
			RegionRenderCacheBuilder builderIn,
			CallbackInfoReturnable<Set<TileEntity>> info,
			//All the locals :raised-hands:
			int i, BlockPos blockpos, BlockPos blockpos1,
			VisGraph visgraph, Set set, ChunkRenderCache chunkrendercache,
			MatrixStack matrixstack, Random random, BlockRendererDispatcher blockrendererdispatcher,
			Iterator iter, BlockPos blockpos2

	) {
		if(true)
		return;
		BlockState blockstate = chunkrendercache.getBlockState(blockpos2);
		if (blockstate.getRenderType() == BlockRenderType.INVISIBLE || !blockstate.isSolid() || !blockstate.isNormalCube(chunkrendercache, blockpos2)) return;

		RenderType renderType = RenderType.getCutout();

		BufferBuilder bufferbuilder2 = builderIn.getBuilder(renderType);
		if (compiledChunkIn.layersStarted.add(renderType)) {
			bufferbuilder2.begin(7, DefaultVertexFormats.BLOCK);
		}

		matrixstack.push();
		matrixstack.translate((double)(blockpos2.getX() & 15), (double)(blockpos2.getY() & 15), (double)(blockpos2.getZ() & 15));

		if(blockrendererdispatcher.getBlockModelRenderer().renderModel(
				chunkrendercache,
				blockrendererdispatcher.getModelForState(Blocks.GLASS.getDefaultState()),
				blockstate,
				blockpos2,
				matrixstack,
				bufferbuilder2,
				true,
				random,
				0L,
				OverlayTexture.NO_OVERLAY,
				EmptyModelData.INSTANCE
		)) {
			compiledChunkIn.empty = false;
			compiledChunkIn.layersUsed.add(renderType);
		}

		matrixstack.pop();
	}
}
