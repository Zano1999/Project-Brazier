package net.dark_roleplay.projectbrazier.objects.blocks;

import net.dark_roleplay.projectbrazier.objects.blocks.templates.HFacedDecoBlock;
import net.dark_roleplay.projectbrazier.util.sitting.SittingUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ChairBlock extends HFacedDecoBlock {
	public ChairBlock(Properties props, String shapeName) {
		super(props, shapeName);
	}

	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if(player.getPositionVec().squareDistanceTo(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) < 4) {
			if(!world.isRemote())
				SittingUtil.sitOnBlockWithRotation((ServerWorld) world, pos.getX(), pos.getY(), pos.getZ(), player, state.get(HORIZONTAL_FACING), state.get(HORIZONTAL_FACING), 0.2F);
		}else {
			player.sendStatusMessage(new TranslationTextComponent("interaction.drpmedieval.chair.to_far", state.getBlock().getTranslatedName()), true);
		}
		return ActionResultType.SUCCESS;
	}
}
