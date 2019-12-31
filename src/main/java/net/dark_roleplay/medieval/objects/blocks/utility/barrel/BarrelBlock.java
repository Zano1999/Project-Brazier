package net.dark_roleplay.medieval.objects.blocks.utility.barrel;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler.MedievalItems;
import net.dark_roleplay.medieval.objects.blocks.decoration.chairs.SolidChairTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class BarrelBlock extends Block {

    public static final BooleanProperty HAS_LIT = BooleanProperty.create("has_lid");

    private net.dark_roleplay.marg.api.materials.Material woodMaterial;

    public BarrelBlock(Properties properties, net.dark_roleplay.marg.api.materials.Material woodMaterial) {
        super(properties);
        this.woodMaterial = woodMaterial;
        this.setDefaultState(this.getDefaultState().with(HAS_LIT, false));
    }

    //TODO Fix: Temporary Hacky Solution
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        CompoundNBT nbtCompound = context.getItem().getTag();
        return this.getDefaultState().with(HAS_LIT, nbtCompound != null && nbtCompound.contains("has_lid"));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        boolean closed = state.get(HAS_LIT);

        if(closed) {
            if(player.isSneaking()) {
                //for(int i = 0; i < 15; i++)
                    //world.addParticle(, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, Math.random() - 0.5F, Math.random(), Math.random() - 0.5F, Block.getStateId(state));

                if(world.isRemote) return true;

                world.setBlockState(pos, state.with(HAS_LIT, false));

                world.playSound(null, pos, this.getSoundType(state, world, pos, null).getBreakSound(), SoundCategory.BLOCKS, 2f, 1F);

            }
        }else {
            if(world.isRemote) return true;

            Item litItem = MedievalItems.BARREL_LIT.get(this.woodMaterial).get();

            ItemStack heldItem = player.getHeldItem(hand);
            if(heldItem.getItem() == litItem) {
                heldItem.shrink(1);
                world.setBlockState(pos, state.with(HAS_LIT, true));
                world.playSound(null, pos, this.getSoundType(state, world, pos, null).getPlaceSound(), SoundCategory.BLOCKS, 2f, 1F);
                return true;
            }

            TileEntity tileEntity = world.getTileEntity(pos);
            if(!(tileEntity instanceof BarrelTileEntity)) return true;
            BarrelTileEntity tileEntityBarrel = (BarrelTileEntity) tileEntity;
            BarrelTileEntity.StorageType type = tileEntityBarrel.getStorageType();

            if(type == BarrelTileEntity.StorageType.FLUID || type == BarrelTileEntity.StorageType.NONE) {
                LazyOptional<IFluidHandler> fluidCap = tileEntityBarrel.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                boolean success = fluidCap.map(fluidHandler -> {
                    FluidActionResult result = FluidUtil.tryEmptyContainer(player.getHeldItem(hand), fluidHandler, 16000, player, true);

                    if(result.isSuccess()) {
                        if(!player.isCreative())
                            player.setHeldItem(hand, result.getResult());
                        return true;
                    }else {
                        result = FluidUtil.tryFillContainer(player.getHeldItem(hand), fluidHandler, 16000, player, true);
                        if(result.isSuccess()) {
                            if(!player.isCreative())
                                player.setHeldItem(hand, result.getResult());

                            return true;
                        }
                    }
                    return false;
                }).orElse(false);
                if(success)
                    return true;
            }
            if(type == BarrelTileEntity.StorageType.ITEMS || type == BarrelTileEntity.StorageType.NONE) {
                LazyOptional<IItemHandler> invCap = tileEntityBarrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, pos);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock() && !state.get(HAS_LIT)) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity != null && tileentity instanceof BarrelTileEntity) {
                BarrelTileEntity bTe = (BarrelTileEntity) tileentity;
                if(bTe.getStorageType() == BarrelTileEntity.StorageType.ITEMS){
                    LazyOptional<IItemHandler> lazyInv = bTe.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    lazyInv.ifPresent(inventory -> {
                       for(int i = 0; i < inventory.getSlots(); i++){
                           world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i)));
                       }
                    });
                }
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BarrelTileEntity();
    }
}
