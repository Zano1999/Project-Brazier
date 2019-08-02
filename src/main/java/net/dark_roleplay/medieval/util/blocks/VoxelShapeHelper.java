package net.dark_roleplay.medieval.util.blocks;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public final class VoxelShapeHelper {

	public static VoxelShape rotateShape(VoxelShape shape, RotationAmount rotation) {
		Set<VoxelShape> rotatedShapes = new HashSet<VoxelShape>();
		
		shape.forEachBox((x1, y1, z1, x2, y2, z2) -> {
			x1 = (x1 * 16) - 8; x2 = (x2 * 16) - 8;
			z1 = (z1 * 16) - 8; z2 = (z2 * 16) - 8;
			
			if(rotation == RotationAmount.NINETY)
				rotatedShapes.add(Block.makeCuboidShape(8 - z1, y1 * 16, 8 + x1, 8 - z2, y2 * 16, 8 + x2));
			else if(rotation == RotationAmount.HUNDRED_EIGHTY)
				rotatedShapes.add(Block.makeCuboidShape(8 - x1, y1 * 16, 8 - z1, 8 - x2, y2 * 16, 8 - z2));
			else if(rotation == RotationAmount.TWO_HUNDRED_SEVENTY)
					rotatedShapes.add(Block.makeCuboidShape(8 + z1, y1 * 16, 8 - x1, 8 + z2, y2 * 16, 8 - x2));
		});
		
		return rotatedShapes.stream().reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	}
	
	public static enum RotationAmount{
		NINETY,
		HUNDRED_EIGHTY,
		TWO_HUNDRED_SEVENTY
	}
}
