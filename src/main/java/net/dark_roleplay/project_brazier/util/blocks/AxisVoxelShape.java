package net.dark_roleplay.project_brazier.util.blocks;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class AxisVoxelShape {

	private final VoxelShape x, z;

	public AxisVoxelShape(VoxelShape shape){
		this.x = shape;
		this.z = VoxelShapeHelper.rotateShape(shape, Direction.EAST);
	}

	public VoxelShape get(Direction.Axis axis){
		switch(axis){
			case X:
				return this.x;
			case Z:
				return this.z;
			default:
				return VoxelShapes.fullCube();
		}
	}
}
