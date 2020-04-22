package net.dark_roleplay.medieval.features.model_loaders.connected_models;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public enum ConnectionType {
	DEFAULT,
	POSITIVE,
	NEGATIVE,
	CENTERED;

	public ConnectionType addPositive() {
		if (this == DEFAULT)
			return POSITIVE;
		else if (this == NEGATIVE)
			return CENTERED;
		return this;
	}

	public ConnectionType addNegative() {
		if (this == DEFAULT)
			return NEGATIVE;
		else if (this == POSITIVE)
			return CENTERED;
		return this;
	}

	public static ConnectionType getConnections(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state){
		if (state.has(BlockStateProperties.HORIZONTAL_AXIS)) {
			ConnectionType type = ConnectionType.DEFAULT;

			Direction.Axis axis = state.get(BlockStateProperties.HORIZONTAL_AXIS);
			switch (axis) {
				case X:
					if(world.getBlockState(pos.east()) == state)
						type = type.addPositive();
					if(world.getBlockState(pos.west()) == state)
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
		return ConnectionType.DEFAULT;
	}
}
