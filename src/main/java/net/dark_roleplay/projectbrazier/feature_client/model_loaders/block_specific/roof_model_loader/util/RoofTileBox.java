package net.dark_roleplay.projectbrazier.feature_client.model_loaders.block_specific.roof_model_loader.util;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature_client.model_loaders.util.AdvancedModelBox;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class RoofTileBox extends AdvancedModelBox {

	int part = 0;
	float angle;

	public RoofTileBox(Vector3f pos, Vector3f size, Vector3f offset, Matrix4f matrix, TextureAtlasSprite sprite, int part) {
		super(pos, size, offset, matrix, sprite);
		this.part = part;
	}

	public BakedQuad[] bake() {
		List<BakedQuad> quads = new ArrayList<>();

		float uS = (sprite.getU1() - sprite.getU0()) / 16;
		float vS = (sprite.getV1() - sprite.getV0()) / 16;
		float minU = sprite.getU0(), maxU = sprite.getU1();
		float minV = sprite.getV0(), maxV = sprite.getV1();

		float baseOffset = ((((part+1) / 3) + part) % 3) * 5 * vS;

		quads.add(new BakedQuad(generateVertexData(minU, minV + baseOffset + vS * 4, maxU, minV + baseOffset, vertices[7], vertices[6], vertices[5], vertices[4]), 0, Direction.UP, sprite, false));

		if (part == 0)
			quads.add(new BakedQuad(generateVertexData(minU, minV + baseOffset + vS * 5, maxU, minV + baseOffset + vS * 4, vertices[4], vertices[5], vertices[1], vertices[0]), 0, Direction.SOUTH, sprite, false));
		quads.add(new BakedQuad(generateVertexData(minU, minV + baseOffset + vS * 5, maxU, minV + baseOffset + vS * 4, vertices[3], vertices[2], vertices[6], vertices[7]), 0, Direction.NORTH, sprite, false));

		//WEST first was a 4
		quads.add(new BakedQuad(generateVertexData(minU, minV + baseOffset, minU + uS, minV + baseOffset + (4 * vS), vertices[4], vertices[4], vertices[3], vertices[7]), 0, Direction.WEST, sprite, false));
		//EAST
		quads.add(new BakedQuad(generateVertexData(minU, minV + baseOffset, minU + uS, minV + baseOffset + (4 * vS), vertices[5], vertices[5], vertices[6], vertices[2]), 0, Direction.EAST, sprite, false));
		return quads.toArray(new BakedQuad[quads.size()]);
	}
}