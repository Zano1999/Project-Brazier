package net.dark_roleplay.medieval.objects.blocks.building.roofs.hacks;

import net.dark_roleplay.medieval.util.ModelUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StraightRoofModelGenerator {

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
    Vec3d offset;
    double sqrtC;

    public StraightRoofModelGenerator(double a, double b, int steps, Vec3d offset){
        double sqrC = a * a + b * b;
        this.sqrtC = Math.sqrt(sqrC);
        this.singleLengthPlank = sqrtC / steps;
        this.angle2 = Math.atan(b/a);

        this.offset = offset;
        this.singleLength = Math.sqrt(sqrC - (steps * steps)) / steps;
        this.angle = Math.atan(b/a) - Math.atan(steps/(steps * singleLength)) ;

        TextureAtlasSprite roofTile = ModelUtility.getBlockSprite("drpmedieval:block/shingle_roofs/oak_shingles");
        TextureAtlasSprite plank = ModelUtility.getBlockSprite("drpmedieval:block/shingle_roofs/planks_treated_wood");

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
                    new Vec3d(offsetX,  -i -1, i * singleLength),
                    new Vec3d(width, 1, singleLength),
                    new Vec3d(angle, 0,0),
                    roofTile,
                    offset,
                    i
            ));
        }
        cubes.add(new RoofBottomBox(
                new Vec3d(offsetX, -1, 0),
                new Vec3d(width, 1, singleLengthPlank * height),
                new Vec3d(angle2, 0, 0),
                plank,
                offset
        ));
    }
}
