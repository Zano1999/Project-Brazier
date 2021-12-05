package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.dark_roleplay.projectbrazier.util.sitting.SittingUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;

public class WoodStoolBlock extends DecoBlock {
	public WoodStoolBlock(Properties properties, String shapeName) {
		super(properties, shapeName);
	}

	@Deprecated
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		SittingUtil.sitOnBlock(world, pos, player, -0.3F, state);
		return InteractionResult.SUCCESS;
	}
}
