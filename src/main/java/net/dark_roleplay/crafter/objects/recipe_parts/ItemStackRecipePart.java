package net.dark_roleplay.crafter.objects.recipe_parts;

import net.dark_roleplay.crafter.api.icon.IIcon;
import net.dark_roleplay.crafter.api.icon.ItemStackIcon;
import net.dark_roleplay.crafter.api.recipe_parts.IRecipePart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class ItemStackRecipePart implements IRecipePart {

    private ItemStack stack;
    private boolean ignoreNBT;
    private ItemStackIcon icon;

    public ItemStackRecipePart(ItemStack stack, boolean ignoreNBT){
        this.stack = stack;
        this.ignoreNBT = ignoreNBT;
        this.icon = new ItemStackIcon(stack);
    }

    @Override
    public IIcon getIcon() {
        return this.icon;
    }

    @Override
    public ResourceLocation getId() {
        return stack.getItem().getRegistryName();
    }

    @Override
    public boolean doesPlayerProvide(PlayerEntity player) {
        NonNullList<ItemStack> inventory = player.inventory.mainInventory;
        int requiredCount = this.stack.getCount();
        for(int i = 0; i < inventory.size(); i++){
            requiredCount -= ignoreNBT ? getAmountIgnoreNBT(inventory.get(i)) : getAmount(inventory.get(i));
            if(requiredCount <= 0)
                return true;
        }
        return false;
    }

    @Override
    public boolean doesTileEntityProvide(TileEntity tileEntity) {
        LazyOptional<IItemHandler> lazyInventory = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
        AtomicInteger requiredCount = new AtomicInteger(this.stack.getCount());
        lazyInventory.ifPresent(inventory -> {
            for(int i = 0; i < inventory.getSlots(); i++){
                requiredCount.set(requiredCount.get() - (ignoreNBT ? getAmountIgnoreNBT(inventory.getStackInSlot(i)) : getAmount(inventory.getStackInSlot(i))));
                if(requiredCount.get() <= 0)
                    return;
            }
        });
        return requiredCount.get() <= 0;
    }

    @Override
    public void consumeFromPlayer(PlayerEntity player) {

    }

    @Override
    public void consumeFromTileEntity(TileEntity tileEntity) {

    }



    //Helper for provider Checks
    private int getAmount(ItemStack other){
        if(!other.isEmpty() && other.getItem() == this.stack.getItem() && ItemStack.areItemStackTagsEqual(this.stack, other))
            return other.getCount();
        return 0;
    }

    private int getAmountIgnoreNBT(ItemStack other){
        if(!other.isEmpty() && other.getItem() == this.stack.getItem())
            return other.getCount();
        return 0;
    }
}
