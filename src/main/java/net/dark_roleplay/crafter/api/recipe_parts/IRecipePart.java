package net.dark_roleplay.crafter.api.recipe_parts;

import net.dark_roleplay.crafter.api.icon.IIcon;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public interface IRecipePart {

    IIcon getIcon();

    ResourceLocation getId();
    boolean doesPlayerProvide(PlayerEntity player);
    boolean doesTileEntityProvide(TileEntity tileEntity);

    void consumeFromPlayer(PlayerEntity player);
    void consumeFromTileEntity(TileEntity tileEntity);
}
