package net.dark_roleplay.medieval.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
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
		private Vec3d pos;
		private int color;
		private Vec2f texUV;
		private Vec2f lightUV;
		private Vec3d normal;

		public Vertex(Vec3d pos, int color, Vec2f texUV, Vec2f lightUV, Vec3d normal) {
			this.pos = pos;
			this.color = color;
			this.texUV = texUV;
			this.lightUV = lightUV;
			this.normal = normal;
		}

		public Vec3d getPos() {
			return pos;
		}

		public int getColor() {
			return color;
		}

		public Vec2f getTexUV() {
			return texUV;
		}

		public Vec2f getLightUV() {
			return lightUV;
		}

		public Vec3d getNormal() {
			return normal;
		}
	}
}
