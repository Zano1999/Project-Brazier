package net.dark_roleplay.projectbrazier.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dark_roleplay.projectbrazier.experimental_features.colliders.ColliderDebugRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets="net.minecraft.client.renderer.debug.DebugRenderer")
public class DebugRendererHook {

	private static final ColliderDebugRenderer COLLIDER_DEBUG_RENDERER = new ColliderDebugRenderer();

	@Inject(
			method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;DDD)V",
			at = @At("RETURN")
	)
	public void render(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, double p_113460_, double p_113461_, double p_113462_, CallbackInfo callback) {
		COLLIDER_DEBUG_RENDERER.render(poseStack, bufferSource, p_113460_, p_113461_, p_113462_);
	}
}