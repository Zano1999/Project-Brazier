package net.dark_roleplay.projectbrazier.util.json;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VoxelShapeLoader {

	public static VoxelShape getVoxelShape(String name){
		try (JsonReader reader = new JsonReader(new InputStreamReader(VoxelShapeLoader.class.getClassLoader().getResourceAsStream("fixed_data/projectbrazier/voxel_shapes/" + name + ".json")))) {
			JsonToken token = reader.peek();
			if(token == JsonToken.BEGIN_ARRAY){
				return new ShapeData(BooleanOp.OR, reader).compile();
			}else if(token == JsonToken.BEGIN_OBJECT){
				return Shapes.block();
			}
		}catch(Exception e){
			System.out.println(name);
			e.printStackTrace();
		}
		return Shapes.block();
	}

	private static class ShapeData{
		BooleanOp function;
		List<float[]> boxes = new ArrayList<>();
		List<ShapeData> subShapes = new ArrayList<>();

		public ShapeData(BooleanOp function, JsonReader reader) throws IOException {
			this.function = function;
			reader.beginArray();
			while(reader.hasNext()){
				JsonToken token = reader.peek();
				if(token == JsonToken.BEGIN_ARRAY){
					reader.beginArray();
					float[] box = new float[6];
					for(int i = 0; i < 6; i++)
						box[i] = (float) reader.nextDouble()/16F;
					this.boxes.add(box);
					reader.endArray();
				}else if(token == JsonToken.BEGIN_OBJECT){
					reader.beginObject();
					BooleanOp function2 = getFuncByName(reader.nextName());
					subShapes.add(new ShapeData(function2, reader));
					reader.endObject();
				}else{
					reader.skipValue();
				}
			}
			reader.endArray();
		}

		public VoxelShape compile(){
			return Stream.concat(
					this.boxes.stream().map(data -> Shapes.box(data[0], data[1], data[2], data[3], data[4], data[5])),
					subShapes.stream().map(shape -> shape.compile())
			).reduce((a, b) -> Shapes.join(a, b, function)).orElseGet(Shapes::empty);
		}
	}

	private static BooleanOp getFuncByName(String name){
		switch(name){
			case "FALSE": return BooleanOp.FALSE;
			case "NOT_OR": return BooleanOp.NOT_OR;
			case "ONLY_SECOND": return BooleanOp.ONLY_SECOND;
			case "NOT_FIRST": return BooleanOp.NOT_FIRST;
			case "ONLY_FIRST": return BooleanOp.ONLY_FIRST;
			case "NOT_SECOND": return BooleanOp.NOT_SECOND;
			case "NOT_SAME": return BooleanOp.NOT_SAME;
			case "NOT_AND": return BooleanOp.NOT_AND;
			case "AND": return BooleanOp.AND;
			case "SAME": return BooleanOp.SAME;
			case "SECOND": return BooleanOp.SECOND;
			case "CAUSES": return BooleanOp.CAUSES;
			case "FIRST": return BooleanOp.FIRST;
			case "CAUSED_BY": return BooleanOp.CAUSED_BY;
			case "OR": return BooleanOp.OR;
			case "TRUE": return BooleanOp.TRUE;
		}
		return BooleanOp.OR;
	}
}
