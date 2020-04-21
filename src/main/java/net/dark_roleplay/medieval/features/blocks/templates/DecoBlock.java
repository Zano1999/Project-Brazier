package net.dark_roleplay.medieval.features.blocks.templates;

import net.dark_roleplay.medieval.util.json.VoxelShapeLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

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
}
