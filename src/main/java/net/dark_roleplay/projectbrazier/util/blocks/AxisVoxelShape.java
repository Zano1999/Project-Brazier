package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class AxisVoxelShape {

	private final VoxelShape x, y, z;

	public AxisVoxelShape(VoxelShape shape){
		this.x = shape;
		this.z = VoxelShapeHelper.rotateShape(shape, Direction.EAST);
		this.y = VoxelShapeHelper.rotateShape(shape, Direction.UP);
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
				return VoxelShapes.fullCube();
		}
	}
}
