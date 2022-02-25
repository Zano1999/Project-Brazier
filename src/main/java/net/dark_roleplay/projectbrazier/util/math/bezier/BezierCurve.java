package net.dark_roleplay.projectbrazier.util.math.bezier;

import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.util.math.BezierUtils;
import net.minecraft.world.phys.Vec2;

public class BezierCurve {

	private BezierCurve origin;
	private final double[] lookupTable;
	private final Vector3f[] controlPoints;
	private final Vector3f[] bezierPoints;
	private final int resolution;

	public BezierCurve(Vector3f[] controlPoints, int resolution){
		this(null, controlPoints, resolution);
	}

	public BezierCurve(BezierCurve origin, Vector3f[] controlPoints, int resolution){
		this.origin = origin;

		this.controlPoints = controlPoints;
		this.resolution = resolution;
		this.lookupTable = BezierUtils.calculateLookupTable(this.controlPoints[0], this.controlPoints[1], this.controlPoints[2], this.resolution);
		this.bezierPoints = new Vector3f[(int) Math.ceil(this.lookupTable[this.lookupTable.length - 1]) + 1];

		for(int i = 0; i < this.bezierPoints.length; i++){
			double progress = 0;
			if(origin != null){
				progress = BezierUtils.getProgressForDistance(origin.lookupTable, Math.min(i, origin.lookupTable[origin.lookupTable.length - 1]));
			}else{
				progress = BezierUtils.getProgressForDistance(this.lookupTable, Math.min(i, this.lookupTable[this.lookupTable.length - 1]));
			}

			this.bezierPoints[i] = BezierUtils.getBezierPos(this.controlPoints[0], this.controlPoints[1], this.controlPoints[2], progress);
		}
	}
	public static BezierCurve createLocal(Vector3f bezierOrigin, Vector3f bezierMid, Vector3f bezierEnd, int resolution){
		float vertOffset = bezierOrigin.y() - bezierMid.y();
		return new BezierCurve(new Vector3f[]{
				new Vector3f(0, vertOffset, 0),
				new Vector3f(bezierMid.x() - bezierOrigin.x(), 0, bezierMid.z() - bezierOrigin.z()),
				new Vector3f(bezierEnd.x() - bezierOrigin.x(), bezierEnd.y() - bezierOrigin.y() + vertOffset, bezierEnd.z() - bezierOrigin.z())
		}, resolution);
	}

	public static BezierCurve createGlobal(Vector3f bezierOrigin, Vector3f bezierMid, Vector3f bezierEnd, int resolution){
		return new BezierCurve(new Vector3f[]{bezierOrigin, bezierMid, bezierEnd}, resolution);
	}

	public Vector3f[] getControlPoints() {
		return controlPoints;
	}

	public Vector3f[] getBezierPoints() {
		return bezierPoints;
	}

	public BezierCurve offset(float vertical, float horizontal){
		Vector3f pStart = getPerpForIndex(0, vertical);
		Vector3f pEnd = getPerpForIndex(2, vertical);

		Vec2 hor = new Vec2(this.controlPoints[2].z() - this.controlPoints[0].z(), -(this.controlPoints[2].x() - this.controlPoints[0].x()));
		hor = hor.normalized().scale(horizontal);

		Vector3f centerOffset = new Vector3f(pStart.x() + pEnd.x(), pStart.y() + pEnd.y(), pStart.z() + pEnd.z());
		centerOffset.mul(0.5F);

		return new BezierCurve(this, new Vector3f[]{
				new Vector3f(this.controlPoints[0].x() + hor.x + pStart.x(), this.controlPoints[0].y() + pStart.y(), this.controlPoints[0].z() + hor.y + pStart.z()),
				new Vector3f(this.controlPoints[1].x() + hor.x + centerOffset.x(), this.controlPoints[1].y() + centerOffset.y(), this.controlPoints[1].z() + hor.y + centerOffset.z()),
				new Vector3f(this.controlPoints[2].x() + hor.x + pEnd.x(), this.controlPoints[2].y() + pEnd.y(), this.controlPoints[2].z() + hor.y + pEnd.z())
		}, this.resolution);
	}

	public double getLength(){
		return this.lookupTable[this.lookupTable.length - 1];
	}

	private Vector3f getPerpForIndex(int index, float vertOffset){
		double x2 = this.controlPoints[1].x() - this.controlPoints[index].x();
		double z2 = this.controlPoints[1].z() - this.controlPoints[index].z();

		double v = Math.abs(z2) + Math.abs(x2);
		float ratio = (float) (x2/ v);
		float rRatio = (float) (z2/ v);

		Vec2 temp = new Vec2(
				(float) Math.sqrt((x2*x2) + (z2*z2)),
				this.controlPoints[1].y() - this.controlPoints[index].y()
		);
		Vec2 perp = new Vec2(-temp.y, temp.x).normalized().scale(vertOffset);
		return new Vector3f(perp.x * ratio, perp.y, perp.x * rRatio);
	}
}
