package net.dark_roleplay.projectbrazier.feature_client.model_loaders.axis_connected_models;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

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


	//hasProperty -> has
	public static AxisConnectionType getConnections(@Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state){
		boolean flag = false;
		if ((flag = state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) || state.hasProperty(BlockStateProperties.AXIS)) {
			Direction.Axis axis = flag ? state.getValue(BlockStateProperties.HORIZONTAL_AXIS) : state.getValue(BlockStateProperties.AXIS);
			return getConnections(world, pos, state, axis);
		}else if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
			return getConnections(world, pos, state, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
		}
		return AxisConnectionType.DEFAULT;
	}

	public static AxisConnectionType getConnections(@Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, Direction.Axis axis){
		AxisConnectionType type = AxisConnectionType.DEFAULT;

		switch (axis) {
			case X:
				if(world.getBlockState(pos.east()) == state)
					type = type.addPositive();
				if(world.getBlockState(pos.west()) == state)
					type = type.addNegative();
				break;
			case Y:
				if(world.getBlockState(pos.above()) == state)
					type = type.addPositive();
				if(world.getBlockState(pos.below()) == state)
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

	public static AxisConnectionType getConnections(@Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, Direction facing){
		AxisConnectionType type = AxisConnectionType.DEFAULT;
		Direction.Axis axis = facing.getClockWise().getAxis();

		switch (axis) {
			case X:
				if(world.getBlockState(pos.east()) == state)
					type = type.addPositive();
				if(world.getBlockState(pos.west()) == state)
					type = type.addNegative();
				break;
			case Y:
				if(world.getBlockState(pos.above()) == state)
					type = type.addPositive();
				if(world.getBlockState(pos.below()) == state)
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
