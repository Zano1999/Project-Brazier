package net.dark_roleplay.projectbrazier.features.model_loaders.axis_connected_models;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public enum AxisConnectionType {
	DEFAULT(false, false),
	POSITIVE(true, false),
	NEGATIVE(false, true),
	CENTERED(true, true);

	private final boolean isPositive,  isNegative;

	private AxisConnectionType(boolean isPositive, boolean isNegative){
		this.isPositive = isPositive;
		this.isNegative = isNegative;
	}

	public AxisConnectionType addPositive() {
		if (this == DEFAULT)
			return POSITIVE;
		else if (this == NEGATIVE)
			return CENTERED;
		return this;
	}

	public AxisConnectionType addNegative() {
		if (this == DEFAULT)
			return NEGATIVE;
		else if (this == POSITIVE)
			return CENTERED;
		return this;
	}

	public boolean isPositive(){
		return this.isPositive;
	}

	public boolean isNegative(){
		return this.isNegative;
	}

	public static AxisConnectionType getConnections(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state){
		boolean flag = false;
		if ((flag = state.has(BlockStateProperties.HORIZONTAL_AXIS)) || state.has(BlockStateProperties.AXIS)) {
			Direction.Axis axis = flag ? state.get(BlockStateProperties.HORIZONTAL_AXIS) : state.get(BlockStateProperties.AXIS);
			return getConnections(world, pos, state, axis);
		}
		return AxisConnectionType.DEFAULT;
	}

	public static AxisConnectionType getConnections(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, Direction.Axis axis){
		AxisConnectionType type = AxisConnectionType.DEFAULT;

		switch (axis) {
			case X:
				if(world.getBlockState(pos.east()) == state)
					type = type.addPositive();
				if(world.getBlockState(pos.west()) == state)
					type = type.addNegative();
				break;
			case Y:
				if(world.getBlockState(pos.up()) == state)
					type = type.addPositive();
				if(world.getBlockState(pos.down()) == state)
					type = type.addNegative();
				break;
			case Z:
				if(world.getBlockState(pos.south()) == state)
					type = type.addPositive();
				if(world.getBlockState(pos.north()) == state)
					type = type.addNegative();
				break;
		}

		return type;
	}
}
