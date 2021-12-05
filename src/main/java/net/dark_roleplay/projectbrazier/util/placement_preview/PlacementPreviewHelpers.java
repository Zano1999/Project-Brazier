package net.dark_roleplay.projectbrazier.util.placement_preview;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.HashMap;
import java.util.Map;

public class PlacementPreviewHelpers {

	private static IRenderTypeBuffer.Impl renderBuffer = null;

	@SubscribeEvent
	public static void preview(RenderWorldLastEvent event) {

		Minecraft mc = Minecraft.getInstance();
		if (mc.hitResult instanceof BlockHitResult && mc.hitResult.getType() != HitResult.Type.MISS) {
			BlockHitResult rayTrace = (BlockHitResult) mc.hitResult;
			Player player = mc.player;
			ItemStack held = player.getMainHandItem();
			if (held.getItem() instanceof BlockItem) {
				BlockItem theBlockItem = (BlockItem) held.getItem();

				if (renderBuffer == null) {
					renderBuffer = initRenderBuffer(mc.renderBuffers().bufferSource());
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
		BufferBuilder fallback = ObfuscationReflectionHelper.getPrivateValue(IRenderTypeBuffer.Impl.class, original, "builder");
		Map<RenderType, BufferBuilder> layerBuffers = ObfuscationReflectionHelper.getPrivateValue(IRenderTypeBuffer.Impl.class, original, "fixedBuffers");
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
