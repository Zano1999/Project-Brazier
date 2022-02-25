package net.dark_roleplay.projectbrazier.util.math;

import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.util.math.VectorUtils;
import net.minecraft.util.Mth;

public class BezierUtils {

	public static double[] calculateLookupTable(Vector3f bezierOrigin, Vector3f bezierMid, Vector3f bezierEnd, int steps){
		double[] LUT = new double[steps + 1];
		double currentDistance = 0;
		Vector3f currentPos = bezierOrigin;
		LUT[0] = 0;
		for(int i = 1; i <= steps; i++){
			Vector3f bezierPos = getBezierPos(bezierOrigin, bezierMid, bezierEnd, ((float)i)/steps);
			currentDistance += VectorUtils.getDistance(currentPos, bezierPos);
			currentPos = bezierPos;
			LUT[i] = currentDistance;
		}
		return LUT;
	}

	public static Vector3f getBezierPos(Vector3f bezierOrigin, Vector3f bezierMid, Vector3f bezierEnd, double progress){
		Vector3f a = VectorUtils.lerpVector(bezierOrigin, bezierMid, progress);
		Vector3f b = VectorUtils.lerpVector(bezierMid, bezierEnd, progress);
		return VectorUtils.lerpVector(a, b, progress);
	}

	public static double getProgressForDistance(double[] lookupTable, double distance){
		double arcLength = lookupTable[lookupTable.length -1];
		int n = lookupTable.length;

		if(distance > 0 && distance < arcLength)
			for(int i = 0; i < n-1; i++)
				if(distance > lookupTable[i] && distance < lookupTable[i+1])
					return Mth.lerp((distance - lookupTable[i]) / (lookupTable[i+1] - lookupTable[i]), i/(n-1F), (i+1)/(n-1F));

		return distance/arcLength; //distance is outside bezier curve
	}
}
