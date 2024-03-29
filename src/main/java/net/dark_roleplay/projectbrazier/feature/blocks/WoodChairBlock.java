package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.experimental_features.crafting.CraftingHelper;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.sitting.SittingUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.palette.PalettedContainer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.stream.Collectors;

import net.minecraft.block.AbstractBlock.Properties;

public class WoodChairBlock extends HFacedDecoBlock {
	public WoodChairBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Deprecated
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		//TODO Revert Debug Changes
//		if (world.isRemote()) return ActionResultType.SUCCESS;
//
//		int size = 64;
//
//		long startTime = System.currentTimeMillis();
//
//		for (int xChunk = 0; xChunk < size; xChunk++) {
//			for (int zChunk = 0; zChunk < size; zChunk++) {
//				Chunk chunk = world.getChunk(xChunk, zChunk);
//				for (int yChunk = 0; yChunk < 16; yChunk++) {
//					ChunkSection section = chunk.getSections()[yChunk];
//					if(section == null) section = chunk.getSections()[yChunk] = new ChunkSection(yChunk << 4);
//					PalettedContainer<BlockState> palette = section.getData();
//					for (int x = 0; x < 16; x++)
//						for (int y = 0; y < 16; y++)
//							for (int z = 0; z < 16; z++)
//								palette.swap(x, y, z, Blocks.AIR.getDefaultState());
//
//
//					section.recalculateRefCounts();
//				}
//			}
//		}
//
//		for (int xChunk = 0; xChunk < size; xChunk++) {
//			for (int zChunk = 0; zChunk < size; zChunk++) {
//				Chunk chunk = world.getChunk(xChunk, zChunk);
//
//				Heightmap.updateChunkHeightmaps(chunk, chunk.getHeightmaps().stream().map(e -> e.getKey()).collect(Collectors.toSet()));
//
//				for (int yChunk = 0; yChunk < 16; yChunk++){
//					world.getChunkProvider().getLightManager().updateSectionStatus(SectionPos.of(xChunk, yChunk, zChunk), false);
//				}
//
//				((ServerChunkProvider) world.getChunkProvider()).chunkManager
//						.getTrackingPlayers(chunk.getPos(), false)
//						.forEach(s -> s.connection.sendPacket(new SChunkDataPacket(chunk, 65535)));
//
//				chunk.markDirty();
//			}
//		}
//
//		long endTime = System.currentTimeMillis() - startTime;
//		System.out.println(endTime);

//		CraftingHelper.openCraftingScreen(player);
		SittingUtil.sitOnBlockWithRotation(world, pos, player, state.getValue(HORIZONTAL_FACING), state.getValue(HORIZONTAL_FACING), -0.3F, state);

		return ActionResultType.SUCCESS;
	}
}
