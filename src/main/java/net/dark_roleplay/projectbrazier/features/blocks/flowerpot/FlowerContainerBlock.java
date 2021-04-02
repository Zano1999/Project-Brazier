package net.dark_roleplay.projectbrazier.features.blocks.flowerpot;

import net.dark_roleplay.projectbrazier.features.blocks.templates.DecoBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FlowerContainerBlock extends DecoBlock {

	public FlowerContainerBlock(Properties properties, String shapeName) {
		super(properties, shapeName);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if(world.isRemote()) return ActionResultType.SUCCESS;

		TileEntity tileEntity = world.getTileEntity(pos);
		if(!(tileEntity instanceof FlowerContainerTileEntity)) return ActionResultType.FAIL;
		FlowerContainerTileEntity flowerTileEntity = (FlowerContainerTileEntity) tileEntity;

		ItemStack heldItem = player.getHeldItem(hand);
		if(heldItem.isEmpty()){
			ItemStack stack = flowerTileEntity.removeFlower();
			if(!stack.isEmpty())
				player.addItemStackToInventory(stack);
		}else {
			flowerTileEntity.addFlower(heldItem);
		}

		world.notifyBlockUpdate(pos, state, state, 3);

		return ActionResultType.SUCCESS;
	}


	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new FlowerContainerTileEntity();
	}
}
