package net.dark_roleplay.projectbrazier.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class VectorUtils {

	public static float getDistance(Vector3f a, Vector3f b){
		double d0 = a.getX() - b.getX();
		double d1 = a.getY() - b.getY();
		double d2 = a.getZ() - b.getZ();
		return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
	}

	public static Vector3f subtract(Vector3f a, Vector3d b){
		a.sub(new Vector3f((float)b.x, (float)b.y, (float)b.z));
		return a;
	}

	public static Vector3f lerpVector(Vector3f a, Vector3f b, double pct){
		return new Vector3f(
				(float) MathHelper.lerp(pct, a.getX(), b.getX()),
				(float) MathHelper.lerp(pct, a.getY(), b.getY()),
				(float) MathHelper.lerp(pct, a.getZ(), b.getZ())
		);
	}
}
