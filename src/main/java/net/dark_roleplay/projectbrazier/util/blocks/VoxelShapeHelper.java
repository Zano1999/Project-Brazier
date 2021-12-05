package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashSet;
import java.util.Set;

public final class VoxelShapeHelper {

	/**
	 * Rotates a VoxelShape in steps of 90°
	 * @param shape the input VoxelShape is assumed to be facing NORTH
	 * @param direction Direction.EAST would result in 90° Clockwise Rotation around the Y axis
	 * @return a new Instance of the rotated VoxelShape
	 */
	public static VoxelShape rotateShape(VoxelShape shape, Direction direction) {
		if(direction == Direction.NORTH) return shape;
		Set<VoxelShape> rotatedShapes = new HashSet<VoxelShape>();

		shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
			y1 = (y1 * 16) - 8; y2 = (y2 * 16) - 8;
			x1 = (x1 * 16) - 8; x2 = (x2 * 16) - 8;
			z1 = (z1 * 16) - 8; z2 = (z2 * 16) - 8;

			if(direction == Direction.EAST)
				rotatedShapes.add(Block.box(8 - z1, y1 + 8, 8 + x1, 8 - z2, y2 + 8, 8 + x2));
			else if(direction == Direction.SOUTH)
				rotatedShapes.add(Block.box(8 - x1, y1 + 8, 8 - z1, 8 - x2, y2 + 8, 8 - z2));
			else if(direction == Direction.WEST)
				rotatedShapes.add(Block.box(8 + z1, y1 + 8, 8 - x1, 8 + z2, y2 + 8, 8 - x2));
			else if(direction == Direction.UP)
				rotatedShapes.add(Block.box(x1 + 8, 8 - z2, 8 + y1, x2 + 8, 8 - z1, 8 + y2));
			else if(direction == Direction.DOWN)
				rotatedShapes.add(Block.box(x1 + 8, 8 + z1, 8 - y2, x2 + 8, 8 + z2, 8 - y1));

		});

		return rotatedShapes.stream().reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();
	}
}
