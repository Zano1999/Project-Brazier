package net.dark_roleplay.medieval.objects.blocks.decoration.road_sign;

import net.dark_roleplay.medieval.handler.MedievalNetworking;
import net.dark_roleplay.medieval.networking.sign_post.SignPostPlacementPacket;
import net.dark_roleplay.medieval.objects.guis.EditRoadSignScreen;
import net.dark_roleplay.medieval.objects.items.equipment.misc.RoadSignItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
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
		return null;//TODO restore
//		return MedievalTileEntities.ROAD_SIGN.get().create();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext selectionContext) {
		return Block.makeCuboidShape(6, 0, 6, 10, 16, 10);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!(player.getHeldItemMainhand().getItem() instanceof RoadSignItem)) return ActionResultType.PASS;

		TileEntity te = world.getTileEntity(pos);
		if(!(te instanceof RoadSignTileEntity)) return ActionResultType.FAIL;
		if(world.isRemote){
			MedievalNetworking.CHANNEL.sendToServer(new SignPostPlacementPacket());
			return ActionResultType.SUCCESS;
		}

		Boolean isRight = SignPostPlacementPacket.getPlayer(player);

		RoadSignTileEntity rte = (RoadSignTileEntity) te;

		int signID = rte.addSign((int)((hit.getHitVec().y - pos.getY()) * 16), (int) (-player.getYaw(0)), new ItemStack(player.getHeldItemMainhand().getItem()), !isRight);
		if(!world.isRemote && !player.isCreative()) player.getHeldItemMainhand().shrink(1);

		world.markAndNotifyBlock(pos, world.getChunkAt(pos), state, state, 3);

		Minecraft.getInstance().displayGuiScreen(new EditRoadSignScreen(rte, signID));
		return ActionResultType.SUCCESS;
	}
}
