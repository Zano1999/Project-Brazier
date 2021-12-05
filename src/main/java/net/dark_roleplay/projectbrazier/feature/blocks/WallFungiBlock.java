package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.WallHFacedDecoBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallFungiBlock extends WallHFacedDecoBlock {

	public WallFungiBlock(Properties properties) {
		super(properties, "hoof_fungus");
	}


	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		//Vec3 offset = this.getOffset(worldIn, pos);
		return super.getShape(state, worldIn, pos, context);//.move(offset.x(), offset.y(), offset.z());
	}


	//TODO Re-add Mixin for this
//	@Override
//	public Vec3 getOffset(BlockGetter access, BlockPos pos) {
//		BlockState state = access.getBlockState(pos);
//		long i = Mth.getSeed(pos.getX(), pos.getY(), pos.getZ());
//
//		if(state.getValue(HORIZONTAL_FACING).getAxis() == Direction.Axis.X){
//			return new Vec3(0.0D, ((i % 6) / 16F), ((i % 3) / 16F));
//		}else{
//			return new Vec3(((i % 3) / 16F), ((i % 6) / 16F), 0.0D);
//		}
//	}
}
