package net.dark_roleplay.projectbrazier.util;

public class OptifineCompat {
	private static boolean isOFLoaded;
	private static boolean checkedOF;

	public static boolean isOFLoaded(){
		if(checkedOF) return isOFLoaded;
		try {
			Class.forName("net.optifine.Log");
			isOFLoaded = true;
		} catch(Exception e) {
			isOFLoaded = false;
		}
		checkedOF = true;
		return isOFLoaded;
	}
}
