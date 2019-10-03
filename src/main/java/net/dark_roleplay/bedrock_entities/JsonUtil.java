package net.dark_roleplay.bedrock_entities;

import com.google.gson.stream.JsonReader;
import net.minecraft.util.math.Vec3d;

import java.io.IOException;
import java.util.function.Consumer;

public class JsonUtil {

    public static Vec3d getVec3d(JsonReader reader) throws IOException {
        reader.beginArray();
        Vec3d vec = new Vec3d(reader.nextDouble(), reader.nextDouble(), reader.nextDouble());
        reader.endArray();
        return vec;
    }

    public static void forEachInArray(JsonReader reader, ioConsumer<JsonReader> cons) throws IOException {
        reader.beginArray();
        while(reader.hasNext()){
            cons.accept(reader);
        }
        reader.endArray();
    }

    public static interface ioConsumer<T>{

        void accept(T t) throws IOException;
    }
}
