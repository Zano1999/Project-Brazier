package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blockentities.ZiplineBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blocks.templates.HAxisDecoBlock;
import net.dark_roleplay.projectbrazier.feature.player_actions.zipline_creation.ZiplineCreationAction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ZiplineAnchorBlock extends HAxisDecoBlock implements EntityBlock {
	public ZiplineAnchorBlock(Properties props, String shape) {
		super(props, shape);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ZiplineBlockEntity(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		BlockEntity be = world.getBlockEntity(pos);
		if (!(be instanceof ZiplineBlockEntity zbe)) return InteractionResult.FAIL;

		if(zbe.isInitialized()){
			zbe.startPlayerZipline(player);
			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		return ZiplineCreationAction.handleClick(world, pos, player, hand);
	}
}
