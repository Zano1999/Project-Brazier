package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.feature.blockentities.BarrelBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.dark_roleplay.projectbrazier.util.CapabilityUtil;
import net.dark_roleplay.projectbrazier.util.capabilities.ItemHandlerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;

public class BarrelBlock extends DecoBlock {
	private boolean isClosed;
	private Block otherBlock;
	private MargMaterial material;
	private Item plankItem;
	public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

	public BarrelBlock(MargMaterial material, Properties properties, String voxelShape, boolean isClosed) {
		super(properties, voxelShape);
		this.isClosed = isClosed;
		this.material = material;
	}

	public void setOtherBlock(Block block){
		this.otherBlock = block;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if(world.isRemote) return ActionResultType.SUCCESS;

		if(isClosed) {
			if(player.isSneaking()) {
				return openBarrel(state, world, pos, player, hand, hit);
			}
		}else {
			ActionResultType closingResult = closeBarrel(state, world, pos, player, hand, hit);
			if(closingResult.isSuccess())
				return closingResult;

			TileEntity tileEntity = world.getTileEntity(pos);
			if(!(tileEntity instanceof BarrelBlockEntity)) return ActionResultType.FAIL;
			BarrelBlockEntity tileEntityBarrel = (BarrelBlockEntity) tileEntity;
			BarrelStorageType type = tileEntityBarrel.getStorageType();

			if(type == BarrelStorageType.FLUID || type == BarrelStorageType.NONE) {
				LazyOptional<IFluidHandler> fluidCap = tileEntityBarrel.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				boolean success = fluidCap.map(fluidHandler -> {
					Optional<FluidStack> fluid = FluidUtil.getFluidContained(player.getHeldItem(hand));
					FluidActionResult result = FluidUtil.tryEmptyContainer(player.getHeldItem(hand), fluidHandler, 16000, player, true);

					if(result.isSuccess()) {
						System.out.println(fluid.get().getFluid().getAttributes().getTemperature(fluid.get()));
						if(fluid.get().getFluid().getAttributes().getTemperature(fluid.get()) > 555){
							world.destroyBlock(pos, false, player, 0);
							world.setBlockState(pos, fluid.get().getFluid().getDefaultState().getBlockState());
						}
						if(!player.isCreative())
							player.setHeldItem(hand, result.getResult());
						return true;
					}else {
						ItemStack stack = player.getHeldItem(hand);
						result = FluidUtil.tryFillContainer(stack, fluidHandler, 16000, player, true);
						if(result.isSuccess()) {
							if(!player.isCreative())
								if(player.getHeldItem(hand).getCount() == 1)
									player.setHeldItem(hand, result.getResult());
								else {
									stack.shrink(1);
									player.dropItem(result.getResult(), true);
								}

							return true;
						}
					}
					return false;
				}).orElse(false);
				if(success)
					return ActionResultType.SUCCESS;
			}
			if(type == BarrelStorageType.ITEMS || type == BarrelStorageType.NONE) {
				LazyOptional<IItemHandler> invCap = tileEntityBarrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, pos);
				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.PASS;
	}

	private ActionResultType openBarrel(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
		if(!this.isClosed) return ActionResultType.PASS;

		world.setBlockState(pos, otherBlock.getDefaultState());
//				world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state), pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, Math.random() - 0.5F, Math.random(), Math.random() - 0.5F);
		world.playSound(null, pos, this.getSoundType(state, world, pos, null).getBreakSound(), SoundCategory.BLOCKS, 2f, 1F);
		return ActionResultType.SUCCESS;
	}

	private ActionResultType closeBarrel(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
		if(this.isClosed) return ActionResultType.PASS;

		if(this.plankItem == null){
			String plankItemName = this.material.getItems().get("planks");
			if(plankItemName == null)
				return ActionResultType.PASS;

			this.plankItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(plankItemName));
			if(this.plankItem == null)
				this.plankItem = Items.BEDROCK;
		}
		ItemStack heldItem = player.getHeldItem(hand);

		if(heldItem.getItem() == this.plankItem) {
			if(!player.isCreative())
				heldItem.shrink(1);
			world.setBlockState(pos, otherBlock.getDefaultState());
			world.playSound(null, pos, this.getSoundType(state, world, pos, null).getPlaceSound(), SoundCategory.BLOCKS, 2f, 1F);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasTileEntity() && ((newState.getBlock() != this && newState.getBlock() != otherBlock)  || !newState.hasTileEntity())) {
			//if(!isClosed)
				ItemHandlerUtil.dropContainerItems(world, pos);

			world.removeTileEntity(pos);
		}
	}

//	@Override
//	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
//		if(!this.isClosed) return super.getDrops(state, builder);
//		TileEntity tileentity = builder.get(LootParameters.BLOCK_ENTITY);
//		if(tileentity instanceof BarrelBlockEntity){
//			ItemStackHandler inv = ((BarrelBlockEntity) tileentity).getItemHandler();
//			if(inv == null) return super.getDrops(state, builder);
//
//			builder = builder.withDynamicDrop(CONTENTS, (context, stackConsumer) -> {
//				for(int i = 0; i < inv.getSlots(); i++)
//					stackConsumer.accept(inv.getStackInSlot(i));
//			});
//		}
//
//		return super.getDrops(state, builder);
//	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new BarrelBlockEntity();
	}
}
