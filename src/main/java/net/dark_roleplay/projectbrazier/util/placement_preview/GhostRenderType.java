package net.dark_roleplay.projectbrazier.util.placement_preview;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;

import java.util.IdentityHashMap;
import java.util.Map;

public class GhostRenderType extends RenderType {
	private static Map<RenderType, RenderType> remappedTypes = new IdentityHashMap<>();

	GhostRenderType(RenderType original) {
		super(original.toString() + "_place_preview", original.getVertexFormat(), original.getDrawMode(), original.getBufferSize(), original.isUseDelegate(), true, () -> {
			original.setupRenderState();
			RenderSystem.disableDepthTest();
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
			RenderSystem.blendColor(1F, 1F, 1F, 0.5F);
		}, () -> {
			RenderSystem.blendColor(1F, 1F, 1F, 1F);
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableBlend();
			RenderSystem.enableDepthTest();
			original.clearRenderState();
		});
	}

	public static RenderType remap(RenderType type) {
		return type instanceof GhostRenderType ? type : remappedTypes.computeIfAbsent(type, GhostRenderType::new);
	}
}