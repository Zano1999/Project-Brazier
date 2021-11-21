package net.dark_roleplay.projectbrazier.feature.blocks;

import net.dark_roleplay.projectbrazier.feature.blocks.templates.DecoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import net.minecraft.block.AbstractBlock.Properties;

public class BrazierBlock extends DecoBlock {

	public static final BooleanProperty BURNING = BooleanProperty.create("burning");

	private final int fireDamage;

	public BrazierBlock(Properties props, int fireDamage, String shape) {
		super(props, shape);
		this.fireDamage = fireDamage;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(BURNING, false);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BURNING);
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getValue(BURNING) ? 15 : 0;
	}

	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.fireImmune() && state.getValue(BURNING) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entityIn)) {
			entityIn.hurt(DamageSource.IN_FIRE, (float)this.fireDamage);
		}

		super.entityInside(state, worldIn, pos, entityIn);
	}

	@Override
	@Deprecated
	public void onProjectileHit(World world, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
		if(!world.isClientSide() && projectile.isOnFire()){
			Entity owner = projectile.getOwner();
			//Check if mob griefing is active
			boolean flag = owner == null || owner instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, owner);
			if (flag && !state.getValue(BURNING))
				world.setBlock(hit.getBlockPos(), state.setValue(BURNING, true), 3);
		}
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getItemInHand(hand);
		if(state.getValue(BURNING)){
			if(heldItem.getToolTypes().contains(ToolType.SHOVEL)){
				if(world.isClientSide()) return ActionResultType.SUCCESS;
				if(!player.isCreative())
					heldItem.hurt(1, player.getRandom(), (ServerPlayerEntity) (player));
				world.setBlockAndUpdate(pos, state.setValue(BURNING, false));
				world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.PLAYERS, 0.8f, 2f);
				return ActionResultType.SUCCESS;
			}
		}else{
			if(heldItem.getItem() == Items.FLINT_AND_STEEL){
				if(world.isClientSide()) return ActionResultType.SUCCESS;
				if(!player.isCreative())
					heldItem.hurt(1, player.getRandom(), (ServerPlayerEntity) (player));
				world.setBlockAndUpdate(pos, state.setValue(BURNING, true));
				world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1f, 1f);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}
}
