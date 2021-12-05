package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.ProjectBrazier;
import net.dark_roleplay.projectbrazier.feature.blockentities.FlowerContainerBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.dark_roleplay.projectbrazier.util.json.VoxelShapeLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class FlowerContainerBlock extends DecoBlock {

	private static Tags.IOptionalNamedTag<Item> POT_PLANTS = ForgeTagHandler.createOptionalTag(ForgeRegistries.ITEMS, new ResourceLocation(ProjectBrazier.MODID, "pot_plant"));
	private VoxelShape allowedPlacementArea;

	public FlowerContainerBlock(Properties properties, String shapeName, String allowedPlacement) {
		super(properties, shapeName);
		this.allowedPlacementArea = VoxelShapeLoader.getVoxelShape(allowedPlacement);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if(world.isClientSide()) return InteractionResult.SUCCESS;

		BlockEntity tileEntity = world.getBlockEntity(pos);
		if(!(tileEntity instanceof FlowerContainerBlockEntity)) return InteractionResult.FAIL;
		FlowerContainerBlockEntity flowerTileEntity = (FlowerContainerBlockEntity) tileEntity;

		ItemStack heldItem = player.getItemInHand(hand);
		if(heldItem.isEmpty()){
			ItemStack stack = flowerTileEntity.removeFlower();
			if(!stack.isEmpty())
				player.addItem(stack);
		}else {
			if(heldItem.getItem() instanceof BlockItem && (ItemTags.FLOWERS.contains(heldItem.getItem()) || POT_PLANTS.contains(heldItem.getItem()))) {
				Vec3 hitPos = hit.getLocation();
				Vec3i offsetPos = new Vec3i((int) Math.floor((hitPos.x() - pos.getX()) * 16), (int) Math.floor((hitPos.y() - pos.getY()) * 16), (int) Math.floor((hitPos.z() - pos.getZ()) * 16));

				boolean isValid = false;
				for(AABB aabb : allowedPlacementArea.toAabbs())
					isValid |= aabb.contains(offsetPos.getX() / 16F, offsetPos.getY() / 16F, offsetPos.getZ() / 16F);

				if(isValid)
					flowerTileEntity.addFlower(heldItem, offsetPos);
			}
		}

		world.sendBlockUpdated(pos, state, state, 3);

		return InteractionResult.SUCCESS;
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state != newState) {
			BlockEntity te = world.getBlockEntity(pos);
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
	public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
		return new FlowerContainerBlockEntity();
	}
}
