package net.dark_roleplay.projectbrazier.util.json;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

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
				return new ShapeData(IBooleanFunction.OR, reader).compile();
			}else if(token == JsonToken.BEGIN_OBJECT){
				return VoxelShapes.fullCube();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return VoxelShapes.fullCube();
	}

	private static class ShapeData{
		IBooleanFunction function;
		List<float[]> boxes = new ArrayList<>();
		List<ShapeData> subShapes = new ArrayList<>();

		public ShapeData(IBooleanFunction function, JsonReader reader) throws IOException {
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
					IBooleanFunction function2 = getFuncByName(reader.nextName());
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
					this.boxes.stream().map(data -> VoxelShapes.create(data[0], data[1], data[2], data[3], data[4], data[5])),
					subShapes.stream().map(shape -> shape.compile())
			).reduce((a, b) -> VoxelShapes.combineAndSimplify(a, b, function)).orElseGet(VoxelShapes::fullCube);
		}
	}

	private static IBooleanFunction getFuncByName(String name){
		switch(name){
			case "FALSE": return IBooleanFunction.FALSE;
			case "NOT_OR": return IBooleanFunction.NOT_OR;
			case "ONLY_SECOND": return IBooleanFunction.ONLY_SECOND;
			case "NOT_FIRST": return IBooleanFunction.NOT_FIRST;
			case "ONLY_FIRST": return IBooleanFunction.ONLY_FIRST;
			case "NOT_SECOND": return IBooleanFunction.NOT_SECOND;
			case "NOT_SAME": return IBooleanFunction.NOT_SAME;
			case "NOT_AND": return IBooleanFunction.NOT_AND;
			case "AND": return IBooleanFunction.AND;
			case "SAME": return IBooleanFunction.SAME;
			case "SECOND": return IBooleanFunction.SECOND;
			case "CAUSES": return IBooleanFunction.CAUSES;
			case "FIRST": return IBooleanFunction.FIRST;
			case "CAUSED_BY": return IBooleanFunction.CAUSED_BY;
			case "OR": return IBooleanFunction.OR;
			case "TRUE": return IBooleanFunction.TRUE;
		}
		return IBooleanFunction.OR;
	}
}
