package net.dark_roleplay.medieval.objects.color_handlers;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

import javax.annotation.Nullable;

public class SingleColorHandler implements IBlockColor, IItemColor {

	private int color = 0;

	public SingleColorHandler(int color){
		this.color = color;
	}

	@Override
	public int getColor(ItemStack stack, int index) {
		return index == 0 ? color : -1;

	}

	@Override
	public int getColor(BlockState state, @Nullable ILightReader reader, @Nullable BlockPos pos, int index) {
		return index == 0 ? color : -1;
	}
}
