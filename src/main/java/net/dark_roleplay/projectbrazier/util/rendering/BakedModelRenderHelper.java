package net.dark_roleplay.projectbrazier.util.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import java.util.List;
import java.util.Random;

public class BakedModelRenderHelper {


	public static void renderModel(PoseStack poseStack, VertexConsumer vertexConsumer, BakedModel model, float red, float green, float blue, int light, IModelData modelData) {
		Random random = new Random();
		long i = 42L;

		if(modelData == null) modelData = EmptyModelData.INSTANCE;

		for(Direction direction : Direction.values()) {
			random.setSeed(42L);
			renderQuadList(poseStack.last(), vertexConsumer, red, green, blue, model.getQuads(null, direction, random, modelData), light, OverlayTexture.NO_OVERLAY);
		}

		random.setSeed(42L);
		renderQuadList(poseStack.last(), vertexConsumer, red, green, blue, model.getQuads(null, (Direction)null, random, modelData), light, OverlayTexture.NO_OVERLAY);
	}

	private static void renderQuadList(PoseStack.Pose pose, VertexConsumer vertexConsumer, float red, float green, float blue, List<BakedQuad> quads, int p_111065_, int p_111066_) {
		for(BakedQuad bakedquad : quads) {
			float f;
			float f1;
			float f2;
			if (bakedquad.isTinted()) {
				f = Mth.clamp(red, 0.0F, 1.0F);
				f1 = Mth.clamp(green, 0.0F, 1.0F);
				f2 = Mth.clamp(blue, 0.0F, 1.0F);
			} else {
				f = 1.0F;
				f1 = 1.0F;
				f2 = 1.0F;
			}

			vertexConsumer.putBulkData(pose, bakedquad, f, f1, f2, p_111065_, p_111066_);
		}
	}
}
