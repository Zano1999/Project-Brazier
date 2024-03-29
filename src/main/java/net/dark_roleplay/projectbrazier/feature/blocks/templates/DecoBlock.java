package net.dark_roleplay.projectbrazier.feature.blocks.templates;

import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class DecoBlock extends Block {

	protected final VoxelShape shape;

	public DecoBlock(Properties properties, String shapeName) {
		super(properties);
		this.shape = VoxelShapeLoader.getVoxelShape(shapeName);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shape;
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
		return false;
	}
}
