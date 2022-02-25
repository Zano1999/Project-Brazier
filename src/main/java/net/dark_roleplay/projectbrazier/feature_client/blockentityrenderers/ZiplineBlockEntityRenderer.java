package net.dark_roleplay.projectbrazier.feature_client.blockentityrenderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.blockentities.ZiplineBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;

import java.util.Arrays;

public class ZiplineBlockEntityRenderer implements BlockEntityRenderer<ZiplineBlockEntity> {

	public ZiplineBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(ZiplineBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		Vector3f[] vertices = blockEntity.getVertices();
		if(vertices == null) return;

		poseStack.pushPose();
		poseStack.translate(-blockEntity.getBlockPos().getX(), -blockEntity.getBlockPos().getY(), -blockEntity.getBlockPos().getZ());
		drawTube(Minecraft.getInstance().level, bufferSource.getBuffer(RenderType.solid()), poseStack, vertices, vertices.length/4, 1F, 1F, 1F);
		poseStack.popPose();
	}

	@Override
	public boolean shouldRenderOffScreen(ZiplineBlockEntity entity) {
		return true;
	}

	@Override
	public int getViewDistance() {
		return 256;
	}

	private void drawTube(BlockAndTintGetter level, VertexConsumer builder, PoseStack poseStack, Vector3f[] vertices, int length, float r, float g, float b){
		TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(TextureAtlas.LOCATION_BLOCKS).getSprite(new ResourceLocation(ProjectBrazier.MODID, "block/rope/rope"));


		BlockPos.MutableBlockPos lightPos = new BlockPos.MutableBlockPos();
		int[] lightCoords = new int[length];
		for(int i = 0, j = 0; i < length; i++, j += 4){
			lightPos.set(vertices[j].x(), vertices[j].y(), vertices[j].z());
			int blockLight = level.getBrightness(LightLayer.BLOCK, lightPos);
			int skyLight = level.getBrightness(LightLayer.SKY, lightPos);
			lightCoords[i] = LightTexture.pack(blockLight, skyLight);
		}

		float uMin = sprite.getU0(), vMin = sprite.getV0(), uMax = sprite.getU1(), vMax = sprite.getV1();
		float u2 = uMin + ((uMax-uMin)/8F);
		float u3 = uMin + ((uMax-uMin)/16F)*4;
		float u4 = uMin + ((uMax-uMin)/16F)*6;
		float u5 = uMin + ((uMax-uMin)/16F)*8;
		float v2 = vMin + ((vMax-vMin)/8F);

		Matrix4f matrix = poseStack.last().pose();

		//level.getLightEmission(
		//Start cap
//		putVertex(builder, matrix, vertices[1].x(), vertices[1].y(), vertices[1].z(), uMin, vMin, lightCoords[0]);
//		putVertex(builder, matrix, vertices[0].x(), vertices[0].y(), vertices[0].z(), u2, vMin, lightCoords[0]);
//		putVertex(builder, matrix, vertices[3].x(), vertices[3].y(), vertices[3].z(), u2, v2, lightCoords[0]);
//		putVertex(builder, matrix, vertices[2].x(), vertices[2].y(), vertices[2].z(), uMin, v2, lightCoords[0]);
//
//		int end = (length-1) * 4;
//
//		//End cap
//		putVertex(builder, matrix, vertices[end + 0].x(), vertices[end + 0].y(), vertices[end + 0].z(), uMin, vMin, lightCoords[length-1]);
//		putVertex(builder, matrix, vertices[end + 1].x(), vertices[end + 1].y(), vertices[end + 1].z(), uMax, vMin, lightCoords[length-1]);
//		putVertex(builder, matrix, vertices[end + 2].x(), vertices[end + 2].y(), vertices[end + 2].z(), uMax, vMax, lightCoords[length-1]);
//		putVertex(builder, matrix, vertices[end + 3].x(), vertices[end + 3].y(), vertices[end + 3].z(), uMin, vMax, lightCoords[length-1]);

		for(int i = 0, j = 1; i < length-1; i++, j = i+1){
			Vector3f[] verts = Arrays.copyOfRange(vertices, i*4, j*4+4);

			//BOTTOM
			putVertex(builder, matrix, verts[1].x(), verts[1].y(), verts[1].z(), u2, vMin, lightCoords[i]);
			putVertex(builder, matrix, verts[5].x(), verts[5].y(), verts[5].z(), u2, vMax, lightCoords[j]);
			putVertex(builder, matrix, verts[4].x(), verts[4].y(), verts[4].z(), u3, vMax, lightCoords[j]);
			putVertex(builder, matrix, verts[0].x(), verts[0].y(), verts[0].z(), u3, vMin, lightCoords[i]);

			//LEFT
			putVertex(builder, matrix, verts[5].x(), verts[5].y(), verts[5].z(), u3, vMin, lightCoords[i]);
			putVertex(builder, matrix, verts[1].x(), verts[1].y(), verts[1].z(), u3, vMax, lightCoords[i]);
			putVertex(builder, matrix, verts[2].x(), verts[2].y(), verts[2].z(), u4, vMax, lightCoords[i]);
			putVertex(builder, matrix, verts[6].x(), verts[6].y(), verts[6].z(), u4, vMin, lightCoords[j]);

			//TOP
			putVertex(builder, matrix, verts[3].x(), verts[3].y(), verts[3].z(), u4, vMin, lightCoords[i]);
			putVertex(builder, matrix, verts[7].x(), verts[7].y(), verts[7].z(), u4, vMax, lightCoords[j]);
			putVertex(builder, matrix, verts[6].x(), verts[6].y(), verts[6].z(), u5, vMax, lightCoords[j]);
			putVertex(builder, matrix, verts[2].x(), verts[2].y(), verts[2].z(), u5, vMin, lightCoords[i]);

			putVertex(builder, matrix, verts[0].x(), verts[0].y(), verts[0].z(), uMin, vMin, lightCoords[i]);
			putVertex(builder, matrix, verts[4].x(), verts[4].y(), verts[4].z(), uMin, vMax, lightCoords[j]);
			putVertex(builder, matrix, verts[7].x(), verts[7].y(), verts[7].z(), u2, vMax, lightCoords[j]);
			putVertex(builder, matrix, verts[3].x(), verts[3].y(), verts[3].z(), u2, vMin, lightCoords[i]);
		}
	}

	private void putVertex(VertexConsumer builder, Matrix4f matrix, float x, float y, float z, float u, float v, int light){
		//.put("Position", ELEMENT_POSITION).put("Color", ELEMENT_COLOR).put("UV0", ELEMENT_UV0).put("UV2", ELEMENT_UV2).put("Normal", ELEMENT_NORMAL).put("Padding", ELEMENT_PADDING)
		builder
				.vertex(matrix, x, y, z)
				.color(1F, 1F, 1F, 1F)
				.uv(u, v)
				.uv2(light)
				.normal(0F, 0F, 0F)
				.endVertex();
	}
}
