package net.dark_roleplay.projectbrazier.objects.model_loaders.roof_model_loader.util;

import net.dark_roleplay.projectbrazier.objects.model_loaders.util.AdvancedModelBox;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class RoofTileBox extends AdvancedModelBox {

	int part = 0;

	public RoofTileBox(Vector3d pos, Vector3d size, Vector3d rot, TextureAtlasSprite sprite, Vector3d offsets, int part) {
		super(pos, size, rot, sprite, offsets);
		this.part = part;
	}

	public BakedQuad[] bake() {
		List<BakedQuad> quads = new ArrayList<>();

		float x = (float) pos.x, y = (float) pos.y, z = (float) pos.z;
		float w = (float) size.x, h = (float) size.y, l = (float) size.z;

		Vector3d[] vertices = {
				rotate(x, y, z),
				rotate(x + w, y, z),

				rotate(x + w, y, z + l),
				rotate(x, y, z + l),

				rotate(x, y + h, z),
				rotate(x + w, y + h, z),

				rotate(x + w, y + h, z + l),
				rotate(x, y + h, z + l),
		};

		float uS = (sprite.getMaxU() - sprite.getMinU()) / 16;
		float vS = (sprite.getMaxV() - sprite.getMinV()) / 16;
		float minU = sprite.getMinU(), maxU = sprite.getMaxU();
		float minV = sprite.getMinV(), maxV = sprite.getMaxV();

		float baseOffset = ((part % 3) * 5 * vS);

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