package net.dark_roleplay.projectbrazier.tests;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;

public class GameTestDemo {

	@GameTest
	public static void doTest(GameTestHelper helper){
		System.out.println("Running Test");
		helper.succeedIf(() -> helper.assertBlock(new BlockPos(1, 1, 1), b -> b == Blocks.AIR, "Block was not air"));
	}
}
