package net.dark_roleplay.medieval.objects.helper;

import java.util.function.BiConsumer;

public class LambdaHelper {

	public static void executeGrid(int width, int size, BiConsumer<Integer, Integer> method) {
		float widthF = width;
		for(int i = 0; i < size; i++) {
			method.accept(i % width, (int) Math.floor(i / widthF));
		}
	}
}
