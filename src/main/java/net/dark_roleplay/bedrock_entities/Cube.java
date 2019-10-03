package net.dark_roleplay.bedrock_entities;

import com.google.gson.stream.JsonReader;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.model.PositionTextureVertex;
import net.minecraft.client.renderer.model.TexturedQuad;
import net.minecraft.util.math.Vec3d;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.io.IOException;

public class Cube {

    private TexturedQuad[] quads;

    private Vec3d origin;
    private Vec3d size;
    private Vec3d rot;
    private Vec3d pivot;
    private double inflate;
    private boolean mirror;

    public Cube(JsonReader reader) throws IOException {
        while(reader.hasNext()) {
            switch (reader.nextName()) {
                case "origin":
                    this.origin = JsonUtil.getVec3d(reader);
                    break;
                case "size":
                    this.size = JsonUtil.getVec3d(reader);
                    break;
                case "rotation":
                    this.rot = JsonUtil.getVec3d(reader);
                    break;
                case "pivot":
                    this.pivot = JsonUtil.getVec3d(reader);
                    break;
                case "inflate":
                    this.inflate = reader.nextDouble();
                    break;
                case "mirror":
                    this.mirror = reader.nextBoolean();
                    break;
                case "uv":
                    //reader.peek();
                    reader.skipValue();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
    }

    public void bake(){
        double[][] rotationMatrix = new double[][]{
                {cos(rot.y) * cos(rot.z),                                               cos(rot.y) * (-sin(rot.z)),                                                 cos(rot.y) * cos(rot.z) * sin(rot.y)                                    },
                {(-sin(rot.x)) * (-sin(rot.y)) * cos(rot.z) + cos(rot.x) * sin(rot.z),  (-sin(rot.x)) * (-sin(rot.y)) * (-sin(rot.z)) + cos(rot.x) * cos(rot.z),    (-sin(rot.x)) * (-sin(rot.y)) * cos(rot.z) + (-sin(rot.x)) * cos(rot.y) },
                {cos(rot.x) * (-sin(rot.y)) * cos(rot.z) + sin(rot.x) * sin(rot.z),     cos(rot.x) * (-sin(rot.y)) * (-sin(rot.z)) + sin(rot.x) + cos(rot.z),       cos(rot.x) * (-sin(rot.y)) * cos(rot.z) + cos(rot.x) * cos(rot.y)       }
        };
        Vec3d dX = rotatePoint(new Vec3d(size.x + (2 * inflate), 0, 0), rotationMatrix);
        Vec3d dY = rotatePoint(new Vec3d(0, size.y + (2 * inflate), 0), rotationMatrix);
        Vec3d dZ = rotatePoint(new Vec3d(0, 0, size.z + (2 * inflate)), rotationMatrix);
        Vec3d pos = rotatePoint(new Vec3d(origin.x - pivot.x - inflate, origin.y - pivot.y - inflate, origin.z - pivot.z - inflate), rotationMatrix);

        if(mirror){
            Vec3d ndX = new Vec3d(-dX.x, -dX.y, -dX.z);
            pos = pos.add(dX);
            dX = ndX;
        }

        PositionTextureVertex[] vertices = new PositionTextureVertex[]{
                new PositionTextureVertex((float) pos.x,            (float) pos.y,          (float) pos.z, 0.0F, 0.0F),
                new PositionTextureVertex((float) pos.add(dX).x,    (float) pos.y,          (float) pos.z, 0.0F, 8.0F),
                new PositionTextureVertex((float) pos.add(dX).x,    (float) pos.add(dY).y,  (float) pos.z, 8.0F, 8.0F),
                new PositionTextureVertex((float) pos.x,            (float) pos.add(dY).y,  (float) pos.z, 8.0F, 0.0F),
                new PositionTextureVertex((float) pos.x,            (float) pos.y,          (float) pos.add(dZ).z, 0.0F, 0.0F),
                new PositionTextureVertex((float) pos.add(dX).x,    (float) pos.y,          (float) pos.add(dZ).z, 0.0F, 8.0F),
                new PositionTextureVertex((float) pos.add(dX).x,    (float) pos.add(dY).y,  (float) pos.add(dZ).z, 8.0F, 8.0F),
                new PositionTextureVertex((float) pos.x,            (float) pos.add(dY).y,  (float) pos.add(dZ).z, 8.0F, 0.0F)
        };

        this.quads[0] = new TexturedQuad(new PositionTextureVertex[]{vertices[5], vertices[1], vertices[2], vertices[6]}, 0, 0, 1, 1, 16, 16);
        this.quads[1] = new TexturedQuad(new PositionTextureVertex[]{vertices[0], vertices[4], vertices[7], vertices[3]}, 0, 0, 1, 1, 16, 16);
        this.quads[2] = new TexturedQuad(new PositionTextureVertex[]{vertices[5], vertices[4], vertices[0], vertices[1]}, 0, 0, 1, 1, 16, 16);
        this.quads[3] = new TexturedQuad(new PositionTextureVertex[]{vertices[2], vertices[3], vertices[7], vertices[6]}, 0, 0, 1, 1, 16, 16);
        this.quads[4] = new TexturedQuad(new PositionTextureVertex[]{vertices[1], vertices[0], vertices[3], vertices[2]}, 0, 0, 1, 1, 16, 16);
        this.quads[5] = new TexturedQuad(new PositionTextureVertex[]{vertices[4], vertices[5], vertices[6], vertices[7]}, 0, 0, 1, 1, 16, 16);
    }

    private Vec3d rotatePoint(Vec3d p, double[][] m){
        double x = m[0][0] * p.x + m[0][1] * p.y + m[0][2] * p.z;
        double y = m[1][0] * p.x + m[1][1] * p.y + m[1][2] * p.z;
        double z = m[2][0] * p.x + m[2][1] * p.y + m[2][2] * p.z;
        return new Vec3d(x, y, z);
    }

    public void render(BufferBuilder renderer, float scale) {
        for(TexturedQuad texturedquad : this.quads) {
            texturedquad.draw(renderer, scale);
        }
    }
}
