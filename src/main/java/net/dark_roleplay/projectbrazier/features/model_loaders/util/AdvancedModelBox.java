package net.dark_roleplay.projectbrazier.features.model_loaders.util;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Vector3d;

public abstract class AdvancedModelBox {
	protected Vector3d pos;
	protected Vector3d size;
	protected Vector3d offsets;
	protected TextureAtlasSprite sprite;

	double[][][] rotMat;

	public AdvancedModelBox(Vector3d pos, Vector3d size, Vector3d rot, TextureAtlasSprite sprite, Vector3d offsets) {
		this.pos = pos;
		this.size = size;
		this.sprite = sprite;
		this.offsets = new Vector3d(offsets.x / 16, offsets.y / 16, offsets.z / 16);

		double cosX = Math.cos(rot.x), cosY = Math.cos(rot.y), cosZ = Math.cos(rot.z);
		double sinX = Math.sin(rot.x), sinY = Math.sin(rot.y), sinZ = Math.sin(rot.z);

		this.rotMat = new double[][][]{
				{
						{1, 0, 0},
						{0, cosX, -sinX},
						{0, sinX, cosX},
				}, {
				{cosY, 0, sinY},
				{0, 1, 0},
				{-sinY, 0, cosY}
		}, {
				{cosZ, -sinZ, 0},
				{sinZ, cosZ, 0},
				{0, 0, 1}
		}
		};
	}

	public abstract BakedQuad[] bake();

	protected int[] generateVertexData(float u1, float v1, float u2, float v2, Vector3d... vertices) {
		int[] data = new int[DefaultVertexFormats.BLOCK.getIntegerSize() * vertices.length];
		int offset = 0;

		float[][] uv = {{u1, v1}, {u2, v1}, {u2, v2}, {u1, v2}};
		int i = 0;
		for (Vector3d vertex : vertices) {
			data[offset++] = Float.floatToIntBits((float) ((float) vertex.getX() * 0.0625F + this.offsets.x));
			data[offset++] = Float.floatToIntBits((float) ((float) vertex.getY() * 0.0625F + this.offsets.y));
			data[offset++] = Float.floatToIntBits((float) ((float) vertex.getZ() * 0.0625F + this.offsets.z));
			data[offset++] = 0xFFFFFFFF;
			data[offset++] = Float.floatToIntBits(uv[i][0]);
			data[offset++] = Float.floatToIntBits(uv[i][1]);
			data[offset++] = 0;
			i += 1;
		}
		return data;
	}

	protected Vector3d rotate(double x, double y, double z) {
		double nX = 0, nY = 0, nZ = 0;
		for (int i = 0; i < 3; i++) {
			nX = (rotMat[i][0][0] * x) + (rotMat[i][0][1] * y) + (rotMat[i][0][2] * z);
			nY = (rotMat[i][1][0] * x) + (rotMat[i][1][1] * y) + (rotMat[i][1][2] * z);
			nZ = (rotMat[i][2][0] * x) + (rotMat[i][2][1] * y) + (rotMat[i][2][2] * z);
			x = nX;
			y = nY;
			z = nZ;
		}

		return new Vector3d(x, y, z);
	}
}
