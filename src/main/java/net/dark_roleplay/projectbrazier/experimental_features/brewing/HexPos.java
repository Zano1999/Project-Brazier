package net.dark_roleplay.projectbrazier.experimental_features.brewing;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;

public class HexPos {
	int posX, posZ;


	public HexPos(int posX, int posZ) {
		this(posX, 0, posZ);
	}

	public HexPos(int posX, int posY, int posZ) {
		this.posX = posX;
		this.posZ = posZ;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosZ() {
		return posZ;
	}

	public int getPosY(){
		return -posX-posZ;
	}

	public boolean isInRange(int posX, int posY, int posZ, int distance){
		return Math.max(Math.max(Math.abs(this.posX-posX), Math.abs((-this.posX-this.posZ)-posY)), Math.abs(this.posZ-posZ)) <= distance;
	}

	public static HexPos pixelToHex(int x, int y, float hexRadius){
		double rx = (Math.sqrt(3)/3 * x - 1F/3*y)/hexRadius;
		double rz = (2F/3*y)/hexRadius;
		return cubeRound(new Vector3d(rx, -rx-rz, rz));
	}

	private static HexPos cubeRound(Vector3d cube){
		int rx = (int) Math.round(cube.x);
		int ry = (int) Math.round(cube.y);
		int rz = (int) Math.round(cube.z);

		double diffX = Math.abs(rx - cube.x);
		double diffY = Math.abs(rx - cube.y);
		double diffZ = Math.abs(rx - cube.z);

		if(diffX > diffY && diffX > diffZ)
			rx = -ry-rz;
		else if(diffY > diffZ)
			ry = -rx-rz;
		else rz = -rx-ry;

		return new HexPos(rx, ry, rz);
	}
}
