package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.blockentities.FlowerContainerBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.dark_roleplay.projectbrazier.util.capabilities.ItemHandlerUtil;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.block.AbstractBlock.Properties;

public class FlowerContainerBlock extends DecoBlock {

	private static Tags.IOptionalNamedTag<Item> POT_PLANTS = ForgeTagHandler.createOptionalTag(ForgeRegistries.ITEMS, new ResourceLocation(ProjectBrazier.MODID, "pot_plant"));
	private VoxelShape allowedPlacementArea;

	public FlowerContainerBlock(Properties properties, String shapeName, String allowedPlacement) {
		super(properties, shapeName);
		this.allowedPlacementArea = VoxelShapeLoader.getVoxelShape(allowedPlacement);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if(world.isClientSide()) return ActionResultType.SUCCESS;

		TileEntity tileEntity = world.getBlockEntity(pos);
		if(!(tileEntity instanceof FlowerContainerBlockEntity)) return ActionResultType.FAIL;
		FlowerContainerBlockEntity flowerTileEntity = (FlowerContainerBlockEntity) tileEntity;

		ItemStack heldItem = player.getItemInHand(hand);
		if(heldItem.isEmpty()){
			ItemStack stack = flowerTileEntity.removeFlower();
			if(!stack.isEmpty())
				player.addItem(stack);
		}else {
			if(heldItem.getItem() instanceof BlockItem && (ItemTags.FLOWERS.contains(heldItem.getItem()) || POT_PLANTS.contains(heldItem.getItem()))) {
				Vector3d hitPos = hit.getLocation();
				Vector3i offsetPos = new Vector3i((int) Math.floor((hitPos.x() - pos.getX()) * 16), (int) Math.floor((hitPos.y() - pos.getY()) * 16), (int) Math.floor((hitPos.z() - pos.getZ()) * 16));

				boolean isValid = false;
				for(AxisAlignedBB aabb : allowedPlacementArea.toAabbs())
					isValid |= aabb.contains(offsetPos.getX() / 16F, offsetPos.getY() / 16F, offsetPos.getZ() / 16F);

				if(isValid)
					flowerTileEntity.addFlower(heldItem, offsetPos);
			}
		}

		world.sendBlockUpdated(pos, state, state, 3);

		return ActionResultType.SUCCESS;
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state != newState) {
			TileEntity te = world.getBlockEntity(pos);
			if(te != null && te instanceof FlowerContainerBlockEntity){
				FlowerContainerBlockEntity flowerTe = (FlowerContainerBlockEntity) te;
				ItemStack stack = flowerTe.removeFlower();
				while(!stack.isEmpty()) {
					InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
					stack = flowerTe.removeFlower();
				}
			}
			world.removeBlockEntity(pos);
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new FlowerContainerBlockEntity();
	}
}
