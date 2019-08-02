package net.dark_roleplay.medieval.objects.enums;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class TimberedClayEnums {

	public static final Map<TimberedClayType, TimberRecipe[]> recipes = new HashMap<TimberedClayType, TimberRecipe[]>();

	static {
		recipes.put(TimberedClayType.CLEAN, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.TOP, ClickLoc.BOTTOM, TimberedClayType.VERTICAL),
			new TimberRecipe(ClickLoc.LEFT, ClickLoc.RIGHT, TimberedClayType.HORIZONTAL),
			new TimberRecipe(ClickLoc.BOTTOM_LEFT, ClickLoc.TOP_RIGHT, TimberedClayType.DIAGONAL_BT),
			new TimberRecipe(ClickLoc.TOP_LEFT, ClickLoc.BOTTOM_RIGHT, TimberedClayType.DIAGONAL_TB),
			new TimberRecipe(ClickLoc.BOTTOM, ClickLoc.TOP_RIGHT, TimberedClayType.DOUBLE_DIAGONAL_T_BT),
			new TimberRecipe(ClickLoc.BOTTOM_LEFT, ClickLoc.TOP, TimberedClayType.DOUBLE_DIAGONAL_B_BT),
			new TimberRecipe(ClickLoc.BOTTOM, ClickLoc.TOP_LEFT, TimberedClayType.DOUBLE_DIAGONAL_T_TB),
			new TimberRecipe(ClickLoc.TOP, ClickLoc.BOTTOM_RIGHT, TimberedClayType.DOUBLE_DIAGONAL_B_TB),
			new TimberRecipe(ClickLoc.BOTTOM_LEFT, ClickLoc.RIGHT, TimberedClayType.DOUBLE_DIAGONAL_L_LR),
			new TimberRecipe(ClickLoc.LEFT, ClickLoc.TOP_RIGHT, TimberedClayType.DOUBLE_DIAGONAL_R_LR),
			new TimberRecipe(ClickLoc.TOP_LEFT, ClickLoc.RIGHT, TimberedClayType.DOUBLE_DIAGONAL_L_RL),
			new TimberRecipe(ClickLoc.LEFT, ClickLoc.BOTTOM_RIGHT, TimberedClayType.DOUBLE_DIAGONAL_R_RL)
		});
		
		recipes.put(TimberedClayType.DIAGONAL_BT, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.TOP_LEFT, ClickLoc.BOTTOM_RIGHT, TimberedClayType.CROSS)
		});

		recipes.put(TimberedClayType.DIAGONAL_TB, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.BOTTOM_LEFT, ClickLoc.TOP_RIGHT, TimberedClayType.CROSS)
		});

		recipes.put(TimberedClayType.DOUBLE_DIAGONAL_T_BT, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.TOP_LEFT, ClickLoc.BOTTOM, TimberedClayType.ARROW_BOTTOM)
		});

		recipes.put(TimberedClayType.DOUBLE_DIAGONAL_T_TB, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.BOTTOM, ClickLoc.TOP_RIGHT, TimberedClayType.ARROW_BOTTOM)
		});

		recipes.put(TimberedClayType.DOUBLE_DIAGONAL_B_BT, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.TOP, ClickLoc.BOTTOM_RIGHT, TimberedClayType.ARROW_TOP)
		});

		recipes.put(TimberedClayType.DOUBLE_DIAGONAL_B_TB, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.TOP, ClickLoc.BOTTOM_LEFT, TimberedClayType.ARROW_TOP)
		});
		
		recipes.put(TimberedClayType.DOUBLE_DIAGONAL_L_LR, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.TOP_LEFT, ClickLoc.RIGHT, TimberedClayType.ARROW_RIGHT)
		});

		recipes.put(TimberedClayType.DOUBLE_DIAGONAL_L_RL, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.BOTTOM_LEFT, ClickLoc.RIGHT, TimberedClayType.ARROW_RIGHT)
		});

		recipes.put(TimberedClayType.DOUBLE_DIAGONAL_R_LR, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.LEFT, ClickLoc.BOTTOM_RIGHT, TimberedClayType.ARROW_LEFT)
		});

		recipes.put(TimberedClayType.DOUBLE_DIAGONAL_R_RL, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.LEFT, ClickLoc.TOP_RIGHT, TimberedClayType.ARROW_LEFT)
		});

		recipes.put(TimberedClayType.HORIZONTAL, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.TOP, ClickLoc.BOTTOM, TimberedClayType.STRAIGHT_CROSS)
		});

		recipes.put(TimberedClayType.VERTICAL, new TimberRecipe[]{
			new TimberRecipe(ClickLoc.LEFT, ClickLoc.RIGHT, TimberedClayType.STRAIGHT_CROSS)
		});
	}
	
	public static enum TimberedClayType{
		//Diagonals go from top to bottom, left to right for naming.
		//So t_bt means top part, from bottom left to top right,
		//and t_tb means top part from top left to bottom right.
		CLEAN("clean"),
		VERTICAL("vertical"),
		HORIZONTAL("horizontal"),
		DIAGONAL_BT("diagonal_bt"),
		DIAGONAL_TB("diagonal_tb"),
		CROSS("cross"),
		ARROW_BOTTOM("arrow_bottom"),
		ARROW_TOP("arrow_top"),
		ARROW_RIGHT("arrow_right"),
		ARROW_LEFT("arrow_left"),
		STRAIGHT_CROSS("straight_cross"),
		DOUBLE_DIAGONAL_T_BT("double_diagonal_t_bt"),
		DOUBLE_DIAGONAL_B_BT("double_diagonal_b_bt"),
		DOUBLE_DIAGONAL_T_TB("double_diagonal_t_tb"),
		DOUBLE_DIAGONAL_B_TB("double_diagonal_b_tb"),
		DOUBLE_DIAGONAL_L_LR("double_diagonal_l_lr"),
		DOUBLE_DIAGONAL_R_LR("double_diagonal_r_lr"),
		DOUBLE_DIAGONAL_L_RL("double_diagonal_l_rl"),
		DOUBLE_DIAGONAL_R_RL("double_diagonal_r_rl");
		
		private String name = "";
		
		private TimberedClayType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	public static class TimberRecipe {
		private ClickLoc loc1;
		private ClickLoc loc2;
		private TimberedClayType output;

		public TimberRecipe(ClickLoc loc1, ClickLoc loc2, TimberedClayType output) {
			this.loc1 = loc1;
			this.loc2 = loc2;
			this.output = output;
		}

		public boolean equals(ClickLoc loc1, ClickLoc loc2) {
			return (this.loc1 == loc1 && this.loc2 == loc2) || (this.loc1 == loc2 && this.loc2 == loc1);
		}

		public TimberedClayType getOutput() {
			return this.output;
		}

		public ClickLoc getLoc1() {
			return this.loc1;
		}

		public ClickLoc getLoc2() {
			return this.loc2;
		}
	}

	public static class ClickInfo {
		BlockPos pos;
		ClickLoc loc;

		public ClickInfo(BlockPos pos, ClickLoc loc) {
			this.pos = pos;
			this.loc = loc;
		}

		public boolean equals(BlockPos pos){
			return this.pos.equals(pos);
		}

		public ClickLoc getLoc() {
			return this.loc;
		}

		public BlockPos getPos() {
			return this.pos;
		}
	}
	
	public static enum ClickLoc{
		TOP_LEFT,
		TOP,
		TOP_RIGHT,
		RIGHT,
		BOTTOM_RIGHT,
		BOTTOM,
		BOTTOM_LEFT,
		LEFT,
		CENTER;

		public static ClickLoc getLoc(Direction facing, float hitX, float hitY, float hitZ) {
			ClickLoc[] locations = getLocationsForHeight(hitY);
			switch(facing) {
				case EAST:
					return hitZ < 0.25F ? locations[2] : hitZ > 0.75F ? locations[0] : locations[1];
				case NORTH:
					return hitX < 0.25F ? locations[0] : hitX > 0.75F ? locations[2] : locations[1];
				case SOUTH:
					return hitX < 0.25F ? locations[0] : hitX > 0.75F ? locations[2] : locations[1];
				case WEST:
					return hitZ < 0.25F ? locations[2] : hitZ > 0.75F ? locations[0] : locations[1];
				default:
					return null;
			}
		}

		private static ClickLoc[] getLocationsForHeight(float hitY) {
			if(hitY > 0.75F) {
				return new ClickLoc[] {TOP_LEFT, TOP, TOP_RIGHT};
			}else if(hitY < 0.25F) {
				return new ClickLoc[] {BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT};
			}else {
				return new ClickLoc[] {LEFT, CENTER, RIGHT};
			}
		}
	}
}
