package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class FacedVoxelShape {

	private final VoxelShape north, east, south, west, up, down;

	public FacedVoxelShape(VoxelShape shape){
		this.north = shape;
		this.east = VoxelShapeHelper.rotateShape(shape, Direction.EAST);
		this.south = VoxelShapeHelper.rotateShape(shape, Direction.SOUTH);
		this.west = VoxelShapeHelper.rotateShape(shape, Direction.WEST);
		this.up = VoxelShapeHelper.rotateShape(shape, Direction.UP);
		this.down = VoxelShapeHelper.rotateShape(shape, Direction.DOWN);
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
			case UP:
				return this.up;
			case DOWN:
				return this.down;
			default:
				return VoxelShapes.fullCube();
		}
	}
}
