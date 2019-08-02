package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.dark_roleplay.medieval.holders.MedievalTileEntities;
import net.dark_roleplay.medieval.objects.items.equipment.misc.RoadSignItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class RoadSign extends Block{

	public RoadSign(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return MedievalTileEntities.ROAD_SIGN.create();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext selectionContext) {
		TileEntity te = world.getTileEntity(pos);
		if(te == null || !(te instanceof RoadSignTileEntity)) return Block.makeCuboidShape(6, 0, 6, 10, 16, 10);
		return VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6, 0, 6, 10, 16, 10), ((RoadSignTileEntity)world.getTileEntity(pos)).getSignsShape(), IBooleanFunction.OR);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!(player.getHeldItemMainhand().getItem() instanceof RoadSignItem)) return false;

		TileEntity te = worldIn.getTileEntity(pos);
		if(!(te instanceof RoadSignTileEntity)) return false;
		RoadSignTileEntity rte = (RoadSignTileEntity) te;

		rte.addSign((int)((hit.getHitVec().y - pos.getY()) * 16), (int) (-player.getRotationYawHead()), "oak", false);
		if(!worldIn.isRemote && !player.isCreative()) player.getHeldItemMainhand().shrink(1);

		worldIn.markAndNotifyBlock(pos, worldIn.getChunkAt(pos), state, state, 3);

		return true;
	}
}
