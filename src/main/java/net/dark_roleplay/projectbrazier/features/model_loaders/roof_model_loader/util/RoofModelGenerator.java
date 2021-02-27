package net.dark_roleplay.projectbrazier.features.model_loaders.roof_model_loader.util;

import net.dark_roleplay.projectbrazier.features.model_loaders.util.AdvancedModelBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RoofModelGenerator {

	List<AdvancedModelBox> fullBoxes = new ArrayList<>();
	List<AdvancedModelBox> leftRim = new ArrayList<>();
	List<AdvancedModelBox> rightRim = new ArrayList<>();
	List<AdvancedModelBox> bottomRim = new ArrayList<>();
	List<AdvancedModelBox> leftBottomRim = new ArrayList<>();
	List<AdvancedModelBox> rightBottomRim = new ArrayList<>();

	double singleLength;
	double singleLengthPlank;
	double angle;
	double angle2;
	Vector3d offset;
	double sqrtC;

	public RoofModelGenerator(double a, double b, int steps, Vector3d offset){
		double sqrC = a * a + b * b;
		this.sqrtC = Math.sqrt(sqrC);
		this.singleLengthPlank = sqrtC / steps;
		this.angle2 = Math.atan(b/a);

		this.offset = offset;
		this.singleLength = Math.sqrt(sqrC - (steps * steps)) / steps;
		this.angle = Math.atan(b/a) - Math.atan(steps/(steps * singleLength)) ;

		Function<ResourceLocation, TextureAtlasSprite> textureGetter = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite roofTile = textureGetter.apply(new ResourceLocation("drpmedieval:block/shingle_roofs/oak_shingles"));
		TextureAtlasSprite plank = textureGetter.apply(new ResourceLocation("drpmedieval:block/shingle_roofs/planks_treated_wood"));

		//Setup fullBoxes
		setupBox(fullBoxes, 16, 0, steps, roofTile, plank);
		setupBox(leftRim, 8, 8, steps, roofTile, plank);
		setupBox(rightRim, 8, 0, steps, roofTile, plank);
		setupBox(bottomRim, 16, 0, (int) Math.ceil(steps/2F), roofTile, plank);
		setupBox(leftBottomRim, 8, 8, (int) Math.ceil(steps/2F), roofTile, plank);
		setupBox(rightBottomRim, 8, 0, (int) Math.ceil(steps/2F), roofTile, plank);
	}

	public List<BakedQuad> getFull(){
		return fullBoxes.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getLeftRim(){
		return leftRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getRightRim(){
		return rightRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getBottomRim(){
		return bottomRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getLeftBottomRim(){
		return leftBottomRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}

	public List<BakedQuad> getRightBottomRim(){
		return rightBottomRim.stream().map(box -> box.bake()).flatMap(quads -> Arrays.stream(quads)).collect(Collectors.toList());
	}
	
	private void setupBox(List<AdvancedModelBox> cubes,
								 float width, float offsetX, int height,
								 TextureAtlasSprite roofTile, TextureAtlasSprite plank){

		for(int i = 0; i < height; i++){
			cubes.add(new RoofTileBox(
					new Vector3d(offsetX,  -i -1, i * singleLength),
					new Vector3d(width, 1, singleLength),
					new Vector3d(angle, 0,0),
					roofTile,
					offset,
					i
			));
		}
		cubes.add(new RoofBottomBox(
				new Vector3d(offsetX, -1, 0),
				new Vector3d(width, 1, singleLengthPlank * height),
				new Vector3d(angle2, 0, 0),
				plank,
				offset
		));
	}
}
