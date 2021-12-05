package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

public class HFacedVoxelShape {

	private final VoxelShape north, east, south, west;

	public HFacedVoxelShape(VoxelShape shape){
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
				return Shapes.block();
		}
	}
}
