package net.dark_roleplay.medieval.util.blocks;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class FacedVoxelShape {

	private final VoxelShape north, east, south, west;

	public FacedVoxelShape(VoxelShape shape){
		this.north = shape;
		this.east = VoxelShapeHelper.rotateShape(shape, Direction.EAST);
		this.south = VoxelShapeHelper.rotateShape(shape, Direction.SOUTH);
		this.west = VoxelShapeHelper.rotateShape(shape, Direction.WEST);
	}

	public VoxelShape get(Direction dir){
		switch(dir){
			case NORTH:
				return this.north;
			case EAST:
				return this.east;
			case SOUTH:
				return this.south;
			case WEST:
				return this.west;
			default:
				return VoxelShapes.fullCube();
		}
	}
}
