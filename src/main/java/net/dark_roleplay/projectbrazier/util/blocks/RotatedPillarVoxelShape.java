package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class RotatedPillarVoxelShape {

	private final VoxelShape x, y, z;

	public RotatedPillarVoxelShape(VoxelShape shape){
		this.y = shape;
		this.z = VoxelShapeHelper.rotateShape(shape, Direction.DOWN);
		this.x = VoxelShapeHelper.rotateShape(VoxelShapeHelper.rotateShape(shape, Direction.DOWN), Direction.EAST);
	}

	public VoxelShape get(Direction.Axis axis){
		switch(axis){
			case X:
				return this.x;
			case Z:
				return this.z;
			case Y:
				return this.y;
			default:
				return VoxelShapes.block();
		}
	}
}
