package net.dark_roleplay.projectbrazier.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

public class ModelUtility {

	public static TextureAtlasSprite getBlockSprite(String loc) {
		return getBlockSprite(new ResourceLocation(loc));
	}

	public static TextureAtlasSprite getBlockSprite(ResourceLocation loc) {
		return Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(loc);
	}

	public static void generateBakedQuad(BakedQuadBuilder builder, TextureAtlasSprite sprite, Vertex[] vertices) {

		ImmutableList<VertexFormatElement> vertexElements = builder.getVertexFormat().getElements();

		for (Vertex vertex : vertices) {
			for (int i = 0; i < vertexElements.size(); i++) {
				VertexFormatElement element = vertexElements.get(i);
				switch (element.getUsage()) {
					case POSITION:
						builder.put(i, (float) vertex.getPos().getX(), (float) vertex.getPos().getY(), (float) vertex.getPos().getZ(), 1.0F);
						break;
					case NORMAL:
						builder.put(i, (float) vertex.getNormal().getX(), (float) vertex.getNormal().getY(), (float) vertex.getNormal().getZ());
						break;
					case COLOR:
						builder.put(i, vertex.getColor() >> 16 & 0xFF, vertex.getColor() >> 8 & 0xFF, vertex.getColor() & 0xFF, vertex.getColor() >> 24 & 0xFF);
						break;
					case UV:
						switch (element.getIndex()) {
							case 0:
								builder.put(i, sprite.getInterpolatedU(vertex.getTexUV().x), sprite.getInterpolatedV(vertex.getTexUV().y));
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
		private Vector3d pos;
		private int color;
		private Vector2f texUV;
		private Vector2f lightUV;
		private Vector3d normal;

		public Vertex(Vector3d pos, int color, Vector2f texUV, Vector2f lightUV, Vector3d normal) {
			this.pos = pos;
			this.color = color;
			this.texUV = texUV;
			this.lightUV = lightUV;
			this.normal = normal;
		}

		public Vector3d getPos() {
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

		public Vector3d getNormal() {
			return normal;
		}
	}
}
