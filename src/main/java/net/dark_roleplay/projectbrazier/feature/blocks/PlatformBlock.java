package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.HAxisDecoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;

public class PlatformBlock extends HAxisDecoBlock {
	protected Block otherBlock;

	public PlatformBlock(Properties properties, String shapeName) {
		super(properties, shapeName);
	}

	public void initOtherBlock(Block otherBlock) {
		this.otherBlock = otherBlock;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		double hitY = context.getHitVec().getY() - context.getPos().getY();
		BlockState source = hitY > 0.5 ? this.getDefaultState() : otherBlock.getDefaultState();
		return source.with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().getAxis());
	}

	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}
}
