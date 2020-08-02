package net.dark_roleplay.projectbrazier.features.blocks.drawbridge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class DrawbridgeAnchorBlock extends Block {

	public DrawbridgeAnchorBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}


	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new DrawbridgeAnchorTileEntity();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		TileEntity te = world.getTileEntity(pos);

		if(te != null && te instanceof DrawbridgeAnchorTileEntity){
			DrawbridgeAnchorTileEntity anchorTe = (DrawbridgeAnchorTileEntity) te;
			if(world.isBlockPowered(pos)) anchorTe.startLowering();
			else anchorTe.startRaising();
		}
	}
}
