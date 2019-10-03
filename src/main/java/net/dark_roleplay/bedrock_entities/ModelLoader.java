package net.dark_roleplay.bedrock_entities;

import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ModelLoader {

    public static Set<Skeleton> load(File modelFile) {
        Set<Skeleton> skeletons = new HashSet<Skeleton>();
        try {
            JsonReader reader = new JsonReader(new BufferedReader(new FileReader(modelFile)));

            reader.beginObject();

            while (reader.hasNext()) {
                switch(reader.nextName()){
                    case "format_version":
                        if(!"1.12.0".equals(reader.nextString())){
                            reader.close();
                            System.out.println("Invalid version");
                            return null;
                        }
                        break;
                    case "minecraft:geometry":
                        JsonUtil.forEachInArray(reader, reader2 -> {
                            reader2.beginObject();
                            skeletons.add(new Skeleton(reader2));
                            reader2.endObject();
                        });
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return skeletons;
    }
}
