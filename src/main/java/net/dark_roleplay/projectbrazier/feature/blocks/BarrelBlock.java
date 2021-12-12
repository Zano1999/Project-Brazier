package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.marg.common.material.MargMaterial;
import net.dark_roleplay.projectbrazier.feature.blockentities.BarrelBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.dark_roleplay.projectbrazier.util.capabilities.ItemHandlerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BarrelBlock extends DecoBlock implements EntityBlock {
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
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if(world.isClientSide) return InteractionResult.SUCCESS;

		if(isClosed) {
			if(player.isShiftKeyDown()) {
				return openBarrel(state, world, pos, player, hand, hit);
			}
		}else {
			InteractionResult closingResult = closeBarrel(state, world, pos, player, hand, hit);
			if(closingResult.shouldSwing())
				return closingResult;

			BlockEntity tileEntity = world.getBlockEntity(pos);
			if(!(tileEntity instanceof BarrelBlockEntity)) return InteractionResult.FAIL;
			BarrelBlockEntity tileEntityBarrel = (BarrelBlockEntity) tileEntity;
			BarrelStorageType type = tileEntityBarrel.getStorageType();

			if(type == BarrelStorageType.FLUID || type == BarrelStorageType.NONE) {
				LazyOptional<IFluidHandler> fluidCap = tileEntityBarrel.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				boolean success = fluidCap.map(fluidHandler -> {
					Optional<FluidStack> fluid = FluidUtil.getFluidContained(player.getItemInHand(hand));
					FluidActionResult result = FluidUtil.tryEmptyContainer(player.getItemInHand(hand), fluidHandler, 16000, player, true);

					if(result.isSuccess()) {
						System.out.println(fluid.get().getFluid().getAttributes().getTemperature(fluid.get()));
						if(fluid.get().getFluid().getAttributes().getTemperature(fluid.get()) > 555){
							world.destroyBlock(pos, false, player, 0);
							world.setBlockAndUpdate(pos, fluid.get().getFluid().defaultFluidState().createLegacyBlock());
						}
						if(!player.isCreative())
							player.setItemInHand(hand, result.getResult());
						return true;
					}else {
						ItemStack stack = player.getItemInHand(hand);
						result = FluidUtil.tryFillContainer(stack, fluidHandler, 16000, player, true);
						if(result.isSuccess()) {
							if(!player.isCreative())
								if(player.getItemInHand(hand).getCount() == 1)
									player.setItemInHand(hand, result.getResult());
								else {
									stack.shrink(1);
									player.drop(result.getResult(), true);
								}

							return true;
						}
					}
					return false;
				}).orElse(false);
				if(success)
					return InteractionResult.SUCCESS;
			}
			if(type == BarrelStorageType.ITEMS || type == BarrelStorageType.NONE) {
				LazyOptional<IItemHandler> invCap = tileEntityBarrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

				NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, pos);
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	private InteractionResult openBarrel(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
		if(!this.isClosed) return InteractionResult.PASS;

		world.setBlockAndUpdate(pos, otherBlock.defaultBlockState());
//				world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state), pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, Math.random() - 0.5F, Math.random(), Math.random() - 0.5F);
		world.playSound(null, pos, this.getSoundType(state, world, pos, null).getBreakSound(), SoundSource.BLOCKS, 2f, 1F);
		return InteractionResult.SUCCESS;
	}

	private InteractionResult closeBarrel(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
		if(this.isClosed) return InteractionResult.PASS;

		if(this.plankItem == null){
			String plankItemName = this.material.getItems().get("planks");
			if(plankItemName == null)
				return InteractionResult.PASS;

			this.plankItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(plankItemName));
			if(this.plankItem == null)
				this.plankItem = Items.BEDROCK;
		}
		ItemStack heldItem = player.getItemInHand(hand);

		if(heldItem.getItem() == this.plankItem) {
			if(!player.isCreative())
				heldItem.shrink(1);
			world.setBlockAndUpdate(pos, otherBlock.defaultBlockState());
			world.playSound(null, pos, this.getSoundType(state, world, pos, null).getPlaceSound(), SoundSource.BLOCKS, 2f, 1F);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (!level.isClientSide && entity.isOnFire()) {
			if(level.getBlockEntity(pos) instanceof BarrelBlockEntity be){
				be.handleEntity(state, pos, entity);
			}
		}
	}



	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && ((newState.getBlock() != this && newState.getBlock() != otherBlock)  || !newState.hasBlockEntity())) {
			//if(!isClosed)
				ItemHandlerUtil.dropContainerItems(world, pos);

			world.removeBlockEntity(pos);
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

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BarrelBlockEntity(pos, state);
	}
}
