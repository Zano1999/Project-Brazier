package net.dark_roleplay.projectbrazier.experimental_features.zipline;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ZiplineStarterBlock extends Block {

	public ZiplineStarterBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ZiplineHelper.startZipline(player, world, new Vector3d(350, 75, 0), new Vector3d(400, 60, 10), new Vector3d(375, 50, 5));
		return ActionResultType.SUCCESS;
	}
}
