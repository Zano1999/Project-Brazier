package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolType;

public class BrazierBlock extends DecoBlock {

	public static final BooleanProperty BURNING = BooleanProperty.create("burning");

	private final int fireDamage;

	public BrazierBlock(Properties props, int fireDamage, String shape) {
		super(props, shape);
		this.fireDamage = fireDamage;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(BURNING, false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BURNING);
	}

	@Override
	public int getLightValue(BlockState state, BlockGetter world, BlockPos pos) {
		return state.getValue(BURNING) ? 15 : 0;
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.fireImmune() && state.getValue(BURNING) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entityIn)) {
			entityIn.hurt(DamageSource.IN_FIRE, (float)this.fireDamage);
		}

		super.entityInside(state, worldIn, pos, entityIn);
	}

	@Override
	@Deprecated
	public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
		if(!world.isClientSide() && projectile.isOnFire()){
			Entity owner = projectile.getOwner();
			//Check if mob griefing is active
			boolean flag = owner == null || owner instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, owner);
			if (flag && !state.getValue(BURNING))
				world.setBlock(hit.getBlockPos(), state.setValue(BURNING, true), 3);
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack heldItem = player.getItemInHand(hand);
		if(state.getValue(BURNING)){
			if(heldItem.getToolTypes().contains(ToolType.SHOVEL)){
				if(world.isClientSide()) return InteractionResult.SUCCESS;
				if(!player.isCreative())
					heldItem.hurt(1, player.getRandom(), (ServerPlayer) (player));
				world.setBlockAndUpdate(pos, state.setValue(BURNING, false));
				world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.8f, 2f);
				return InteractionResult.SUCCESS;
			}
		}else{
			if(heldItem.getItem() == Items.FLINT_AND_STEEL){
				if(world.isClientSide()) return InteractionResult.SUCCESS;
				if(!player.isCreative())
					heldItem.hurt(1, player.getRandom(), (ServerPlayer) (player));
				world.setBlockAndUpdate(pos, state.setValue(BURNING, true));
				world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1f, 1f);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
