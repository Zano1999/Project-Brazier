package net.dark_roleplay.projectbrazier.feature.items;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class WarHornItem extends Item {

	public WarHornItem(Properties properties) {
		super(properties);
	}

	protected static int maxDistance = 512;

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack item = player.getItemInHand(hand);

		if (world.isClientSide()) return InteractionResultHolder.success(item);
		player.startUsingItem(hand);

		return InteractionResultHolder.success(item);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
		if (!(world instanceof ServerLevel)) return stack;
		Player player = entity instanceof Player ? (Player) entity : null;

		if (entity.isEyeInFluid(FluidTags.WATER)) {
			Vec3 spawnPoint = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle().multiply(0.5F, 0.5F, 0.5F));
			((ServerLevel) world).sendParticles(ParticleTypes.BUBBLE, spawnPoint.x, spawnPoint.y, spawnPoint.z, 8, 0.05, 0, 0.05, 0.005);
			world.playSound(null, entity.blockPosition(), SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.PLAYERS, 1, 1);
			entity.setAirSupply(Math.max(0, entity.getAirSupply() - 90));
		} else {
			for (ServerPlayer receiver : ((ServerLevel) world).players()) {
				double distance = receiver.position().distanceTo(entity.position());
				if (distance > maxDistance) continue;
				Vec3 sourcePos = receiver.position().add(entity.position().subtract(receiver.position()).normalize().multiply(6, 6, 6));

				receiver.connection.send(new ClientboundSoundPacket(BrazierSounds.WAR_HORN.get(), SoundSource.PLAYERS, sourcePos.x, sourcePos.y, sourcePos.z, (float) ((maxDistance - distance) / maxDistance) * 0.5F, 0.9F + world.getRandom().nextFloat() * 0.2F));
			}
		}

		if (player != null)
			player.getCooldowns().addCooldown(stack.getItem(), 120);

		stack.hurtAndBreak(1, entity, breaker -> {
			world.playSound(null, breaker.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1, 1);
		});

		return stack;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 20;
	}
}