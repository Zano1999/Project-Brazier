package net.dark_roleplay.medieval.objects.blocks.utility.barrel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class BarrelBlock extends Block {

    public static final BooleanProperty HAS_LIT = BooleanProperty.create("has_lit");

    public BarrelBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(HAS_LIT, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        CompoundNBT nbtCompound = context.getItem().getTag();
        return this.getDefaultState().with(HAS_LIT, nbtCompound != null && nbtCompound.contains("has_lit"));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
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
}
