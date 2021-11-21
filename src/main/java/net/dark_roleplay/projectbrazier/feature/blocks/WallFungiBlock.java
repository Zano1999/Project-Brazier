package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.WallHFacedDecoBlock;
import net.dark_roleplay.projectbrazier.mixin_helper.ICustomOffset;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class WallFungiBlock extends WallHFacedDecoBlock implements ICustomOffset {

	public WallFungiBlock(Properties properties) {
		super(properties, "hoof_fungus");
	}


	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d offset = this.getOffset(state, worldIn, pos);
		return super.getShape(state, worldIn, pos, context).move(offset.x(), offset.y(), offset.z());
	}

	@Override
	public Vector3d getOffset(BlockState state, IBlockReader access, BlockPos pos) {
		long i = MathHelper.getSeed(pos.getX(), pos.getY(), pos.getZ());

		if(state.getValue(HORIZONTAL_FACING).getAxis() == Direction.Axis.X){
			return new Vector3d(0.0D, ((i % 6) / 16F), ((i % 3) / 16F));
		}else{
			return new Vector3d(((i % 3) / 16F), ((i % 6) / 16F), 0.0D);
		}
	}
}
