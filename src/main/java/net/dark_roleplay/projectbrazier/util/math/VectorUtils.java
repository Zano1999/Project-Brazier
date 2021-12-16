package net.dark_roleplay.projectbrazier.util.math;

import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class VectorUtils {
	public static float getDistance(Vector3f a, Vector3f b){
		double d0 = a.x() - b.x();
		double d1 = a.y() - b.y();
		double d2 = a.z() - b.z();
		return Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
	}

	public static Vector3f subtract(Vector3f a, Vec3 b){
		a.sub(new Vector3f((float)b.x, (float)b.y, (float)b.z));
		return a;
	}

	public static Vector3f lerpVector(Vector3f a, Vector3f b, double pct){
		return new Vector3f(
				(float) Mth.lerp(pct, a.x(), b.x()),
				(float) Mth.lerp(pct, a.y(), b.y()),
				(float) Mth.lerp(pct, a.z(), b.z())
		);
	}
}
