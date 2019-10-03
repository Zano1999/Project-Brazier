package net.dark_roleplay.bedrock_entities;

import com.google.gson.stream.JsonReader;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import java.io.IOException;
import java.util.Set;

public class Skeleton extends Model {

    private ResourceLocation registryName;
    private int textureWidth, textureHeight;
    private double visibleBoundsWidth, visibleBoundsHeight;
    private Vec3d visibleBoundsOffset;

    private Set<Bone> bones;

    public Skeleton(JsonReader reader) throws IOException {
        while(reader.hasNext()){
            switch(reader.nextName()){
                case "description":
                    reader.beginObject();
                    readDescription(reader);
                    reader.endObject();
                    break;
                case "bones":
                    JsonUtil.forEachInArray(reader, reader2 -> {
                        reader2.beginObject();
                        bones.add(new Bone(reader2));
                        reader2.endObject();
                    });
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
    }

    private void readDescription(JsonReader reader) throws IOException {
        while(reader.hasNext()) {
            switch (reader.nextName()) {
                case "identifier":
                    this.registryName = new ResourceLocation(reader.nextString());
                    break;
                case "texture_width":
                    this.textureWidth = reader.nextInt();
                    break;
                case "texture_height":
                    this.textureHeight = reader.nextInt();
                    break;
                case "visible_bounds_width":
                    this.visibleBoundsWidth = reader.nextDouble();
                    break;
                case "visible_bounds_height":
                    this.visibleBoundsHeight = reader.nextDouble();
                    break;
                case "visible_bounds_offset":
                    this.visibleBoundsOffset = JsonUtil.getVec3d(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
    }

}
