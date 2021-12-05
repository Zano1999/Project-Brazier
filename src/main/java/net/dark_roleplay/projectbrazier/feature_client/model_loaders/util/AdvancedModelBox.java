package net.dark_roleplay.projectbrazier.feature_client.model_loaders.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public abstract class AdvancedModelBox {
	protected Vector3f pos;
	protected Vector3f size;
	protected TextureAtlasSprite sprite;

	protected Vector3f[] vertices;

	public AdvancedModelBox(Vector3f pos, Vector3f size, Vector3f offset, Matrix4f matrix, TextureAtlasSprite sprite) {
		this.pos = pos;
		this.size = size;
		this.sprite = sprite;

		this.vertices = new Vector3f[8];

		float x = pos.x(), x2 = pos.x() + size.x();
		float y = pos.y(), y2 = pos.y() + size.y();
		float z = pos.z(), z2 = pos.z() + size.z();

		Vector4f[] tmpVectors = new Vector4f[]{
				new Vector4f(x, y, z, 1f),
				new Vector4f(x2, y, z, 1f),
				new Vector4f(x2, y, z2, 1f),
				new Vector4f(x, y, z2, 1f),
				new Vector4f(x, y2, z, 1f),
				new Vector4f(x2, y2, z, 1f),
				new Vector4f(x2, y2, z2, 1f),
				new Vector4f(x, y2, z2, 1f)
		};

		for (int i = 0; i < 8; i++) {
			Vector4f tmp = tmpVectors[i];
			tmp.transform(matrix);
			this.vertices[i] = new Vector3f(tmp.x() + offset.x(), tmp.y() + offset.y(), tmp.z() + offset.z());
		}
	}

	public abstract BakedQuad[] bake();

	protected int[] generateVertexData(float u1, float v1, float u2, float v2, Vector3f... vertices) {
		int[] data = new int[DefaultVertexFormat.BLOCK.getIntegerSize() * vertices.length];
		int offset = 0;

		Vector3f normalVec = vertices[1].copy();
		Vector3f vecB = vertices[2].copy();
		normalVec.sub(vertices[0]);
		vecB.sub(vertices[0]);
		normalVec.cross(vecB);

		int normal = ((int) normalVec.x()) << 24 | ((int) normalVec.y()) << 16 | ((int) normalVec.z()) << 8;

		float[][] uv = {{u1, v1}, {u2, v1}, {u2, v2}, {u1, v2}};
		int i = 0;
		for (Vector3f vertex : vertices) {
			data[offset++] = Float.floatToIntBits(vertex.x() * 0.0625F);
			data[offset++] = Float.floatToIntBits(vertex.y() * 0.0625F);
			data[offset++] = Float.floatToIntBits(vertex.z() * 0.0625F);
			data[offset++] = 0xFFFFFFFF;
			data[offset++] = Float.floatToIntBits(uv[i][0]);
			data[offset++] = Float.floatToIntBits(uv[i][1]);
			data[offset++] = 0;
			data[offset++] = normal;
			i += 1;
		}
		return data;
	}
}
