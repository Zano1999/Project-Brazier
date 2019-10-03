package net.dark_roleplay.bedrock_entities;

import com.google.gson.stream.JsonReader;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.math.Vec3d;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bone{

    private String identifier;
    private Bone parent;
    private String parentName;
    private Vec3d pivot;
    private Vec3d rotation;
    private boolean mirror;
    private double inflate;
    private boolean debug;
    private int renderGroupId;
    private Set<Cube> cubes;

    public Bone(JsonReader reader) throws IOException {
        while(reader.hasNext()) {
            switch (reader.nextName()) {
                case "name":
                    this.identifier = reader.nextString();
                    break;
                case "parent":
                    this.parentName = reader.nextString();
                    break;
                case "pivot":
                    this.pivot = JsonUtil.getVec3d(reader);
                    break;
                case "rotation":
                    this.rotation = JsonUtil.getVec3d(reader);
                    break;
                case "mirror":
                    this.mirror = reader.nextBoolean();
                    break;
                case "inflate":
                    this.inflate = reader.nextDouble();
                    break;
                case "debug":
                    this.debug = reader.nextBoolean();
                    break;
                case "render_group_id":
                    this.renderGroupId = reader.nextInt();
                    break;
                case "cubes":
                    this.cubes = new HashSet<>();
                    JsonUtil.forEachInArray(reader, reader2 -> {
                        reader2.beginObject();
                        this.cubes.add(new Cube(reader));
                        reader2.endObject();
                    });
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
    }

    //TODO Implement more stuff on my own.

    protected void compileDisplayList(float scale) {
//        this.displayList = GLAllocation.generateDisplayLists(1);
//        GlStateManager.newList(this.displayList, 4864);
//        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
//
//        for(int i = 0; i < this.cubeList.size(); ++i) {
//            this.cubeList.get(i).render(bufferbuilder, scale);
//        }
//
//        GlStateManager.endList();
       // this.compiled = true;
    }

}