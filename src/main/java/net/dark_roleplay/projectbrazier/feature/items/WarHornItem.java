package net.dark_roleplay.projectbrazier.feature.items;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.item.Item.Properties;

public class WarHornItem extends Item {

	public WarHornItem(Properties properties) {
		super(properties);
	}

	protected static int maxDistance = 512;

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack item = player.getItemInHand(hand);

		if (world.isClientSide()) return ActionResult.success(item);
		player.startUsingItem(hand);

		return ActionResult.success(item);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
		if (!(world instanceof ServerWorld)) return stack;
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;

		if (entity.isEyeInFluid(FluidTags.WATER)) {
			Vector3d spawnPoint = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle().multiply(0.5F, 0.5F, 0.5F));
			((ServerWorld) world).sendParticles(ParticleTypes.BUBBLE, spawnPoint.x, spawnPoint.y, spawnPoint.z, 8, 0.05, 0, 0.05, 0.005);
			world.playSound(null, entity.blockPosition(), SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.PLAYERS, 1, 1);
			entity.setAirSupply(Math.max(0, entity.getAirSupply() - 90));
		} else {
			for (ServerPlayerEntity receiver : ((ServerWorld) world).players()) {
				double distance = receiver.position().distanceTo(entity.position());
				if (distance > maxDistance) continue;
				Vector3d sourcePos = receiver.position().add(entity.position().subtract(receiver.position()).normalize().multiply(6, 6, 6));

				receiver.connection.send(new SPlaySoundEffectPacket(BrazierSounds.WAR_HORN.get(), SoundCategory.PLAYERS, sourcePos.x, sourcePos.y, sourcePos.z, (float) ((maxDistance - distance) / maxDistance) * 0.5F, 0.9F + world.getRandom().nextFloat() * 0.2F));
			}
		}

		if (player != null)
			player.getCooldowns().addCooldown(stack.getItem(), 120);

		stack.hurtAndBreak(1, entity, breaker -> {
			world.playSound(null, breaker.blockPosition(), SoundEvents.ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
		});

		return stack;
	}

	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 20;
	}
}