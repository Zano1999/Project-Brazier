package net.dark_roleplay.projectbrazier.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

public class ModelUtility {

	public static TextureAtlasSprite getBlockSprite(String loc) {
		return getBlockSprite(new ResourceLocation(loc));
	}

	public static TextureAtlasSprite getBlockSprite(ResourceLocation loc) {
		return Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(loc);
	}

	public static void generateBakedQuad(BakedQuadBuilder builder, TextureAtlasSprite sprite, Vertex[] vertices) {

		ImmutableList<VertexFormatElement> vertexElements = builder.getVertexFormat().getElements();

		for (Vertex vertex : vertices) {
			for (int i = 0; i < vertexElements.size(); i++) {
				VertexFormatElement element = vertexElements.get(i);
				switch (element.getUsage()) {
					case POSITION:
						builder.put(i, (float) vertex.getPos().x(), (float) vertex.getPos().y(), (float) vertex.getPos().z(), 1.0F);
						break;
					case NORMAL:
						builder.put(i, (float) vertex.getNormal().x(), (float) vertex.getNormal().y(), (float) vertex.getNormal().z());
						break;
					case COLOR:
						builder.put(i, vertex.getColor() >> 16 & 0xFF, vertex.getColor() >> 8 & 0xFF, vertex.getColor() & 0xFF, vertex.getColor() >> 24 & 0xFF);
						break;
					case UV:
						switch (element.getIndex()) {
							case 0:
								builder.put(i, sprite.getU(vertex.getTexUV().x), sprite.getV(vertex.getTexUV().y));
								break;
							case 2:
								builder.put(i, vertex.getLightUV().x, vertex.getLightUV().y);
								break;
							default:
								builder.put(i);
								break;
						}
						break;
					default:
						builder.put(i);
						break;
				}
			}
		}
	}

	public static class Vertex {
		private Vec3 pos;
		private int color;
		private Vector2f texUV;
		private Vector2f lightUV;
		private Vec3 normal;

		public Vertex(Vec3 pos, int color, Vector2f texUV, Vector2f lightUV, Vec3 normal) {
			this.pos = pos;
			this.color = color;
			this.texUV = texUV;
			this.lightUV = lightUV;
			this.normal = normal;
		}

		public Vec3 getPos() {
			return pos;
		}

		public int getColor() {
			return color;
		}

		public Vector2f getTexUV() {
			return texUV;
		}

		public Vector2f getLightUV() {
			return lightUV;
		}

		public Vec3 getNormal() {
			return normal;
		}
	}
}
