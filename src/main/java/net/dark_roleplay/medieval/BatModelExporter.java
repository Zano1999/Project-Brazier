package net.dark_roleplay.medieval;

import com.google.gson.stream.JsonWriter;
import net.minecraft.client.renderer.entity.model.BatModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Random;

public class BatModelExporter{

    EntityModel model = new BatModel();

    public BatModelExporter(){

    }

    public void writeModel() throws IOException, IllegalAccessException {
        JsonWriter writer = new JsonWriter(new FileWriter(new File("./BatModel.json")));
        writer.beginObject();

        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true); // You might want to set modifier to public first.
            if(RendererModel.class.equals(field.getType())){
                Object value = field.get(model);
                if (value != null && value instanceof RendererModel) {
                    RendererModel modelPart = (RendererModel) value;
                    writeRecursive(modelPart, new Random(), writer);
                }
            }
        }

        writer.endObject();
        writer.flush();
    }

    public void writeRecursive(RendererModel modelPart, Random rnd, JsonWriter writer) throws IOException {
        writer.name(modelPart.boxName == null ? "noNameFound" + rnd.nextInt() : modelPart.boxName);
        writer.beginObject();
        writer.name("data");

        writer.beginArray()
        .value(modelPart.offsetX).value(modelPart.offsetY).value(modelPart.offsetZ)
        .value(modelPart.rotationPointX).value(modelPart.rotationPointY).value(modelPart.rotationPointZ)
        .value(modelPart.rotateAngleX).value(modelPart.rotateAngleY).value(modelPart.rotateAngleZ)
        .endArray();

        writer.name("cubes");
        writer.beginArray();
        for(ModelBox box : modelPart.cubeList){
            writer.beginArray()
            .value(box.posX1).value(box.posY1).value(box.posZ1)
            .value(box.posX2 - box.posX1).value(box.posY2 - box.posY1).value(box.posZ1 - box.posZ1)
            .endArray();
        }
        writer.endArray();
        if(modelPart.childModels != null) {
            writer.name("children");
            writer.beginArray();
            for (RendererModel child : modelPart.childModels) {
                writer.beginObject();
                writeRecursive(child, rnd, writer);
                writer.endObject();
            }
            writer.endArray();
        }
        writer.endObject();
    }
}
