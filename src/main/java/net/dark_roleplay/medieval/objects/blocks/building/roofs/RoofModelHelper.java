package net.dark_roleplay.medieval.objects.blocks.building.roofs;

import com.google.common.primitives.Ints;
import net.dark_roleplay.medieval.objects.enums.RoofSegment;
import net.dark_roleplay.medieval.util.ModelUtility;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class RoofModelHelper {

    public static RoofModel create(BlockState roofState, BlockState otherState, IBakedModel otherModel) {
        RoofModel model = new RoofModel();

        RoofSegment roofSegment = roofState.get(RoofBlock.SEGMENT);
        Direction roofDir = roofState.get(BlockStateProperties.HORIZONTAL_FACING);

        EnumMap<Direction, List<BakedQuad>> otherQuads = new EnumMap<>(Direction.class);
        for(Direction dir : Direction.values()){
            otherQuads.put(dir, otherModel.getQuads(otherState, dir, new Random()));
        }

        //MainFace
        //TODO SAFETY CHECKS
        if (roofSegment == RoofSegment.STRAIGHT) {
            if (otherQuads.get(roofDir.getOpposite()) != null && otherQuads.get(roofDir.getOpposite()).size() > 0) {
                UVHelper.getHelper(otherQuads.get(roofDir.getOpposite()).get(0));
                model.addQuad(createBakedQuadForFace(0, otherQuads.get(roofDir.getOpposite()).get(0), roofDir.getOpposite(), Type.FULL), roofDir.getOpposite());
            }
            if (otherQuads.get(roofDir.rotateYCCW()) != null && otherQuads.get(roofDir.rotateYCCW()).size() > 0) {
                model.addQuad(createBakedQuadForFace(0, otherQuads.get(roofDir.rotateYCCW()).get(0), roofDir.rotateYCCW(), Type.LEFT), roofDir.rotateYCCW());
            }
            if(otherQuads.get(roofDir.rotateY()) != null && otherQuads.get(roofDir.rotateY()).size() > 0){
                model.addQuad(createBakedQuadForFace(0, otherQuads.get(roofDir.rotateY()).get(0), roofDir.rotateY(), Type.RIGHT), roofDir.rotateY());
            }
            if(otherQuads.get(Direction.DOWN) != null && otherQuads.get(Direction.DOWN).size() > 0) {
                model.addQuad(createBakedQuadForFace(0, otherQuads.get(Direction.DOWN).get(0), Direction.DOWN, Type.FULL), Direction.DOWN);
            }
        }

        return model;
    }

    public enum QuadHelper {
        DOWN(new Vec3d[]{
              new Vec3d(1, 0, 0),
              new Vec3d(1, 0, 1),
              new Vec3d(0, 0, 1),
              new Vec3d(0, 0, 0)
        }, new Vec3d(0, 0, 0), Direction.DOWN),
        WEST(new Vec3d[]{
              new Vec3d(0, 0, 1),
              new Vec3d(0, 1, 1),
              new Vec3d(0, 1, 0),
              new Vec3d(0, 0, 0)
        }, new Vec3d(0, 0.5, 0.5), Direction.WEST),
        EAST(new Vec3d[]{
              new Vec3d(1, 0, 0),
              new Vec3d(1, 1, 0),
              new Vec3d(1, 1, 1),
              new Vec3d(1, 0, 1)
        }, new Vec3d(1, 0.5, 0.5), Direction.EAST),
        NORTH(new Vec3d[]{
              new Vec3d(0, 0, 0),
              new Vec3d(0, 1, 0),
              new Vec3d(1, 1, 0),
              new Vec3d(1, 0, 0)
        }, new Vec3d(0.5, 0.5, 0), Direction.NORTH),
        SOUTH(new Vec3d[]{
              new Vec3d(1, 0, 1),
              new Vec3d(1, 1, 1),
              new Vec3d(0, 1, 1),
              new Vec3d(0, 0, 1)
        }, new Vec3d(0.5, 0.5, 1), Direction.SOUTH);

        public Vec3d[] vectors;
        public final Vec3d altVec;
        public final Direction dir;
        public final int normal;

        QuadHelper(Vec3d[] vectors, Vec3d altVec, Direction dir) {
            this.vectors = vectors;
            this.altVec = altVec;
            this.dir = dir;
            this.normal = calculatePackedNormal(vectors[0], vectors[1], vectors[2], vectors[3]);
        }

        public static QuadHelper getQuadHelp(Direction dir) {
            switch (dir) {
                case DOWN:
                    return DOWN;
                case WEST:
                    return WEST;
                case EAST:
                    return EAST;
                case NORTH:
                    return NORTH;
                case SOUTH:
                    return SOUTH;
            }
            return null;
        }
    }

    private static BakedQuad createBakedQuadForFace(int tintIndex, BakedQuad sourceQuad, Direction face, Type type) {
        TextureAtlasSprite texture = ModelUtility.getBlockSprite((ResourceLocation)null);
        if(sourceQuad != null)
            texture = sourceQuad.func_187508_a();

        UVHelper uv = UVHelper.getHelper(sourceQuad);
        QuadHelper helper = QuadHelper.getQuadHelp(face);

        boolean isRight = type == Type.RIGHT;
        boolean isLeft = type == Type.LEFT;

        ModelUtility.Vertex[] vertices = new ModelUtility.Vertex[]{
              new ModelUtility.Vertex(
                    helper.vectors[0],
                    0xFFFFFFFF,
                    new Vec2f(uv.uv[0], uv.uv[1]),
                    new Vec2f(0, 0),
                    new Vec3d(0, 0, 0)
              ),
              new ModelUtility.Vertex(
                    isRight ? helper.altVec : helper.vectors[1],
                    0xFFFFFFFF,
                    new Vec2f(isRight ? 8 : uv.uv[2], isRight ? 8 : uv.uv[3]),
                    new Vec2f(0, 0),
                    new Vec3d(0, 0, 0)
              ),
              new ModelUtility.Vertex(
                    isLeft ? helper.altVec : helper.vectors[2],
                    0xFFFFFFFF,
                    new Vec2f(uv.uv[4], uv.uv[5]),
                    new Vec2f(0, 0)
                    , new Vec3d(0, 0, 0)
              ),
              new ModelUtility.Vertex(
                    helper.vectors[3],
                    0xFFFFFFFF,
                    new Vec2f(uv.uv[6], uv.uv[7]),
                    new Vec2f(0, 0),
                    new Vec3d(0, 0, 0)
              )
        };


        BakedQuadBuilder quadBuilder = new BakedQuadBuilder();
        ModelUtility.generateBakedQuad(quadBuilder, texture, vertices);
        quadBuilder.setQuadTint(tintIndex);
        quadBuilder.setQuadOrientation(isLeft ? face.rotateYCCW() : isRight ? face.rotateY() : face);
        quadBuilder.setTexture(texture);

        return quadBuilder.build();
    }

    private enum Type{
        LEFT,
        RIGHT,
        FULL
    }

    private enum UVHelper{
        NONE(16, 16, 16, 0, 0, 0, 0, 16),
        LEFT(0, 16, 16, 16, 16, 0, 0, 0),
        RIGHT(16, 0, 0, 0, 0, 16, 16, 16),
        FULL;

        float[] uv;

        UVHelper(float... uv){
            this.uv = uv;
        }

        public static UVHelper getHelper(BakedQuad quad){
            int[] vertexData = quad.getVertexData();
            int offset = DefaultVertexFormats.BLOCK.getOffset(DefaultVertexFormats.TEX_2F.getIndex()) / 4;
            int size = DefaultVertexFormats.BLOCK.getIntegerSize();
            float[] uv = new float[8];
            for(int i = 0; i < 4; i++){
                uv[i * 2] = Float.intBitsToFloat(vertexData[(size * i) + offset]);
                uv[i * 2 + 1] = Float.intBitsToFloat(vertexData[(size * i) + offset + 1]);
            }

            if(uv[0] > uv[2] && uv[3] < uv[5]){
                System.out.println("LEFT");
                return LEFT;
                //Rotated 90° or -90° & Flipped
            }else if(uv[7] < uv[1] && uv[4] > uv[6]){
                //Rotated 180° or flipped
                return RIGHT;
            }

            return NONE;
        }
    }

    private static int calculatePackedNormal(Vec3d a, Vec3d b, Vec3d c, Vec3d d) {
        Vec3d normalized = b.subtract(a).crossProduct(d.subtract(a)).normalize();

        int x = ((byte)(normalized.getX() * 127)) & 0xFF;
        int y = ((byte)(normalized.getY() * 127)) & 0xFF;
        int z = ((byte)(normalized.getZ() * 127)) & 0xFF;
        return x | (y << 0x08) | (z << 0x10);
    }

    public static class RoofModel implements IBakedModel{
        static List<BakedQuad> empty = new ArrayList<>();
        List<BakedQuad> n = new ArrayList<>();
        List<BakedQuad> e = new ArrayList<>();
        List<BakedQuad> s = new ArrayList<>();
        List<BakedQuad> w = new ArrayList<>();
        List<BakedQuad> b = new ArrayList<>();

        public void addQuad(BakedQuad quad, Direction dir){
            switch(dir){
                case NORTH:
                    n.add(quad);
                    break;
                case EAST:
                    e.add(quad);
                    break;
                case SOUTH:
                    s.add(quad);
                    break;
                case WEST:
                    w.add(quad);
                    break;
                case DOWN:
                    b.add(quad);
                    break;
                default:
                    break;
            }
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
            if(side == null) return empty;
            switch(side){
                case NORTH:
                    return n;
                case EAST:
                    return e;
                case SOUTH:
                    return s;
                case WEST:
                    return w;
                case DOWN:
                    return b;
                default:
                    return empty;
            }
        }

        @Override
        public boolean isAmbientOcclusion() {
            return true;
        }

        @Override
        public boolean isGui3d() {
            return false;
        }

        @Override
        public boolean func_230044_c_() {
            return false;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return true;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return null;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return null;
        }
    }

}
