package net.dark_roleplay.projectbrazier.util.blocks;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.util.StringRepresentable;

public class BrazierStateProperties {

	public static final EnumProperty<MultiFacing> MULTI_FACING = DirectionProperty.create("facing", MultiFacing.class);
	public static final BooleanProperty HIDDEN_LEVER = BooleanProperty.create("hidden_lever");


	public enum MultiFacing implements StringRepresentable {
		NORTH("north", false, Direction.NORTH),
		NORTH_EAST("north_east", true, Direction.NORTH),
		EAST("east", false, Direction.EAST),
		SOUTH_EAST("south_east", true, Direction.EAST),
		SOUTH("south", false, Direction.SOUTH),
		SOUTH_WEST("south_west", true, Direction.SOUTH),
		WEST("west", false, Direction.WEST),
		NORTH_WEST("north_west", true, Direction.WEST);

		private final String name;
		private final boolean isAngled;
		private final Direction direction;

		MultiFacing(String name, boolean isAngled, Direction direction){
			this.name = name;
			this.isAngled = isAngled;
			this.direction = direction;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		public boolean isAngled(){
			return isAngled;
		}

		public Direction toDirection(){
			return this.direction;
		}

		public static MultiFacing byAngle(float angle){
			return values()[Mth.floor(angle / 45.0D + 0.5D) & 7];
		}

		public MultiFacing rotate(Rotation rot){
			if(rot == Rotation.NONE) return this;
			boolean flag = rot == Rotation.CLOCKWISE_90;
			boolean flag1 = rot == Rotation.CLOCKWISE_180;
			switch(this){
				case NORTH: return flag ? EAST : flag1 ? SOUTH : WEST;
				case EAST: return flag ? SOUTH : flag1 ? WEST : NORTH;
				case SOUTH: return flag ? WEST : flag1 ? NORTH : EAST;
				case WEST: return flag ? NORTH : flag1 ? EAST : SOUTH;
				case NORTH_EAST: return flag ? SOUTH_EAST : flag1 ? SOUTH_WEST : NORTH_WEST;
				case SOUTH_EAST: return flag ? SOUTH_WEST : flag1 ? NORTH_WEST : NORTH_EAST;
				case NORTH_WEST: return flag ? NORTH_EAST : flag1 ? SOUTH_EAST : SOUTH_WEST;
				case SOUTH_WEST: return flag ? NORTH_WEST : flag1 ? NORTH_EAST : SOUTH_EAST;
				default: return this;
			}
		}

		public MultiFacing mirror(Mirror mirror){
			if(mirror == Mirror.NONE) return this;
			boolean flag = mirror == Mirror.FRONT_BACK;
			switch(this){
				case NORTH: return !flag ? SOUTH : this;
				case EAST: return flag ? WEST : this;
				case SOUTH: return !flag ? NORTH : this;
				case WEST: return flag ? EAST : this;
				case NORTH_EAST: return flag ? NORTH_WEST : SOUTH_EAST;
				case NORTH_WEST: return flag ? NORTH_EAST : SOUTH_WEST;
				case SOUTH_EAST: return flag ? SOUTH_WEST : NORTH_EAST;
				case SOUTH_WEST: return flag ? SOUTH_EAST : NORTH_WEST;
				default: return this;
			}
		}
	}
}
