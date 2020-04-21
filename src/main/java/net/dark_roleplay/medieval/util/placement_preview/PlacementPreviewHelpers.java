package net.dark_roleplay.medieval.util.placement_preview;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.HashMap;
import java.util.Map;

public class PlacementPreviewHelpers {

	private static IRenderTypeBuffer.Impl renderBuffer = null;

	@SubscribeEvent
	public static void preview(RenderWorldLastEvent event) {

		Minecraft mc = Minecraft.getInstance();
		if (mc.objectMouseOver instanceof BlockRayTraceResult && mc.objectMouseOver.getType() != RayTraceResult.Type.MISS) {
			BlockRayTraceResult rayTrace = (BlockRayTraceResult) mc.objectMouseOver;
			PlayerEntity player = mc.player;
			ItemStack held = player.getHeldItemMainhand();
			if (held.getItem() instanceof BlockItem) {
				BlockItem theBlockItem = (BlockItem) held.getItem();

				if (renderBuffer == null) {
					renderBuffer = initRenderBuffer(mc.getRenderTypeBuffers().getBufferSource());
				}
//				MatrixStack transforms = event.getMatrixStack();
//				Vec3d projVec = mc.getRenderManager().info.getProjectedView();
//				transforms.translate(-projVec.x, -projVec.y, -projVec.z);
//				transforms.push();
//				BlockPos target = context.getPos();
//				transforms.translate(target.getX(), target.getY(), target.getZ());
//				World world = context.getWorld();
//				switch (placeResult.getRenderType()) {
//					case MODEL:
//						mc.getBlockRendererDispatcher().renderModel(placeResult, target, world, transforms, renderBuffer.getBuffer(RenderTypeLookup.getRenderType(placeResult)), EmptyModelData.INSTANCE);
//					case ENTITYBLOCK_ANIMATED:
//						/*
//						 * Yes, we use a fake tile entity to workaround this.
//						 * All exceptions are discared. It is ugly, yes, but
//						 * it partially solve the problem.
//						 */
//						if (placeResult.hasTileEntity()) {
//							TileEntity tile = placeResult.createTileEntity(world);
//							tile.setWorldAndPos(world, target);
//							TileEntityRenderer<? super TileEntity> renderer = TileEntityRendererDispatcher.instance.getRenderer(tile);
//							if (renderer != null) {
//								try {
//									renderer.render(tile, 0F, transforms, renderBuffer, 0xF000F0, OverlayTexture.NO_OVERLAY);
//								} catch (Exception ignored) {
//								}
//							}
//						}
//					default:
//						break;
//				}
//				transforms.pop();
//				renderBuffer.finish();
			}
		}
	}

	private static IRenderTypeBuffer.Impl initRenderBuffer(IRenderTypeBuffer.Impl original) {
		BufferBuilder fallback = ObfuscationReflectionHelper.getPrivateValue(IRenderTypeBuffer.Impl.class, original, "field_228457_a_");
		Map<RenderType, BufferBuilder> layerBuffers = ObfuscationReflectionHelper.getPrivateValue(IRenderTypeBuffer.Impl.class, original, "field_228458_b_");
		Map<RenderType, BufferBuilder> remapped = new HashMap<>();
		for (Map.Entry<RenderType, BufferBuilder> e : layerBuffers.entrySet()) {
			remapped.put(GhostRenderType.remap(e.getKey()), e.getValue());
		}
		return new IRenderTypeBuffer.Impl(fallback, remapped) {
			@Override
			public IVertexBuilder getBuffer(RenderType type) {
				return super.getBuffer(GhostRenderType.remap(type));
			}
		};
	}
}
