package net.dark_roleplay.projectbrazier.features.model_loaders.roof_model_loader.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dark_roleplay.projectbrazier.features.model_loaders.util.AdvancedModelBox;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RoofModelGenerator {

	List<AdvancedModelBox> fullBoxes = new ArrayList<>();
	List<AdvancedModelBox> leftRim = new ArrayList<>();
	List<AdvancedModelBox> rightRim = new ArrayList<>();
	List<AdvancedModelBox> bottomRim = new ArrayList<>();
	List<AdvancedModelBox> leftBottomRim = new ArrayList<>();
	List<AdvancedModelBox> rightBottomRim = new ArrayList<>();

	Vector3f offset;
	int shingleCount;
	float actualHeight;
	float actualDepth;
	float unrotatedLength;
	double singleLength;
	double singleLengthPlank;
	double angle;
	double angle2;
	double sqrtC;

	public RoofModelGenerator(double depth, double height, int shingleCount, Vector3f offset, MatrixStack matrixStack, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, Map<String, RenderMaterial> textures) {
		double sqrC = depth * depth + height * height;
		this.sqrtC = Math.sqrt(sqrC);
		this.singleLengthPlank = sqrtC / shingleCount;
		this.angle2 = Math.atan(height / depth);

		this.offset = offset;
		this.actualDepth = (float) depth;
		this.actualHeight = (float) height;
		this.shingleCount = shingleCount;
		this.unrotatedLength = (float)Math.sqrt(sqrC - (shingleCount * shingleCount));
		this.singleLength = unrotatedLength / shingleCount;
		this.angle = Math.atan(height / depth) - Math.atan(shingleCount / (shingleCount * singleLength));

		TextureAtlasSprite roofTile = spriteGetter.apply(textures.get("roof_shingle"));
		TextureAtlasSprite plank = spriteGetter.apply(textures.get("bottom"));

		//Setup fullBoxes

		matrixStack.rotate(Vector3f.XP.rotation((float) angle));

		Matrix4f matrix = matrixStack.getLast().getMatrix();
		setupBox(fullBoxes, 16, 0, shingleCount, matrix, roofTile, plank);
		setupBox(leftRim, 8, 8, shingleCount, matrix, roofTile, plank);
		setupBox(rightRim, 8, 0, shingleCount, matrix, roofTile, plank);
		setupBox(bottomRim, 16, 0, (int) Math.ceil(shingleCount / 2F), matrix, roofTile, plank);
		setupBox(leftBottomRim, 8, 8, (int) Math.ceil(shingleCount / 2F), matrix, roofTile, plank);
		setupBox(rightBottomRim, 8, 0, (int) Math.ceil(shingleCount / 2F), matrix, roofTile, plank);
	}

	public List<BakedQuad> getFull() {
		return fullBoxes.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getLeftRim() {
		return leftRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getRightRim() {
		return rightRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getBottomRim() {
		return bottomRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getLeftBottomRim() {
		return leftBottomRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getRightBottomRim() {
		return rightBottomRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	private void setupBox(List<AdvancedModelBox> cubes,
								 float width, float offsetX, int height, Matrix4f matrix,
								 TextureAtlasSprite roofTile, TextureAtlasSprite plank) {

		float halfHeight = height/2f - 1;
		float halfLength = (float)(unrotatedLength/2);
		Vector3f offset = new Vector3f(8, this.actualHeight/2, this.actualDepth/2F);
		offset.add(this.offset);

		for (int i = 0; i < height; i++) {
			cubes.add(new RoofTileBox(
					new Vector3f(offsetX - 8, -i + halfHeight, (float) (i * singleLength) - halfLength),
					new Vector3f(width, 1, (float) singleLength),
					offset,
					(float) angle,
					matrix,
					roofTile,
					i
			));
		}
//		cubes.add(new RoofBottomBox(
//				new Vector3d(offsetX, -1, 0),
//				new Vector3d(width, 1, singleLengthPlank * height),
//				angle,
//				matrix,
//				plank,
//				offset
//		));
	}
}
