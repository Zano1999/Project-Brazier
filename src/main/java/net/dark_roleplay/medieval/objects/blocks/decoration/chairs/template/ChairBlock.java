package net.dark_roleplay.medieval.objects.blocks.decoration.chairs.template;

import net.dark_roleplay.medieval.objects.blocks.templates.HorizontalBlock;
import net.dark_roleplay.medieval.util.sitting.SittingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ChairBlock extends HorizontalBlock {

	public ChairBlock(Properties properties) {
		super(properties);
		
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (facing != Direction.DOWN)
			return state;
		if (!Block.hasSolidSideOnTop(world, facingPos))
			return Blocks.AIR.getDefaultState();
		return state;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if(player.getPositionVec().squareDistanceTo(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) < 9) {
			if(!world.isRemote())
				SittingUtil.sitOnBlockWithRotation((ServerWorld) world, pos.getX(), pos.getY(), pos.getZ(), player, state.get(HORIZONTAL_FACING), 0.2F);
		}else {
			player.sendStatusMessage(new TranslationTextComponent("interaction.drpmedieval.chair.to_far", state.getBlock().getNameTextComponent().getFormattedText()), true);
		}
		return ActionResultType.SUCCESS;
	}
}
