package net.dark_roleplay.medieval.objects.items.blocks;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class BarrelBlockItem extends BlockItem {

    String closedTranslationKey = null;

    public BarrelBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
        this.addPropertyOverride(new ResourceLocation(DarkRoleplayMedieval.MODID, "lit"),
                (stack, world, entity) -> stack.getOrCreateTag().contains("isClosed") ? 1 : 0 );

    }

    public String getTranslationKey(ItemStack stack) {
        if(closedTranslationKey == null){
            ResourceLocation key = this.getRegistryName();
            closedTranslationKey = Util.makeTranslationKey("item", new ResourceLocation(key.getNamespace(), "closed_" + key.getPath()));
        }

        return stack.getOrCreateTag().contains("isClosed") ? this.closedTranslationKey : this.getTranslationKey();
    }

}
