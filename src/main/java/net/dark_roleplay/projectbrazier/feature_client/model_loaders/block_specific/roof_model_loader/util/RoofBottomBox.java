package net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader.util;

import net.dark_roleplay.projectbrazier.feature_client.model_loaders.util.AdvancedModelBox;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class RoofBottomBox extends AdvancedModelBox {

	public RoofBottomBox(Vector3f pos, Vector3f size, Vector3f offset, Matrix4f matrix, TextureAtlasSprite sprite){
		super(pos, size, offset, matrix, sprite);
	}

	public BakedQuad[] bake(){
		List<BakedQuad> quads = new ArrayList<>();

		float minU = sprite.getMinU(), maxU = sprite.getMaxU();
		float minV = sprite.getMinV(), maxV = sprite.getMaxV();

		float uS = (sprite.getMaxU() - sprite.getMinU()) / 16;
		float vS = (sprite.getMaxV() - sprite.getMinV()) / 16;

		quads.add(new BakedQuad(generateVertexData(minU, minV, maxU, maxV, vertices[0], vertices[1], vertices[2], vertices[3]), 0, Direction.DOWN, sprite, false));

		quads.add(new BakedQuad(generateVertexData(minU, minV, maxU, minV + vS, vertices[4], vertices[5], vertices[1], vertices[0]), 0, Direction.SOUTH, sprite, false));
		quads.add(new BakedQuad(generateVertexData(minU, maxV, maxU, maxV - vS, vertices[3], vertices[2], vertices[6], vertices[7]), 0, Direction.NORTH, sprite, false));

		//WEST first was a 4
		quads.add(new BakedQuad(generateVertexData(minU, minV, minU + uS, maxV, vertices[4], vertices[0], vertices[3], vertices[7]), 0, Direction.WEST, sprite, false));
		//EAST
		quads.add(new BakedQuad(generateVertexData(minU, minV, minU + uS, maxV, vertices[1], vertices[5], vertices[6], vertices[2]), 0, Direction.EAST, sprite, false));
		return quads.toArray(new BakedQuad[quads.size()]);
	}
}