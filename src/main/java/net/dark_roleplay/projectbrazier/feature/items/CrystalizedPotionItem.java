package net.dark_roleplay.projectbrazier.feature.items;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class CrystalizedPotionItem extends Item {

	public CrystalizedPotionItem(Properties properties) {
		super(properties);
	}

	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag showAdvanced) {
		PotionUtils.addPotionTooltip(stack, components, 1.0F);
	}

	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> itemList) {
		if (this.allowdedIn(tab)) {
			for(Potion potion : Registry.POTION) {
				if (potion != Potions.EMPTY) {
					itemList.add(PotionUtils.setPotion(new ItemStack(this), potion));
				}
			}
		}
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if(!level.isClientSide())
			causePotionExplosion(player.getItemInHand(hand), level, player, player.position());
		return InteractionResultHolder.pass(player.getItemInHand(hand));
	}

	@Override
	public void onDestroyed(ItemEntity itemEntity) {
		causePotionExplosion(itemEntity.getItem(), itemEntity.getLevel(), null, itemEntity.position());
	}

	public void causePotionExplosion(ItemStack stack, Level level, Player player, Vec3 pos){
		List<MobEffectInstance> effects = PotionUtils.getMobEffects(stack);

		List<Entity> entities = level.getEntities((Entity) null, new AABB(pos, pos).inflate(5), entity -> entity instanceof LivingEntity);

//		level.addAlwaysVisibleParticle(
		if(level instanceof ServerLevel serverLevel){
			serverLevel.sendParticles(ParticleTypes.ENTITY_EFFECT, pos.x, pos.y, pos.z, 1000, 2F, 2F, 2F, Float.intBitsToFloat(PotionUtils.getColor(stack)));
		}

		for(Entity e : entities){
			if(!(e instanceof LivingEntity lE)) return;
			for(MobEffectInstance mobeffectinstance : effects) {
				if (mobeffectinstance.getEffect().isInstantenous()) {
					mobeffectinstance.getEffect().applyInstantenousEffect(player, null, lE, mobeffectinstance.getAmplifier(), 1.0D);
				} else {
					lE.addEffect(new MobEffectInstance(mobeffectinstance));
				}
			}
		}
	}
}
