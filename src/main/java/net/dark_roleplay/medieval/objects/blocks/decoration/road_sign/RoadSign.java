package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairTileEntity;
import net.dark_roleplay.medieval.objects.blocks.templates.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class RoadSign extends BaseBlock{

	public RoadSign(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return new RoadSignTileEntity();
	}
	
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te == null || !(te instanceof RoadSignTileEntity)) return Block.makeCuboidShape(6, 0, 6, 10, 16, 10);
		return VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6, 0, 6, 10, 16, 10), ((RoadSignTileEntity)worldIn.getTileEntity(pos)).getSignsShape(), IBooleanFunction.OR);
	}
}
