package net.dark_roleplay.projectbrazier.objects.blocks;

import net.dark_roleplay.projectbrazier.objects.blocks.templates.HAxisDecoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraftforge.fml.RegistryObject;

public class PlatformBlock extends HAxisDecoBlock {
	protected final RegistryObject<Block> bottomPlattform;

	public PlatformBlock(Properties properties, String shapeName, RegistryObject<Block> bottomPlatform) {
		super(properties, shapeName);
		this.bottomPlattform = bottomPlatform;
	}


	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		double hitY = context.getHitVec().getY() - context.getPos().getY();
		BlockState source = hitY > 0.5 ? this.getDefaultState() : bottomPlattform.get().getDefaultState();
		return source.with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().getAxis());
	}

	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}
}
