package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler_2.MedievalTileEntities;
import net.dark_roleplay.medieval.objects.guis.EditRoadSignScreen;
import net.dark_roleplay.medieval.objects.items.equipment.misc.RoadSignItem;
import net.dark_roleplay.medieval.objects.packets.RoadSignPlacementPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
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
		return MedievalTileEntities.ROAD_SIGN.get().create();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext selectionContext) {
		return Block.makeCuboidShape(6, 0, 6, 10, 16, 10);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!(player.getHeldItemMainhand().getItem() instanceof RoadSignItem)) return false;

		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof RoadSignTileEntity)) return false;
		if(world.isRemote){
			DarkRoleplayMedieval.MOD_CHANNEL.sendToServer(new RoadSignPlacementPacket());
			return true;
		}

		Boolean isRight = RoadSignPlacementPacket.players.get(player);
		if(isRight == null) isRight = false;

		RoadSignTileEntity rte = (RoadSignTileEntity) te;

		int signID = rte.addSign((int)((hit.getHitVec().y - pos.getY()) * 16), (int) (-player.getYaw(0)), ((RoadSignItem) player.getHeldItemMainhand().getItem()).getMaterial().getName(), !isRight);
		if(!world.isRemote && !player.isCreative()) player.getHeldItemMainhand().shrink(1);

		world.markAndNotifyBlock(pos, world.getChunkAt(pos), state, state, 3);

		Minecraft.getInstance().displayGuiScreen(new EditRoadSignScreen(rte, signID));
		return true;
	}
}
