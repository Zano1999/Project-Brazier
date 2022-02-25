package net.dark_roleplay.projectbrazier.feature.player_actions.zipline_creation;

import com.mojang.math.Vector3f;
import net.dark_roleplay.projectbrazier.feature.blockentities.ZiplineBlockEntity;
import net.dark_roleplay.projectbrazier.feature.blocks.ZiplineAnchorBlock;
import net.dark_roleplay.projectbrazier.feature.registrars.BrazierItems;
import net.dark_roleplay.projectbrazier.util.Inventories;
import net.dark_roleplay.projectbrazier.util.math.VectorUtils;
import net.dark_roleplay.projectbrazier.util.math.bezier.BezierCurve;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Map;
import java.util.WeakHashMap;

public final class ZiplineCreationAction {
	private static Map<Player, ZiplineCreationAction> actions = new WeakHashMap<>();

	public static InteractionResult handleClick(Level level, BlockPos pos, Player player, InteractionHand hand) {
		ZiplineCreationAction action = actions.computeIfAbsent(player, p -> new ZiplineCreationAction());
		InteractionResultHolder<Component> result = action.handleClickInternal(level, pos, player, hand);
		if (result.getObject() != null)
			player.displayClientMessage(result.getObject(), true);
		return result.getResult();
	}

	private ZiplineCreationAction() {
	}

	private Level level;
	private BlockPos initPos;

	private InteractionResultHolder<Component> handleClickInternal(Level level, BlockPos pos, Player player, InteractionHand hand) {
		if (player.getItemInHand(hand).getItem() != BrazierItems.ROPE.get()) return InteractionResultHolder.pass(null);

		if (this.level == null || this.initPos == null || level.dimension() != this.level.dimension()) {
			if(!level.isClientSide()) {
				this.level = level;
				this.initPos = pos.immutable();
			}

			return InteractionResultHolder.sidedSuccess(new TranslatableComponent("interaction.projectbrazier.zipline.rope.set").withStyle(ChatFormatting.GREEN), level.isClientSide);
		}else if(!(level.getBlockState(this.initPos).getBlock() instanceof ZiplineAnchorBlock)){
			if(!level.isClientSide()) {
				this.level = level;
				this.initPos = pos.immutable();
			}
			return InteractionResultHolder.sidedSuccess(new TranslatableComponent("interaction.projectbrazier.zipline.rope.set").withStyle(ChatFormatting.GREEN), level.isClientSide);
		}

		BlockPos posAHor = new BlockPos(this.initPos.getX(), 0, this.initPos.getZ());
		BlockPos posBHor = new BlockPos(pos.getX(), 0, pos.getZ());

		double verticalDistance = Math.abs(this.initPos.getY() - pos.getY());
		double horizontalDistance = Math.sqrt(posAHor.distSqr(posBHor));

		if (horizontalDistance < 7)
			return InteractionResultHolder.fail(new TranslatableComponent("interaction.projectbrazier.zipline.rope.to_short", 7).withStyle(ChatFormatting.RED));
		else if (horizontalDistance > 256)
			return InteractionResultHolder.fail(new TranslatableComponent("interaction.projectbrazier.zipline.rope.to_far", 256).withStyle(ChatFormatting.RED));
		else if (verticalDistance < Math.ceil(horizontalDistance * 0.05))
			return InteractionResultHolder.fail(new TranslatableComponent("interaction.projectbrazier.zipline.rope.not_steep_enough").withStyle(ChatFormatting.RED));

		BlockPos topPos = this.initPos.getY() > pos.getY() ? this.initPos : pos;
		BlockPos bottomPos = this.initPos.getY() > pos.getY() ? pos : this.initPos;

		BlockEntity be = level.getBlockEntity(topPos);
		if (!(be instanceof ZiplineBlockEntity zbe)) return InteractionResultHolder.fail(null);

		Vector3f start = new Vector3f(topPos.getX() + 0.5F, topPos.getY() + 0.5F, topPos.getZ() + 0.5F);
		Vector3f end = new Vector3f(bottomPos.getX() + 0.5F, bottomPos.getY() + 0.5F, bottomPos.getZ() + 0.5F);
		Vector3f mid = VectorUtils.lerpVector(start, end, 0.6);
		float yDist = start.y() - end.y(),
				xDist = start.x() - end.x(),
				zDist = start.z() - end.z();
		float distance = (float) Math.sqrt(yDist * yDist + xDist * xDist + zDist * zDist);
		mid.setY(mid.y() - Math.min(yDist * 0.4F, 7F));

		BezierCurve curve = BezierCurve.createGlobal(start, mid, end, (int) distance);

		int requiredRope = (int) Math.ceil(curve.getLength());
		int missing = 0;
		if (!player.isCreative() && (missing = Inventories.doesPlayerHaveEnoughItems(player, BrazierItems.ROPE.get(), requiredRope)) > 0) {
			return InteractionResultHolder.fail(new TranslatableComponent("interaction.projectbrazier.zipline.rope.not_enough_rope", missing).withStyle(ChatFormatting.RED));
		} else {
			if(level.isClientSide()) return InteractionResultHolder.sidedSuccess(null, level.isClientSide);
			if (!player.isCreative())
				Inventories.consumeAmountOfItems(player, BrazierItems.ROPE.get(), requiredRope);
			zbe.setPositionInitial(start, mid, end, curve);
			level.markAndNotifyBlock(topPos, level.getChunkAt(topPos), level.getBlockState(topPos), level.getBlockState(topPos), 3, 0);
		}

		this.initPos = null;
		this.level = null;
		return InteractionResultHolder.sidedSuccess(null, level.isClientSide);
	}
}