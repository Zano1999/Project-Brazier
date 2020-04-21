package net.dark_roleplay.medieval.features.items;

import net.dark_roleplay.medieval.handler.MedievalSounds;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WarHornItem extends Item {

	public WarHornItem(Properties properties) {
		super(properties);
	}

	protected static int maxDistance = 512;

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack item = player.getHeldItem(hand);
		if(world.isRemote()) return ActionResult.resultSuccess(item);
		player.setActiveHand(hand);

		return ActionResult.resultSuccess(item);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
		if(!(world instanceof ServerWorld)) return stack;
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity)entity : null;

		if(entity.areEyesInFluid(FluidTags.WATER)){
			Vec3d spawnPoint = entity.getPositionVec().add(0, entity.getEyeHeight(), 0).add(entity.getLookVec().mul(0.5F, 0.5F, 0.5F));
			((ServerWorld) world).spawnParticle(ParticleTypes.BUBBLE, spawnPoint.x, spawnPoint.y, spawnPoint.z, 8, 0.05, 0,0.05, 0.005);
			world.playSound(null, entity.getPosition(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.PLAYERS, 1, 1);
			entity.setAir(Math.max(0, entity.getAir() - 90));
		}else {
			for(ServerPlayerEntity receiver : ((ServerWorld) world).getPlayers()){
				double distance = receiver.getPositionVec().distanceTo(entity.getPositionVec());
				if(distance > maxDistance) continue;
				Vec3d sourcePos = receiver.getPositionVec().add(receiver.getPositionVec().subtract(entity.getPositionVec()).normalize().mul(10, 10, 10));

				receiver.connection.sendPacket(new SPlaySoundEffectPacket(MedievalSounds.WAR_HORN.get(), SoundCategory.PLAYERS, sourcePos.x, sourcePos.y, sourcePos.z, (float)((maxDistance-distance)/maxDistance) * 0.5F, 0.9F + world.getRandom().nextFloat()*0.2F));
			}
		}

		if(player != null)
			player.getCooldownTracker().setCooldown(stack.getItem(), 120);

		stack.damageItem(1, entity, breaker -> {
			world.playSound(null, breaker.getPosition(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
		});

		return stack;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 20;
	}
}