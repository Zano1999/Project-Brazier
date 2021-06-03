package net.dark_roleplay.projectbrazier.experimental_features.decals;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.experimental_features.decals.capability.DecalChunk;
import net.dark_roleplay.projectbrazier.experimental_features.decals.capability.DecalContainer;
import net.dark_roleplay.projectbrazier.experimental_features.decals.decal.Decal;
import net.dark_roleplay.projectbrazier.experimental_features.decorator.ChunkBakeEvent;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.util.LazyOptional;

public class DecalListener {

	public static void markChunkDirty(World w, BlockPos pos){
		ClientWorld world = (ClientWorld) w;
		Minecraft.getInstance().worldRenderer.markForRerender(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
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

		LazyOptional<DecalContainer> capability = chunkCache.chunks[chunkX][chunkY].getCapability(DecalRegistry.DECAL);
		capability.ifPresent(decalCon -> {
			DecalChunk decChunk = decalCon.getDecalChunk(pos.getY() >> 4, false);
			if(decChunk == null) return;

			stack.push();
			decChunk.getDecals().forEach((statePos, state) -> {
				Vector3i localPos = unpackPos(statePos);
				stack.translate(localPos.getX(), localPos.getY(), localPos.getZ());

				renderDispatcher.getBlockModelRenderer().renderModel(
						event.getChunkCache(),
						Minecraft.getInstance().getModelManager().getModel(Decal.getDecal().getModelLocation()),
						Blocks.GLASS.getDefaultState(),
						pos.add(DecalPos.getX(), DecalPos.getY(), DecalPos.getZ()),
						stack,
						buffer,
						true,
						event.getRandom(),
						0L,
						OverlayTexture.NO_OVERLAY,
						EmptyModelData.INSTANCE
				);

				stack.translate(-localPos.getX(), -localPos.getY(), -localPos.getZ());
			});
			stack.pop();
		});
	}

	private static Vector3i unpackPos(int pos){
		return new Vector3i(pos >> 24, pos >> 16, pos >> 8);
	}
}
