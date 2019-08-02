package net.dark_roleplay.medieval.objects.enums;

public enum TelescopeZoom {
	NONE(0, -1),
	LOW(1, 50),
	MEDIUM(2, 30),
	HIGH(3, 10);

	public final int ID;
	public final int targetFOV;

	private TelescopeZoom(int id, int targetFOV){
		this.ID = id;
		this.targetFOV = targetFOV;
	}
}
